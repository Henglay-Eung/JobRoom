package com.job_room.schedule_service.service;

import com.job_room.schedule_service.model.Dto.ScheduleDto;
import com.job_room.schedule_service.model.reponse.ScheduleCandidate;
import com.job_room.schedule_service.model.schedule.Schedule;
import org.springframework.data.domain.Page;

import javax.mail.MessagingException;

public interface ScheduleService {

    //TODO: Insert Schedule =========================================================
    ScheduleDto insert(ScheduleDto scheduleDto);

    //TODO: Select all Schedule By Status =========================================================
    Page<ScheduleDto> selectAllByStatus(int id,int page, int pageSize);
    //TODO: Delete Schedule =========================================================

    ScheduleDto delete  (int id);
    //TODO: Select Schedule By Id and Status =========================================================

    ScheduleDto selectByIdAndStatus(int id);
    //TODO: Update Schedule =========================================================

    ScheduleDto update(int id,ScheduleDto scheduleDto);

    //TODO: Consume Candidate =========================================================
    ScheduleCandidate selectCandidateById(int id);

    //TODO: Notify Candidate =========================================================
    boolean notifyCandidate(String candidateEmail,String email,String password,String emailContent,String companyName,String position,String date) throws MessagingException;

    //TODO Count Schedule By HR
    int count(int hrId);

}
