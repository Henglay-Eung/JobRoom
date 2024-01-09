package com.job_room.annoouncement_service;

import com.job_room.annoouncement_service.configuration.BaseAPI;
import com.job_room.annoouncement_service.model.Token;
import com.job_room.annoouncement_service.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

@SpringBootApplication
public class AnnouncementServiceApplication {



    public static void main(String[] args) {

        SpringApplication.run(AnnouncementServiceApplication.class, args);
    }

}
