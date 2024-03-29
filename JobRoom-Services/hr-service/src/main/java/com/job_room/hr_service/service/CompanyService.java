package com.job_room.hr_service.service;

import com.job_room.hr_service.model.company.Company;
import com.job_room.hr_service.model.company.CompanyDto;
import com.job_room.hr_service.rest.message.CompanyAnnouncement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CompanyService {

    //For admin
    //TODO: Select all companies not banned =========================================================
    Page<CompanyDto> findAll(String name, Pageable pagination);

    //TODO: Select company by id =========================================================
    CompanyDto selectById(int id);

    //TODO: Ban company ==================================================================
    CompanyDto ban(int id);

    //TODO: Unban company ================================================================
    CompanyDto unban(int id);

    //TODO: Count all company ================================================================
    int countAllCompany();

    //For HR

    //TODO: Select company with announcements ============================================
//    CompanyAnnouncement selectCompanyWithAnnouncements(int id);

    //TODO: Insert company ===============================================================
    CompanyDto insert(CompanyDto companyDto);

    //TODO: Delete company ===============================================================
    CompanyDto delete(int id);

    //TODO: Update company ===============================================================
    CompanyDto update(CompanyDto companyDto);

    //TODO Find by id for admin
    CompanyDto findByIdAndStatusIsTrueForAdmin(int id);

    //TODO find by UUID
    CompanyDto findByUUID(String uuid);
}
