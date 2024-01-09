package com.job_room.hr_service;

import com.job_room.hr_service.model.Token;
import com.job_room.hr_service.model.User;
import com.job_room.hr_service.model.announcement.AnnouncementResponse;
import com.job_room.hr_service.rest.message.BaseApiResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class HrServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(HrServiceApplication.class, args);
    }
}
