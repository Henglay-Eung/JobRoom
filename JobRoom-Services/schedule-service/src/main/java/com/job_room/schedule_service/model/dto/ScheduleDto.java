package com.job_room.schedule_service.model.Dto;

import javax.persistence.Column;

public class ScheduleDto {
    private int id;
    private String position;
    private String remark;
    private String meetingDate;
    private String meetingTime;
    private int hrId;
    private int candidateId;
    private boolean status;
    private String emailContent;
    public ScheduleDto(){}

    public ScheduleDto(int id, String position, String remark, String meetingDate, String meetingTime, int hrId, int candidateId, boolean status, String emailContent) {
        this.id = id;
        this.position = position;
        this.remark = remark;
        this.meetingDate = meetingDate;
        this.meetingTime = meetingTime;
        this.hrId = hrId;
        this.candidateId = candidateId;
        this.status = status;
        this.emailContent = emailContent;
    }

    public String getMeetingTime() {
        return meetingTime;
    }

    public void setMeetingTime(String meetingTime) {
        this.meetingTime = meetingTime;
    }

    public String getEmailContent() {
        return emailContent;
    }

    public void setEmailContent(String emailContent) {
        this.emailContent = emailContent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ScheduleDto{" +
                "id=" + id +
                ", position='" + position + '\'' +
                ", remark='" + remark + '\'' +
                ", meetingDate='" + meetingDate + '\'' +
                ", meetingTime='" + meetingTime + '\'' +
                ", hrId=" + hrId +
                ", candidateId=" + candidateId +
                ", status=" + status +
                ", emailContent='" + emailContent + '\'' +
                '}';
    }
}
