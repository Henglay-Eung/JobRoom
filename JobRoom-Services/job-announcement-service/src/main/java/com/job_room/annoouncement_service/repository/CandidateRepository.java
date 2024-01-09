package com.job_room.annoouncement_service.repository;

import com.job_room.annoouncement_service.model.candidate.Candidate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate,Integer> {

    //TODO: Find by id =========================================================
    Candidate findByIdAndStatusIsTrue(int id);

    //TODO: Find all =========================================================
    Page<Candidate> findAllByStatusIsTrue(Pageable pageable);

    //TODO: Find all by announcement =========================================================
    Page<Candidate> findAllByAnnouncementIdAndStatusIsTrue(int announcementId, Pageable pageable);
//
//    //TODO: Count candidate by announcement =========================================================
    int countCandidateByCompanyIdAndStatusIsTrue(int id);

    //TODO: Find all by apply date =========================================================
    Page<Candidate> findAllByCompanyIdAndAppliesDateBetweenAndStatusIsTrue(int id,Date startDate, Date endDate, Pageable pageable);
    
    //TODO Count Candidate
    int countCandidateByAnnouncementIdAndStatusIsTrue(int announcementId);

    //TODO find candidate by companyId
    Page<Candidate> findAllByCompanyIdAndStatusIsTrue(int companyId,Pageable pageable);

    //TODO search candidate By name;
    Page<Candidate> findAllByCompanyIdAndNameContainingIgnoreCaseAndStatusIsTrue(int companyId,String name,Pageable pageable);
}
