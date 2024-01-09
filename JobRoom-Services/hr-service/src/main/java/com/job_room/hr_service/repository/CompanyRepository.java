package com.job_room.hr_service.repository;

import com.job_room.hr_service.model.company.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

    //For admin

//    //TODO: Find all companies ==========================================================
//    Page<Company> findAllByNameContainingIgnoreCaseAndStatusIsTrueAndIsBannedIsFalse(String name,Pageable pageable);

    //TODO: Find all companies ==========================================================
    Page<Company> findAllByNameContainingIgnoreCaseAndStatusIsTrue(String name,Pageable pageable);

    //TODO: Find company by id ==========================================================
    Company findByIdAndStatusIsTrueAndIsBannedIsFalse(int id);

    Company findByIdAndStatusIsTrue(int id);
    //TODO: Count all companies =========================================================
    @Query("select count(c) FROM Company c where c.status = true")
    int countAllAndStatusIsTrue();

    //TODO find Hr By UUID
    Company findByUuidAndStatusIsTrueAndIsBannedIsFalse(String uuid);

}
