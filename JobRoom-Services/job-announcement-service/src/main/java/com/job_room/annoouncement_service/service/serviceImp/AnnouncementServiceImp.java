package com.job_room.annoouncement_service.service.serviceImp;

import com.job_room.annoouncement_service.components.CommonUtils;
import com.job_room.annoouncement_service.model.Token;
import com.job_room.annoouncement_service.model.User;
import com.job_room.annoouncement_service.model.announcement.Announcement;
import com.job_room.annoouncement_service.model.announcement.AnnouncementDto;
import com.job_room.annoouncement_service.model.consume_model.Company;
import com.job_room.annoouncement_service.repository.AnnouncementRepository;
import com.job_room.annoouncement_service.rest.message.BaseApiResponse;
import com.job_room.annoouncement_service.service.AnnouncementService;
import com.job_room.annoouncement_service.utils.PaginationUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AnnouncementServiceImp implements AnnouncementService {

    private AnnouncementRepository announcementRepository;

    private ModelMapper modelMapper = new ModelMapper();
    private CommonUtils commonUtils;
    private RestTemplate restTemplate;

    Token token = new Token();

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Autowired
    public void setCommonUtils(CommonUtils commonUtils) {
        this.commonUtils = commonUtils;
    }

    @Autowired
    public void setAnnouncementRepository(AnnouncementRepository announcementRepository) {
        this.announcementRepository= announcementRepository;
    }

    //For admin

    //TODO: Select announcements by company id =========================================================
    @Override
    public Page<AnnouncementDto> selectAnnouncementsByCompany(int companyId, String caption, Pageable pageable) {

        Page<Announcement> announcements = announcementRepository.findAllByCompanyIdAndCaptionContainingIgnoreCaseAndStatusIsTrueAndIsBannedIsFalseAndIsDraftIsFalse(companyId,caption,pageable);

        PaginationUtils<AnnouncementDto, Page<Announcement>> paging = new PaginationUtils<>(AnnouncementDto.class);
        paging.setData(announcements);
        Page<AnnouncementDto> announcementDtos = paging;
        return announcementDtos;
    }

    //TODO: Ban announcement =========================================================
    @Override
    public AnnouncementDto ban(int id) {

        Announcement announcement = announcementRepository.findByIdAndStatusIsTrueAndIsBannedIsFalse(id);
        if(announcement==null){
            return null;
        }
        announcement.setBanned(true);

        Announcement isUpdated = announcementRepository.save(announcement);

        AnnouncementDto announcementDto = modelMapper.map(announcement, AnnouncementDto.class);

        if (isUpdated==null) {
            return null;
        }
        else
            return announcementDto;
    }

    @Override
    public AnnouncementDto unban(int id) {

        Announcement announcement = announcementRepository.findByIdAndStatusIsTrue(id);

        if(announcement==null)
            return  null;

        announcement.setBanned(false);

        Announcement isUpdated = announcementRepository.save(announcement);

        AnnouncementDto announcementDto = modelMapper.map(announcement, AnnouncementDto.class);

        if (isUpdated==null) {
            return null;
        }
        else
            return announcementDto;
    }


    //For Job seeker

    //TODO: Select all announcements by position and range of dates =========================================================
    @Override
    public Page<AnnouncementDto> selectAllByPositionAndDates(String position, Date startDate, Date endDate, Pageable pagination) {

        Page<Announcement> announcements = announcementRepository.findAllByPositionContainingIgnoreCaseAndClosedDateBetweenAndClosedDateGreaterThanAndStatusIsTrueAndIsDraftIsFalseAndIsBannedIsFalse(position,startDate,endDate,commonUtils.getCurrentDate(),pagination);
        PaginationUtils<AnnouncementDto, Page<Announcement>> paging = new PaginationUtils<>(AnnouncementDto.class);
        paging.setData(announcements);
        Page<AnnouncementDto> announcementDtos = paging;

        return announcementDtos;
    }

    //For HR

    //TODO: Select announcement by id =========================================================
    @Override
    public AnnouncementDto selectById(int id) {

        Announcement announcement = announcementRepository.findByIdAndStatusIsTrueAndIsBannedIsFalse(id);

        if(announcement==null)
            return null;

        AnnouncementDto announcementDto = modelMapper.map(announcement, AnnouncementDto.class);

        return announcementDto;
    }

    //TODO: Insert announcement =========================================================
    @Override
    public AnnouncementDto insert(AnnouncementDto announcementDto) throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        Date closedDate = formatter.parse(announcementDto.getClosedDate());
        Date publishedDate = formatter.parse(announcementDto.getPublishedDate());

        announcementDto.setStatus(true);
        announcementDto.setUuid(UUID.randomUUID().toString());
        Announcement announcement = modelMapper.map(announcementDto, Announcement.class);
        announcement.setClosedDate(closedDate);
        announcement.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        announcement.setPublishedDate(publishedDate);
        Announcement isInserted = announcementRepository.save(announcement);

        announcementDto.setId(isInserted.getId());
        announcementDto.setCreatedDate(commonUtils.convertDateToString(isInserted.getCreatedDate()));
        if (isInserted==null) {
            return null;
        }
        else
            return announcementDto;
    }

    //TODO: Delete announcement =========================================================
    @Override
    public AnnouncementDto delete(int id) {

        Announcement announcement = announcementRepository.findByIdAndStatusIsTrueAndIsBannedIsFalse(id);

        if(announcement==null)
            return null;

        announcement.setStatus(false);
        announcementRepository.save(announcement);

        AnnouncementDto announcementDto = modelMapper.map(announcement, AnnouncementDto.class);

        return announcementDto;
    }

    //TODO: Update announcement =========================================================
    @Override
    public AnnouncementDto update(AnnouncementDto announcementDto) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        Date closedDate = formatter.parse(announcementDto.getClosedDate());
        Date publishedDate = formatter.parse(announcementDto.getPublishedDate());
        Announcement announcement = announcementRepository.findByIdAndStatusIsTrueAndIsBannedIsFalse(announcementDto.getId());
        if(announcement==null)
            return  null;


        announcement.setPublishedDate(publishedDate);
        announcement.setClosedDate(closedDate);
        announcement.setStatus(true);
        announcement.setImage(announcement.getImage());
        announcement.setForm(announcementDto.getForm());
        announcement.setCompanyId(announcementDto.getCompanyId());
        announcement.setBanned(announcementDto.isBanned());
        announcement.setDraft(announcementDto.isDraft());
        announcement.setCaption(announcementDto.getCaption());
        announcement.setIsShared(announcementDto.getIsShared());
        announcement.setPosition(announcementDto.getPosition());
        announcement.setThumbnail(announcementDto.getThumbnail());

        Announcement isUpdated = announcementRepository.save(announcement);
        System.out.println("Date:"+isUpdated.getCreatedDate());
        if (isUpdated==null) {
            return null;
        }
        else

            announcementDto=modelMapper.map(isUpdated, AnnouncementDto.class);
            announcementDto.setCreatedDate(commonUtils.convertDateToString(isUpdated.getCreatedDate()));
            announcementDto.setClosedDate(commonUtils.convertDateToString(isUpdated.getClosedDate()));
            announcementDto.setPublishedDate(commonUtils.convertDateToString(isUpdated.getPublishedDate()));
            return announcementDto;

    }



    //TODO: Share announcement =========================================================
    @Override
    public AnnouncementDto share(int id) {

        Announcement announcement = announcementRepository.findByIdAndStatusIsTrueAndIsBannedIsFalse(id);

        if(announcement==null)
            return  null;

        announcement.setIsShared(true);

        Announcement isUpdated = announcementRepository.save(announcement);

        AnnouncementDto announcementDto = modelMapper.map(announcement, AnnouncementDto.class);

        if (isUpdated==null) {
            return null;
        }
        else
            return announcementDto;
    }

    @Override
    public AnnouncementDto unshare(int id) {

        Announcement announcement = announcementRepository.findByIdAndStatusIsTrueAndIsBannedIsFalse(id);

        if(announcement==null)
            return  null;

        announcement.setIsShared(false);

        Announcement isUpdated = announcementRepository.save(announcement);

        AnnouncementDto announcementDto = modelMapper.map(announcement, AnnouncementDto.class);

        if (isUpdated==null) {
            return null;
        }
        else
            return announcementDto;
    }

    @Override
    public AnnouncementDto draft(int id) {

        Announcement announcement = announcementRepository.findByIdAndStatusIsTrueAndIsBannedIsFalse(id);

        if(announcement==null)
            return  null;

        announcement.setDraft(true);

        Announcement isUpdated = announcementRepository.save(announcement);

        AnnouncementDto announcementDto = modelMapper.map(announcement, AnnouncementDto.class);

        if (isUpdated==null) {
            return null;
        }
        else
            return announcementDto;
    }

    @Override
    public AnnouncementDto undraft(int id) {

        Announcement announcement = announcementRepository.findByIdAndStatusIsTrueAndIsBannedIsFalse(id);

        if(announcement==null)
            return  null;

        announcement.setDraft(false);

        Announcement isUpdated = announcementRepository.save(announcement);

        AnnouncementDto announcementDto = modelMapper.map(announcement, AnnouncementDto.class);

        if (isUpdated==null) {
            return null;
        }
        else
            return announcementDto;
    }

    @Override
    public Page<AnnouncementDto> findAllByCompanyIdAndClosedDate(int id, String caption, Date currentDate, Pageable pageable) {

        Page<Announcement> announcements = announcementRepository.findAllByCompanyIdAndCaptionContainingIgnoreCaseAndClosedDateIsLessThanEqualAndStatusIsTrue(id,caption,currentDate,pageable);

        PaginationUtils<AnnouncementDto, Page<Announcement>> paging = new PaginationUtils<>(AnnouncementDto.class);
        paging.setData(announcements);
        Page<AnnouncementDto> announcementDtos = paging;

        return announcementDtos;
    }
    // TODO select All position for filter by position
    @Override
    public List<String> selectAllPosition(int id) {
        List<String> listPosition=announcementRepository.selectAllPosition(id);
        return listPosition;
    }

    @Override
    public Page<AnnouncementDto> selectDraft(int id, String caption, Pageable pageable) {

        Page<Announcement> announcements = announcementRepository.findAllByCompanyIdAndCaptionContainingIgnoreCaseAndStatusIsTrueAndIsDraftIsTrueAndIsBannedIsFalse(id,caption,pageable);
        PaginationUtils<AnnouncementDto, Page<Announcement>> paging = new PaginationUtils<>(AnnouncementDto.class);
        paging.setData(announcements);
        Page<AnnouncementDto> announcementDtos = paging;
        return announcementDtos;
    }

    ///TODO: Select announcement by id =========================================================
    @Override
    public Page<AnnouncementDto> selectDraftByCompanyAndPosition(int id, String position, Pageable pageable) {

        Page<Announcement> announcements = announcementRepository.findAllByCompanyIdAndPositionEqualsAndStatusIsTrueAndIsDraftIsTrueAndIsBannedIsFalse(id,position,pageable);
        PaginationUtils<AnnouncementDto, Page<Announcement>> paging = new PaginationUtils<>(AnnouncementDto.class);
        paging.setData(announcements);
        Page<AnnouncementDto> announcementDtos = paging;
        return announcementDtos;
    }

    //TODO select all position
    @Override
    public Page<AnnouncementDto> findAllByPosition(String position, Pageable pageable) {

        Page<Announcement> announcements = announcementRepository.findAllByPositionContainingAndStatusIsTrueAndIsDraftIsFalseAndIsBannedIsFalse(position,pageable);
        PaginationUtils<AnnouncementDto, Page<Announcement>> paging = new PaginationUtils<>(AnnouncementDto.class);
        paging.setData(announcements);
        Page<AnnouncementDto> announcementDtos = paging;
        return announcementDtos;
    }


    //TODO filter Announcement By Company and Position

    @Override
    public Page<AnnouncementDto> filterActiveAnnouncementByCompanyIdAndPosition(int id, String position, Date currentDate, Pageable pageable) {

        Page<Announcement> announcements = announcementRepository.findAllByCompanyIdAndPositionEqualsAndClosedDateGreaterThanAndStatusIsTrueAndIsBannedIsFalseAndIsDraftIsFalse(id,position,currentDate,pageable);

        PaginationUtils<AnnouncementDto, Page<Announcement>> paging = new PaginationUtils<>(AnnouncementDto.class);
        paging.setData(announcements);

        Page<AnnouncementDto> announcementDtos = paging;
        return announcementDtos;
    }

    ///TODO select Active Announcement
    @Override
    public Page<AnnouncementDto> selectActiveAnnouncementByCompanyId(int id, String caption, Date currentDate, Pageable pageable) {

        Page<Announcement> announcements = announcementRepository.findAllByCompanyIdAndCaptionContainingIgnoreCaseAndClosedDateGreaterThanAndStatusIsTrueAndIsBannedIsFalseAndIsDraftIsFalse(id,caption,currentDate,pageable);

        PaginationUtils<AnnouncementDto, Page<Announcement>> paging = new PaginationUtils<>(AnnouncementDto.class);
        paging.setData(announcements);

        Page<AnnouncementDto> announcementDtos = paging;
        return paging;
    }

    //TODO filter close announcement by company and position
    @Override
    public Page<AnnouncementDto> filterCloseAnnouncementByCompanyIdAndPosition(int id, String position, Date currentDate, Pageable pageable) {

        Page<Announcement> announcements = announcementRepository.findAllByCompanyIdAndPositionEqualsAndClosedDateIsLessThanEqualAndStatusIsTrue(id,position,currentDate,pageable);

        PaginationUtils<AnnouncementDto, Page<Announcement>> paging = new PaginationUtils<>(AnnouncementDto.class);
        paging.setData(announcements);

        Page<AnnouncementDto> announcementDtos = paging;
        return announcementDtos;
    }

    @Override
    public AnnouncementDto findById(int id) {

        Announcement announcement = announcementRepository.findByIdAndStatusIsTrueAndIsBannedIsFalse(id);

        if(announcement==null)
            return null;

        AnnouncementDto announcementDto =modelMapper.map(announcement, AnnouncementDto.class);

        return announcementDto;
    }

    //TODO consume Company
    @Override
    public Company companyById(int id) {
        ParameterizedTypeReference<BaseApiResponse<Company>> parameterizedTypeReference =
                new ParameterizedTypeReference<BaseApiResponse<Company>>() {
                };

        getToken();
        System.out.println(token);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        headers.add("Authorization", "Bearer "+token.getAccess_token());

        ResponseEntity<BaseApiResponse<Company>> result = restTemplate.exchange("https://gateway.kshrd-ite.com/hr/companies/" + id, HttpMethod.GET, entity, parameterizedTypeReference);
        if (result.getBody().getData() != null) {
            return result.getBody().getData();
        } else {
            return null;
        }

    }

    @Override
    public int countActiveAnnouncement(int id, Date currentDate) {
        return this.announcementRepository.countByCompanyIdAndClosedDateGreaterThanAndStatusIsTrueAndIsBannedIsFalseAndIsDraftIsFalse(id,currentDate);
    }

    @Override
    public int countCloseAnnouncement(int id, Date currentDate) {
        return this.announcementRepository.countByCompanyIdAndClosedDateLessThanEqualAndStatusIsTrueAndIsBannedIsFalse(id,currentDate);
    }

    //TODO find active announcement by company , isBanned false ,isDraft false ,status true
    //TODO ***NOTE***  use for get announcement for count candidate that apply this announcement and this announcement is active
    @Override
    public List<AnnouncementDto> findAllActiveAnnouncementByCompanyIdNoPage(int companyId, Date currentDate) {

        List<Announcement> announcementList=announcementRepository.findAllByCompanyIdAndClosedDateGreaterThanAndStatusIsTrueAndIsBannedIsFalseAndIsDraftIsFalse(companyId,currentDate);
        List<AnnouncementDto> announcementDtoLis=new ArrayList<>();
        for(int i=0;i<announcementList.size();i++){
            announcementDtoLis.add(modelMapper.map(announcementList.get(i), AnnouncementDto.class));
        }

        return announcementDtoLis;
    }

    //TODO find active announcement by start date and end date ,status is true ,banned is false ,draft is false;
    @Override
    public Page<AnnouncementDto> findActiveAnnouncementByCompanyIdStartDateAndEndDate(int companyId, Date start, Date end, Pageable pageable) {
        Page<Announcement> announcements;
        PaginationUtils<AnnouncementDto, Page<Announcement>> paging = new PaginationUtils<>(AnnouncementDto.class);
             announcements=announcementRepository.findAllByCompanyIdAndClosedDateGreaterThanAndClosedDateBetweenAndIsBannedIsFalseAndStatusIsTrueAndIsDraftIsFalse(companyId,commonUtils.getCurrentDate(),start,end,pageable);
            paging.setData(announcements);


        Page<AnnouncementDto> announcementDtos = paging;
        return announcementDtos;
    }
    //TODO find close announcement by start date and end date ,status is true ,banned is false ,draft is false;
    @Override
    public Page<AnnouncementDto> findCloseAnnouncementByCompanyIdStartDateAndEndDate(int companyId, Date start, Date end, Pageable pageable) {
        Page<Announcement> announcements;
        PaginationUtils<AnnouncementDto, Page<Announcement>> paging = new PaginationUtils<>(AnnouncementDto.class);
        announcements=announcementRepository.findAllByCompanyIdAndClosedDateLessThanEqualAndClosedDateBetweenAndIsBannedIsFalseAndStatusIsTrueAndIsDraftIsFalse(companyId,commonUtils.getCurrentDate(),start,end,pageable);
        paging.setData(announcements);


        Page<AnnouncementDto> announcementDtos = paging;
        return announcementDtos;
    }
    ///TODO find Save Draft announcement by company , isBanned false ,isDraft true ,status true
    @Override
    public Page<AnnouncementDto> findSaveAnnouncementByCompanyIdStartDateAndEndDate(int companyId, Date start, Date end, Pageable pageable) {
        Page<Announcement> announcements;
        PaginationUtils<AnnouncementDto, Page<Announcement>> paging = new PaginationUtils<>(AnnouncementDto.class);
        announcements=announcementRepository.findAllByCompanyIdAndCreatedDateBetweenAndStatusIsTrueAndIsBannedIsFalseAndIsDraftIsTrue(companyId,start,end,pageable);
        paging.setData(announcements);


        Page<AnnouncementDto> announcementDtos = paging;
        return announcementDtos;
    }

    @Override
    public Page<AnnouncementDto> findAnnouncementByCompanyIdForAdmin(int companyId, String caption,Date startDate,Date endDate, Pageable pageable) {

        Page<Announcement> announcements = announcementRepository.findAllByCompanyIdAndPositionContainingIgnoreCaseAndClosedDateBetweenAndStatusIsTrue(companyId,caption,startDate,endDate,pageable);

        PaginationUtils<AnnouncementDto, Page<Announcement>> paging = new PaginationUtils<>(AnnouncementDto.class);
        paging.setData(announcements);
        Page<AnnouncementDto> announcementDtos = paging;
        return announcementDtos;
    }

    @Override
    public AnnouncementDto findAnnouncementByIdForAdmin(int id) {

        Announcement announcement = announcementRepository.findByIdAndStatusIsTrue(id);

        if(announcement==null)
            return null;

        AnnouncementDto announcementDto = modelMapper.map(announcement, AnnouncementDto.class);

        return announcementDto;
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
