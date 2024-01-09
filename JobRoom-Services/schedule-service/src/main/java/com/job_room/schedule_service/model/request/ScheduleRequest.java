package com.job_room.schedule_service.model.request;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

public class ScheduleRequest {
    @NotBlank(message = "position cannot be empty")
    private String position;
    @NotBlank(message = "remark cannot be empty")
    private String remark;
    @NotBlank(message = "meetingDate cannot be empty")
    private String meetingDate;
    private String meetingTime;
    private int hrId;
    private int candidateId;
    private String emailContent;
    public ScheduleRequest (){}

    public ScheduleRequest(@NotBlank(message = "position cannot be empty") String position, @NotBlank(message = "remark cannot be empty") String remark, @NotBlank(message = "meetingDate cannot be empty") String meetingDate, String meetingTime, int hrId, int candidateId, String emailContent) {
        this.position = position;
        this.remark = remark;
        this.meetingDate = meetingDate;
        this.meetingTime = meetingTime;
        this.hrId = hrId;
        this.candidateId = candidateId;
        this.emailContent = emailContent;
    }

    public String getEmailContent() {
        return emailContent;
    }

    public void setEmailContent(String emailContent) {
        this.emailContent = emailContent;
    }

    public String getMeetingTime() {
        return meetingTime;
    }

    public void setMeetingTime(String meetingTime) {
        this.meetingTime = meetingTime;
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

    public int getHrId() {
        return hrId;
    }

    public void setHrId(int hrId) {
        this.hrId = hrId;
    }

    public int getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(int candidateId) {
        this.candidateId = candidateId;
    }
}
