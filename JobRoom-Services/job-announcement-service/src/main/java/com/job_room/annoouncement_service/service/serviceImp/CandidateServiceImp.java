package com.job_room.annoouncement_service.service.serviceImp;

import com.job_room.annoouncement_service.components.CommonUtils;
import com.job_room.annoouncement_service.configuration.BaseAPI;
import com.job_room.annoouncement_service.model.Token;
import com.job_room.annoouncement_service.model.User;
import com.job_room.annoouncement_service.model.announcement.AnnouncementDto;
import com.job_room.annoouncement_service.model.candidate.Candidate;
import com.job_room.annoouncement_service.model.candidate.CandidateDto;
import com.job_room.annoouncement_service.model.candidate.CandidateEmployee;
import com.job_room.annoouncement_service.repository.CandidateRepository;
import com.job_room.annoouncement_service.rest.message.BaseApiResponse;
import com.job_room.annoouncement_service.service.AnnouncementService;
import com.job_room.annoouncement_service.service.CandidateService;
import com.job_room.annoouncement_service.utils.PaginationUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class CandidateServiceImp implements CandidateService {
    private CandidateRepository candidateRepository;
    private ModelMapper modelMapper = new ModelMapper();
    private RestTemplate restTemplate;
    private AnnouncementService announcementService;
    private CommonUtils commonUtils;


    HttpHeaders headers;
    HttpEntity<String> entity;

    Token token = new Token();

    @Autowired
    public void setCommonUtils(CommonUtils commonUtils) {
        this.commonUtils = commonUtils;
    }

    @Autowired
    public void setAnnouncementService(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Autowired
    public void setCandidateRepository(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    //TODO: Insert Candidate =========================================================
    @Override
    public CandidateDto insert(CandidateDto candidateDto) throws ParseException {

        candidateDto.setStatus(true);
        Candidate candidate = modelMapper.map(candidateDto, Candidate.class);
        Date date = new Date();
        String strDateFormat = "yyyy-MM-dd";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        String formattedDate = dateFormat.format(date);
        candidate.setAppliesDate(new Timestamp(System.currentTimeMillis()));


        if (announcementService.findById(candidateDto.getAnnouncementId()) == null) {
            return null;
        }

        if (selectEmployeeById(candidateDto.getEmployeeId()) == null) {
            return null;
        }
        if (announcementService.companyById(candidateDto.getCompanyId()) == null) {
            return null;
        }

        Candidate isInserted = candidateRepository.save(candidate);

        if (isInserted == null) {
            return null;
        } else {
            candidateDto.setAppliesDate(formattedDate);
            return candidateDto;
        }

    }

    //TODO: Delete Candidate =========================================================
    @Override
    public CandidateDto delete(int id) {
        Candidate candidate = candidateRepository.findByIdAndStatusIsTrue(id);

        if (candidate == null)
            return null;

        candidate.setStatus(false);
        candidateRepository.save(candidate);

        CandidateDto candidateDto = modelMapper.map(candidate, CandidateDto.class);

        return candidateDto;
    }

    ///TODO: Select Candidate by id =========================================================
    @Override
    public CandidateDto selectById(int id) {
        Candidate candidate = candidateRepository.findByIdAndStatusIsTrue(id);

        if (candidate == null)
            return null;

        CandidateDto candidateDto = modelMapper.map(candidate, CandidateDto.class);

        return candidateDto;
    }

    ///TODO: Select All Candidate  =========================================================
    @Override
    public Page<CandidateDto> selectAll(Pageable pageable) {

        Page<Candidate> candidates = candidateRepository.findAllByStatusIsTrue(pageable);

        PaginationUtils<CandidateDto, Page<Candidate>> paging = new PaginationUtils<>(CandidateDto.class);
        paging.setData(candidates);
        Page<CandidateDto> candidateDtos = paging;


        return candidateDtos;
    }

    ///TODO Consume Employee
    @Override
    public CandidateEmployee selectEmployeeById(int id) {

        getToken();

        ParameterizedTypeReference<BaseApiResponse<CandidateEmployee>> parameterizedTypeReference =
                new ParameterizedTypeReference<BaseApiResponse<CandidateEmployee>>() {
                };
        headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        entity = new HttpEntity<String>("parameters", headers);
        headers.add("Authorization", "Bearer "+token.getAccess_token());

        ResponseEntity<BaseApiResponse<CandidateEmployee>> result = restTemplate.exchange("https://gateway.kshrd-ite.com/employee/employees/" + id, HttpMethod.GET, entity, parameterizedTypeReference);

        if (result.getBody().getData() != null) {
            return result.getBody().getData();
        } else {
            return null;
        }
    }

    @Override
    public int countCandidatesByAnnouncement(int id) {
        return candidateRepository.countCandidateByAnnouncementIdAndStatusIsTrue(id);
    }


    ///TODO Filter Candidate By AnnouncementId
    @Override
    public Page<CandidateDto> filterCandidateByAnnouncementId(int announcementId, Pageable pageable) {
        Page<Candidate> candidates = candidateRepository.findAllByAnnouncementIdAndStatusIsTrue(announcementId, pageable);

        PaginationUtils<CandidateDto, Page<Candidate>> paging = new PaginationUtils<>(CandidateDto.class);
        paging.setData(candidates);
        Page<CandidateDto> candidateDtos = paging;


        return candidateDtos;
    }

    ///TODO Filter By Date
    @Override
    public Page<CandidateDto> findAllByCompanyIdAndAppliesDateBetween(int id, Date startDate, Date endDate, Pageable pageable) {

        Page<Candidate> candidates = candidateRepository.findAllByCompanyIdAndAppliesDateBetweenAndStatusIsTrue(id, startDate, endDate, pageable);
        PaginationUtils<CandidateDto, Page<Candidate>> paging = new PaginationUtils<>(CandidateDto.class);
        paging.setData(candidates);
        Page<CandidateDto> candidateDtos = paging;
        return candidateDtos;

    }

    ///TODO Count Candidate By Company
    @Override
    public int countAllByCompanyId(int id) {
        return candidateRepository.countCandidateByCompanyIdAndStatusIsTrue(id);
    }


    @Override
    public Page<CandidateDto> findAllByCompanyId(int companyId, Pageable pageable) {
        Page<Candidate> candidates = candidateRepository.findAllByCompanyIdAndStatusIsTrue(companyId, pageable);
        PaginationUtils<CandidateDto, Page<Candidate>> paging = new PaginationUtils<>(CandidateDto.class);
        paging.setData(candidates);
        Page<CandidateDto> candidateDtos = paging;
        return candidateDtos;
    }

    @Override
    public Page<CandidateDto> SearchCandidateByName(int companyId, String name, Pageable pageable) {
        Page<Candidate> candidates = candidateRepository.findAllByCompanyIdAndNameContainingIgnoreCaseAndStatusIsTrue(companyId, name, pageable);
        PaginationUtils<CandidateDto, Page<Candidate>> paging = new PaginationUtils<>(CandidateDto.class);
        paging.setData(candidates);
        Page<CandidateDto> candidateDtos = paging;
        return candidateDtos;
    }

//    public Object readObjectFromFile(String filepath) {
//
//        try {
//
//            FileInputStream fileIn = new FileInputStream(filepath);
//            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
//
//            Object obj = objectIn.readObject();
//
//            System.out.println("The Object has been read from the file");
//            objectIn.close();
//            return obj;
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return null;
//        }
//    }

    public void getToken(){

        RestTemplate restTemplate = new RestTemplate();

        String url = "https://gateway.kshrd-ite.com/root/post/oauth2/token";
        String clientId = "6y1yGsCwJCn9mmbrs6Vh7LFW7H8dFO5m";
        String redirectUri = "http://35.197.132.204:31000";
        String username = "jobroom";
        String password = "jobroom";
        String clientSecret = "VbI7nYEG2lD1ct65rILNp9sdh9aALIar";
        String provisionKey = "AU3p0FF7yU4mA8thdUGG8xdpOV45Iwjb";
        String authenticated_userid = "jobroom";
        String scope = "email";

        HttpHeaders headers;

        HttpEntity<String> entity;

        headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        entity = new HttpEntity<String>("parameters", headers);

        token = restTemplate.postForObject(url,new User(),Token.class);


//        final String filepath= BaseAPI.TOKEN_PATH;
//
//        try {
//
//            FileOutputStream fileOut = new FileOutputStream(filepath);
//            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
//            objectOut.writeObject(token);
//            objectOut.close();
//            System.out.println("The Object  was successfully written to a file");
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
    }
}
