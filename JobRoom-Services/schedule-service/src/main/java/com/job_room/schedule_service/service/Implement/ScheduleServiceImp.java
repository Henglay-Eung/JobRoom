package com.job_room.schedule_service.service.Implement;

import com.job_room.schedule_service.model.Dto.ScheduleDto;
import com.job_room.schedule_service.model.Token;
import com.job_room.schedule_service.model.User;
import com.job_room.schedule_service.model.schedule.Schedule;
import com.job_room.schedule_service.model.reponse.ScheduleCandidate;
import com.job_room.schedule_service.repository.ScheduleRepository;
import com.job_room.schedule_service.rest.message.BaseApiResponse;
import com.job_room.schedule_service.service.ScheduleService;
import com.job_room.schedule_service.util.PaginationUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Optional;
import java.util.Properties;
import java.util.UUID;

@Service
public class ScheduleServiceImp implements ScheduleService {
    private ScheduleRepository scheduleRepository;
    private ModelMapper modelMapper = new ModelMapper();
    private RestTemplate restTemplate;
    HttpHeaders headers;
    HttpEntity<String> entity;

    Token token = new Token();

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Autowired
    public void setScheduleRepository(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    //TODO: Insert Schedule =========================================================
    @Override
    public ScheduleDto insert(ScheduleDto scheduleDto) {

        Schedule schedule = modelMapper.map(scheduleDto, Schedule.class);
        schedule.setStatus(true);
        ScheduleDto scheduleDto1 = modelMapper.map(scheduleRepository.save(schedule), ScheduleDto.class);
        return scheduleDto1;


    }

    //TODO: Select all Schedule By Status =========================================================
    @Override
    public Page<ScheduleDto> selectAllByStatus(int id, int page, int pageSize) {

        Pageable pageable = PageRequest.of(page, pageSize);

        Page<Schedule> schedules = scheduleRepository.findAllByHrIdAndStatus(id, true, pageable);

        PaginationUtils<ScheduleDto, Page<Schedule>> paging = new PaginationUtils<>(ScheduleDto.class);
        paging.setData(schedules);

        Page<ScheduleDto> scheduleDtos = paging;

        return scheduleDtos;

    }

    //TODO: Delete Schedule =========================================================
    @Override
    public ScheduleDto delete(int id) {

        ScheduleDto scheduleDto = selectByIdAndStatus(id);

        if (scheduleDto != null) {

            Schedule schedule = modelMapper.map(scheduleDto, Schedule.class);
            schedule.setStatus(false);
            scheduleRepository.save(schedule);

            return scheduleDto;
        } else {
            return null;
        }
    }

    //TODO: Select Schedule By Id and Status =========================================================
    @Override
    public ScheduleDto selectByIdAndStatus(int id) {

        Optional<Schedule> schedule = scheduleRepository.findByIdAndStatusIsTrue(id);
        ScheduleDto scheduleDto;

        if (schedule.isEmpty()) {
            return null;

        } else {
            scheduleDto = modelMapper.map(schedule.get(), ScheduleDto.class);
            return scheduleDto;
        }
    }

    //TODO: Update Schedule =========================================================
    @Override
    public ScheduleDto update(int id, ScheduleDto scheduleDto) {

        ScheduleDto scheduleDtoToUpdate = selectByIdAndStatus(id);

        if (scheduleDtoToUpdate != null) {

            scheduleDtoToUpdate.setId(id);
            scheduleDtoToUpdate.setCandidateId(scheduleDto.getCandidateId());
            scheduleDtoToUpdate.setHrId(scheduleDto.getHrId());
            scheduleDtoToUpdate.setMeetingDate(scheduleDto.getMeetingDate());
            scheduleDtoToUpdate.setPosition(scheduleDto.getPosition());
            scheduleDtoToUpdate.setRemark(scheduleDto.getRemark());
            scheduleDtoToUpdate.setEmailContent(scheduleDto.getEmailContent());
            scheduleDtoToUpdate.setMeetingTime(scheduleDto.getMeetingTime());
            Schedule schedule = modelMapper.map(scheduleDtoToUpdate, Schedule.class);
            scheduleRepository.save(schedule);

            return scheduleDtoToUpdate;
        } else {
            return null;
        }

    }

    @Override
    public ScheduleCandidate selectCandidateById(int id) {

        ParameterizedTypeReference<BaseApiResponse<ScheduleCandidate>> scheduleCandidate =
                new ParameterizedTypeReference<BaseApiResponse<ScheduleCandidate>>() {
                };

        getToken();

        headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        entity = new HttpEntity<String>("parameters", headers);
        headers.add("Authorization", "Bearer "+token.getAccess_token());
        ResponseEntity<BaseApiResponse<ScheduleCandidate>> result = restTemplate.exchange("https://gateway.kshrd-ite.com/job/candidates/" + id, HttpMethod.GET, entity, scheduleCandidate);

        if (result.getBody().getData() == null)
            return null;
        return result.getBody().getData();
    }

    JavaMailSenderImpl mailSender;
    Properties properties;

    @Override
    public boolean notifyCandidate(String candidateEmail, String email, String password, String emailContent,String companyName,String position,String date) throws MessagingException {

        setEmailProperties(email, password);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        String subject = companyName + "-"+ position + "Interview-" + date;

        helper.setSubject(subject);
        helper.setFrom(email);
        helper.setTo(candidateEmail);
        helper.setText(emailContent, true);


        mailSender.send(message);

        return true;
    }

    public void setEmailProperties(String email, String password) {

        mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername(email);
        mailSender.setPassword(password);
        properties = new Properties();
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        mailSender.setJavaMailProperties(properties);
    }

    @Override
    public int count(int hrId) {
        return this.scheduleRepository.countSchedulesByHrIdAndStatusIsTrue(hrId);
    }

    public void getToken(){
        RestTemplate restTemplate = new RestTemplate();

        String url = "https://gateway.kshrd-ite.com/root/post/oauth2/token";
        String clientId = "6y1yGsCwJCn9mmbrs6Vh7LFW7H8dFO5m";
        String redirectUri = "http://35.197.132.204:31000";
        String username = "jobroom";
        String password = "jobroom";
        String clientSecret = "VbI7nYEG2lD1ct65rILNp9sdh9aALIar";
        String provisionKey = "AU3p0FF7yU4mA8thdUGG8xdpOV45Iwjb";
        String authenticated_userid = "jobroom";
        String scope = "email";


        HttpHeaders headers;

        HttpEntity<String> entity;

        headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        entity = new HttpEntity<String>("parameters", headers);

        token = restTemplate.postForObject(url,new User(), Token.class);

        System.out.println(token);

//        final String filepath= BaseAPI.TOKEN_PATH;
//
//        try {
//
//            FileOutputStream fileOut = new FileOutputStream(filepath);
//            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
//            objectOut.writeObject(token);
//            objectOut.close();
//            System.out.println("The Object  was successfully written to a file");
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
    }
}
