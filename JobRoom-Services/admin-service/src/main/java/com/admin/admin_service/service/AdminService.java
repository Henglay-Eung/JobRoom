package com.admin.admin_service.service;


import com.admin.admin_service.model.AnnouncementResponse;
import com.admin.admin_service.model.CompanyResponse;

import java.util.List;

public interface AdminService {

    //TODO: Count all company =========================================================
    int countAllCompany();

    //TODO: Count all announcement =========================================================
    int countAllAnnouncement();

    //TODO: Select company =========================================================
    List<CompanyResponse> selectCompany(String name);

    //TODO: Select one company =========================================================
    CompanyResponse selectOneCompany(int id);

    //TODO: Ban company =========================================================
    CompanyResponse banCompany(int id);

    //TODO: Unban company =========================================================
    CompanyResponse unbanCompany(int id);

    //TODO: Select company =========================================================
    List<AnnouncementResponse> selectAnnouncementByCompany(int companyId);

    //TODO: Select one announcement =========================================================
    AnnouncementResponse selectOneAnnouncement(int id);

    //TODO: Ban announcement =========================================================
    AnnouncementResponse banAnnouncement(int id);

    //TODO: Unban announcement =========================================================
    AnnouncementResponse unbanAnnouncement(int id);
}
