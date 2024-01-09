package com.job_room.hr_service.service.serviceImp;

import com.job_room.hr_service.model.Token;
import com.job_room.hr_service.model.User;
import com.job_room.hr_service.model.announcement.AnnouncementResponse;
import com.job_room.hr_service.model.company.Company;
import com.job_room.hr_service.model.company.CompanyDto;
import com.job_room.hr_service.model.company.CompanyResponse;
import com.job_room.hr_service.repository.CompanyRepository;
import com.job_room.hr_service.rest.message.BaseApiResponse;
import com.job_room.hr_service.rest.message.CompanyAnnouncement;
import com.job_room.hr_service.service.CompanyService;
import com.job_room.hr_service.utils.PaginationUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class CompanyServiceImp implements CompanyService {

    private CompanyRepository companyRepository;

    private ModelMapper modelMapper;

    private RestTemplate restTemplate;

    HttpHeaders headers;

    HttpEntity<String> entity;

    Token token = new Token();

    @Autowired
    public void setCompanyRepository(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }



    //For Admin

    //TODO: Select all companies =========================================================
    @Override
    public Page<CompanyDto> findAll(String name, Pageable pagination) {

        System.out.println(token);

        Page<Company> companies = companyRepository.findAllByNameContainingIgnoreCaseAndStatusIsTrue(name, pagination);

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        PaginationUtils<CompanyDto, Page<Company>> paging = new PaginationUtils<>(CompanyDto.class);
        paging.setData(companies);
        Page<CompanyDto> companyDtos = paging;

        return companyDtos;
    }

    @Override
    public CompanyDto ban(int id) {

        Company company = companyRepository.findByIdAndStatusIsTrueAndIsBannedIsFalse(id);
        if(company==null){
            return null;
        }
        company.setBanned(true);

        Company isUpdated = companyRepository.save(company);

        CompanyDto companyDto = modelMapper.map(company,CompanyDto.class);

        if (isUpdated==null) {
            return null;
        }
        else
            return companyDto;
    }

    @Override
    public CompanyDto unban(int id) {

        Company company = companyRepository.findByIdAndStatusIsTrue(id);
        if(company==null){
            return null;
        }
        company.setBanned(false);

        Company isUpdated = companyRepository.save(company);

        CompanyDto companyDto = modelMapper.map(company,CompanyDto.class);

        if (isUpdated==null) {
            return null;
        }
        else
            return companyDto;
    }

    @Override
    public int countAllCompany() {
        return companyRepository.countAllAndStatusIsTrue();
    }


    //For HR

    //TODO: Select company by id =========================================================
    @Override
    public CompanyDto selectById(int id) {

        Company company = companyRepository.findByIdAndStatusIsTrueAndIsBannedIsFalse(id);

        if (company == null)
            return null;

        CompanyDto companyDto = modelMapper.map(company, CompanyDto.class);

        return companyDto;
    }



    //TODO: Insert company =========================================================
    @Override
    public CompanyDto insert(CompanyDto companyDto) {

        companyDto.setStatus(true);
        companyDto.setUuid("cmp"+UUID.randomUUID().toString());

        Company company = modelMapper.map(companyDto, Company.class);

        Company isInserted = companyRepository.save(company);

        companyDto.setId(company.getId());

        if (isInserted == null) {
            return null;
        } else
            return companyDto;
    }

    //TODO: Delete company =========================================================
    @Override
    public CompanyDto delete(int id) {

        Company company = companyRepository.findByIdAndStatusIsTrueAndIsBannedIsFalse(id);

        if (company == null)
            return null;

        company.setStatus(false);

        companyRepository.save(company);

        CompanyDto companyDto = modelMapper.map(company, CompanyDto.class);

        return companyDto;
    }

    //TODO: Update company =========================================================
    @Override
    public CompanyDto update(CompanyDto companyDto) {

        Company company = modelMapper.map(companyDto, Company.class);

        Company companyToCheck = companyRepository.findByIdAndStatusIsTrueAndIsBannedIsFalse(company.getId());
        if (companyToCheck == null)
            return null;

        company.setUuid(companyToCheck.getUuid());
        Company isUpdated = companyRepository.save(company);

        if (isUpdated == null) {
            return null;
        } else
            return companyDto;
    }
//
//    //TODO: Select company with announcements =========================================================
//    @Override
//    public CompanyAnnouncement selectCompanyWithAnnouncements(int id) {
//
//        CompanyAnnouncement companyAnnouncement = new CompanyAnnouncement();
//        Company company = companyRepository.findByIdAndStatusIsTrueAndIsBannedIsFalse(id);
//
//        if(company==null)
//            return null;
//
//        CompanyResponse companyResponse = modelMapper.map(company, CompanyResponse.class);
//
//        ParameterizedTypeReference<BaseApiResponse<List<AnnouncementResponse>>> parameterizedTypeReference =
//                new ParameterizedTypeReference<BaseApiResponse<List<AnnouncementResponse>>>() {
//                };
//        headers = new HttpHeaders();
//        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//        entity = new HttpEntity<String>("parameters", headers);
//        ResponseEntity<BaseApiResponse<List<AnnouncementResponse>>> result = restTemplate.exchange("http://35.197.132.204:30006/api/v1/announcements/company/" + id, HttpMethod.GET, entity, parameterizedTypeReference);
//        if (result.getBody().getData() == null) {
//            return null;
//        }
//
//        companyAnnouncement.setCompanyResponse(companyResponse);
//        companyAnnouncement.setAnnouncementResponseList(result.getBody().getData());
//
//        return companyAnnouncement;
//    }

    @Override
    public CompanyDto findByIdAndStatusIsTrueForAdmin(int id) {

        Company company = companyRepository.findByIdAndStatusIsTrue(id);

        if (company == null)
            return null;

        CompanyDto companyDto = modelMapper.map(company, CompanyDto.class);

        return companyDto;
    }

    @Override
    public CompanyDto findByUUID(String uuid) {
        Company company = companyRepository.findByUuidAndStatusIsTrueAndIsBannedIsFalse(uuid);

        if (company == null)
            return null;

        CompanyDto companyDto = modelMapper.map(company, CompanyDto.class);

        return companyDto;
    }

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

    }
}