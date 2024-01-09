package com.job_room.annoouncement_service.repository;

import com.job_room.annoouncement_service.model.announcement.Announcement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Integer> {



    //TODO: Find all by company id and caption =========================================================
    Page<Announcement> findAllByCompanyIdAndCaptionContainingIgnoreCaseAndStatusIsTrueAndIsBannedIsFalseAndIsDraftIsFalse(int companyId,String caption, Pageable pageable);


    //For admin
    //TODO: Find all by company id and caption =========================================================
    Page<Announcement> findAllByCompanyIdAndPositionContainingIgnoreCaseAndClosedDateBetweenAndStatusIsTrue(int companyId,String caption,Date startDate,Date endDate, Pageable pageable);


    //For Job seekers

    //TODO: Find all by position and dates =========================================================
    Page<Announcement> findAllByPositionContainingIgnoreCaseAndClosedDateBetweenAndClosedDateGreaterThanAndStatusIsTrueAndIsDraftIsFalseAndIsBannedIsFalse(String position, Date startDate, Date endDate,Date currenDate, Pageable pageable);

    //For HR

    //TODO: Find all by position =========================================================
    Page<Announcement> findAllByPositionContainingAndStatusIsTrueAndIsDraftIsFalseAndIsBannedIsFalse(String position, Pageable pageable);

    //TODO: Find all draft =========================================================
    Page<Announcement> findAllByCompanyIdAndCaptionContainingIgnoreCaseAndStatusIsTrueAndIsDraftIsTrueAndIsBannedIsFalse(int id,String caption,Pageable pageable);

    //TODO: Find draft by position =========================================================
    Page<Announcement> findAllByCompanyIdAndPositionEqualsAndStatusIsTrueAndIsDraftIsTrueAndIsBannedIsFalse(int id,String position,Pageable pageable);

    //TODO: Find by id and Banned is false =========================================================
    Announcement findByIdAndStatusIsTrueAndIsBannedIsFalse(int id);

    //TODO find by id (use for unbanned)
    //TODO for admin
    Announcement findByIdAndStatusIsTrue(int id);
    //TODO: Find close by caption and dates =========================================================
    Page<Announcement> findAllByCompanyIdAndCaptionContainingIgnoreCaseAndClosedDateIsLessThanEqualAndStatusIsTrue(int id,String caption,Date currentDate,Pageable pageable);

    //TODO: Find close by position and dates =========================================================
    Page<Announcement> findAllByCompanyIdAndPositionEqualsAndClosedDateIsLessThanEqualAndStatusIsTrue(int id,String position,Date currentDate,Pageable pageable);

    //TODO find close announcement by start date and end date ,status is true ,banned is false ,draft is false;
    Page<Announcement> findAllByCompanyIdAndClosedDateLessThanEqualAndClosedDateBetweenAndIsBannedIsFalseAndStatusIsTrueAndIsDraftIsFalse(int companyId,Date currentDate,Date start,Date end,Pageable pageable);


    //TODO: Find all positions =========================================================
    @Query(value = "SELECT a.position FROM Announcement as a WHERE a.companyId=:id AND a.status=true GROUP BY a.position")
    List<String> selectAllPosition(@Param("id")int id);

    //TODO: Find active announcement by position =========================================================
    Page<Announcement> findAllByCompanyIdAndPositionEqualsAndClosedDateGreaterThanAndStatusIsTrueAndIsBannedIsFalseAndIsDraftIsFalse(int id,String position,Date currentDate,Pageable pageable);

    //TODO: Find active announcement by caption =========================================================
    Page<Announcement> findAllByCompanyIdAndCaptionContainingIgnoreCaseAndClosedDateGreaterThanAndStatusIsTrueAndIsBannedIsFalseAndIsDraftIsFalse(int id,String caption,Date currentDate,Pageable pageable);

    //TODO find active announcement by start date and end date ,status is true ,banned is false ,draft is false;

    Page<Announcement> findAllByCompanyIdAndClosedDateGreaterThanAndClosedDateBetweenAndIsBannedIsFalseAndStatusIsTrueAndIsDraftIsFalse(int companyId,Date currentDate,Date start,Date end,Pageable pageable);

    //TODO Count Active Announcement
    int countByCompanyIdAndClosedDateGreaterThanAndStatusIsTrueAndIsBannedIsFalseAndIsDraftIsFalse(int companyId,Date currentDate);

    //TODO Count Close Announcement
    int countByCompanyIdAndClosedDateLessThanEqualAndStatusIsTrueAndIsBannedIsFalse(int companyId,Date currentDate);

    //TODO find active announcement by company , isBanned false ,isDraft false ,status true
    List<Announcement> findAllByCompanyIdAndClosedDateGreaterThanAndStatusIsTrueAndIsBannedIsFalseAndIsDraftIsFalse(int companyId,Date currentDate);

    ///TODO find Save Draft announcement by company , isBanned false ,isDraft true ,status true

    Page<Announcement> findAllByCompanyIdAndCreatedDateBetweenAndStatusIsTrueAndIsBannedIsFalseAndIsDraftIsTrue(int companyId,Date start,Date end,Pageable pageable);
}
