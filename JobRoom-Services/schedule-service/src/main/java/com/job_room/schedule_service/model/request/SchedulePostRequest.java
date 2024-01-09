package com.job_room.schedule_service.model.request;

import com.job_room.schedule_service.model.request.CandidateIdRequest;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import java.sql.Time;
import java.util.Arrays;
import java.util.Date;

public class SchedulePostRequest {

    @NotBlank(message = "position cannot be empty")
    private String position;
    @NotBlank(message = "remark cannot be empty")
    private String remark;
    @NotBlank(message = "meeting date cannot be empty")
    private String meetingDate;
    private String meetingTime;
    private int hrId;
    private CandidateIdRequest[] candidateIdRequest;
    private String emailContent;

    public SchedulePostRequest (){}

    public SchedulePostRequest(@NotBlank(message = "position cannot be empty") String position, @NotBlank(message = "remark cannot be empty") String remark, @NotBlank(message = "meeting date cannot be empty") String meetingDate, String meetingTime, int hrId, CandidateIdRequest[] candidateIdRequest, String emailContent) {
        this.position = position;
        this.remark = remark;
        this.meetingDate = meetingDate;
        this.meetingTime = meetingTime;
        this.hrId = hrId;
        this.candidateIdRequest = candidateIdRequest;
        this.emailContent = emailContent;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(String meetingDate) {
        this.meetingDate = meetingDate;
    }

    public String getMeetingTime() {
        return meetingTime;
    }

    public void setMeetingTime(String meetingTime) {
        this.meetingTime = meetingTime;
    }

    public int getHrId() {
        return hrId;
    }

    public void setHrId(int hrId) {
        this.hrId = hrId;
    }

    public CandidateIdRequest[] getCandidateIdRequest() {
        return candidateIdRequest;
    }

    public void setCandidateIdRequest(CandidateIdRequest[] candidateIdRequest) {
        this.candidateIdRequest = candidateIdRequest;
    }

    public String getEmailContent() {
        return emailContent;
    }

    public void setEmailContent(String emailContent) {
        this.emailContent = emailContent;
    }
}