package com.job_room.annoouncement_service.service;

import com.job_room.annoouncement_service.model.announcement.AnnouncementDto;
import com.job_room.annoouncement_service.model.consume_model.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.text.ParseException;
import java.util.Date;
import java.util.List;


public interface AnnouncementService {



    //TODO: Select announcements by company =========================================================
    Page<AnnouncementDto> selectAnnouncementsByCompany(int companyId, String caption, Pageable pageable);

    //TODO: Ban announcement =========================================================
    AnnouncementDto ban(int id);

    //TODO: Unban announcement =========================================================
    AnnouncementDto unban(int id);


    //For Job seeker

    //TODO: Select all announcements by position and range of dates =========================================================
    Page<AnnouncementDto> selectAllByPositionAndDates(String position, Date startDate, Date endDate, Pageable pageable);

    //For HR

    //TODO: Select  announcement =========================================================
    Page<AnnouncementDto> findAllByPosition(String position, Pageable pageable);

    //TODO: Select draft announcement by Company =========================================================
    Page<AnnouncementDto> selectDraft(int id, String caption, Pageable pageable);

    //TODO Select Draft announcement By companyId and Position
    Page<AnnouncementDto> selectDraftByCompanyAndPosition(int id, String position, Pageable pageable);

    ///TODO: Select announcement by id =========================================================
    AnnouncementDto selectById(int id);

    //TODO: Insert announcement =========================================================
    AnnouncementDto insert(AnnouncementDto announcementDto) throws ParseException;

    //TODO: Delete announcement =========================================================
    AnnouncementDto delete(int id);

    //TODO: Update announcement =========================================================
    AnnouncementDto update(AnnouncementDto announcementDto) throws ParseException;

    //TODO: Share announcement =========================================================
    AnnouncementDto share(int id);

    //TODO: Unshare announcement =========================================================
    AnnouncementDto unshare(int id);

    //TODO: Draft announcement =========================================================
    AnnouncementDto draft(int id);

    //TODO: Unshare announcement =========================================================
    AnnouncementDto undraft(int id);

    // TODO select Announcement with close date(Close Announcement)
    Page<AnnouncementDto> findAllByCompanyIdAndClosedDate(int id, String caption, Date currentDate, Pageable pageable);

    //TODO select all position
    List<String> selectAllPosition(int id);

    //TODO filter active Announcement By Company and Position
    Page<AnnouncementDto> filterActiveAnnouncementByCompanyIdAndPosition(int id, String position, Date currentDate, Pageable pageable);

    ///TODO select Active Announcement
    Page<AnnouncementDto> selectActiveAnnouncementByCompanyId(int id, String caption, Date currentDate, Pageable pageable);

    //TODO filter close announcement by company and position
    Page<AnnouncementDto> filterCloseAnnouncementByCompanyIdAndPosition(int id, String position, Date currentDate, Pageable pageable);

    //TODO find announcements by id
    AnnouncementDto findById(int id);

    //TODO consume company
    Company companyById(int id);

    //TODO Count Active Announcement
    int countActiveAnnouncement(int id ,Date currentDate);

    //TODO Count Close Announcement
    int countCloseAnnouncement(int id,Date currentDate);
    
    //TODO find active announcement by company , isBanned false ,isDraft false ,status true
    List<AnnouncementDto> findAllActiveAnnouncementByCompanyIdNoPage(int companyId, Date currentDate);

    //TODO find active announcement by start date and end date ,status is true ,banned is false ,draft is false;
    Page<AnnouncementDto> findActiveAnnouncementByCompanyIdStartDateAndEndDate(int companyId, Date start, Date end, Pageable pageable);

    //TODO find close announcement by start date and end date ,status is true ,banned is false ,draft is false;
    Page<AnnouncementDto> findCloseAnnouncementByCompanyIdStartDateAndEndDate(int companyId, Date start, Date end, Pageable pageable);

    ///TODO find Save Draft announcement by company , isBanned false ,isDraft true ,status true
    Page<AnnouncementDto> findSaveAnnouncementByCompanyIdStartDateAndEndDate(int companyId, Date start, Date end, Pageable pageable);


    //admin
    //TODO find announcement by companyId (All Announcement ,banned save draft...)
    Page<AnnouncementDto> findAnnouncementByCompanyIdForAdmin(int companyId, String caption,Date startDate,Date endDate, Pageable pageable);

    AnnouncementDto findAnnouncementByIdForAdmin(int id);
}
