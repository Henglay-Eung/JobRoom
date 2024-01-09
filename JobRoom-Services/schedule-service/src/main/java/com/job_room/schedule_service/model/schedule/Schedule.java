package com.job_room.schedule_service.model.schedule;

import org.hibernate.type.TimestampType;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "jr_schedules")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String position;
    private String remark;

    @Column(name = "meeting_date")
    private String meetingDate;

    @Column(name = "meeting_time")
    private String meetingTime;

    @Column(name = "hr_id")
    private int hrId;
    @Column(name = "email_contant")
    private String emailContent;
    @Column(name = "candidate_id")
    private int candidateId;
    private boolean status;
    public Schedule(){}

    public Schedule(int id, String position, String remark, String meetingDate, String meetingTime, int hrId, String emailContent, int candidateId, boolean status) {
        this.id = id;
        this.position = position;
        this.remark = remark;
        this.meetingDate = meetingDate;
        this.meetingTime = meetingTime;
        this.hrId = hrId;
        this.emailContent = emailContent;
        this.candidateId = candidateId;
        this.status = status;
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

    public String getEmailContent() {
        return emailContent;
    }

    public void setEmailContent(String emailContent) {
        this.emailContent = emailContent;
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
}
