package com.admin.admin_service.rest.restcontroller;

import com.admin.admin_service.rest.message.*;
import com.admin.admin_service.service.AdminService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class AdminRestController {

    private AdminService adminService;

    private MessageProperties messageProperties;

    ModelMapper modelMapper = new ModelMapper();

    RestTemplate restTemplate = new RestTemplate();

    @Autowired
    public void setMessageProperties(MessageProperties messageProperties) {
        this.messageProperties = messageProperties;
    }
    @Autowired
    public void setAdminService(AdminService adminService) {
        this.adminService = adminService;
    }

    //TODO: Exception =========================================================
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex){

        ErrorResponse response = new ErrorResponse();
        List<Object> errors = new ArrayList<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {

            Map<String, String> objectError = new HashMap<>();
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            objectError.put("field", fieldName);
            objectError.put("message", errorMessage);
            errors.add(objectError);
        });

        response.setMessage(messageProperties.insertError("Alumni"));
        response.setError(errors);
        response.setStatus(HttpStatus.BAD_REQUEST);
        response.setTime(new Timestamp(System.currentTimeMillis()));

        return ResponseEntity.ok(response);
    }

    //TODO: Select all companies =========================================================
    @GetMapping("/companies")
    public ResponseEntity<BaseApiResponseWithPagination> findAllCompanies(){
        ResponseEntity<BaseApiResponseWithPagination> response = restTemplate.getForEntity("http://35.197.132.204:30005/api/v1/companies",BaseApiResponseWithPagination.class);
        return response;
    }

    //TODO: Select company by id =========================================================
    @GetMapping("/companies/{id}")
    public ResponseEntity<BaseApiResponse> findCompanyById(@PathVariable int id){
        ResponseEntity<BaseApiResponse> response = restTemplate.getForEntity("http://35.197.132.204:30005/api/v1/companies/"+id,BaseApiResponse.class);
        return response;
    }

    //TODO: Select announcements by company =========================================================
    @GetMapping("/companies/{id}/announcements")
    public ResponseEntity<BaseApiResponseWithPagination> findAllAnnouncementsByCompany(@PathVariable int id){
        ResponseEntity<BaseApiResponseWithPagination> response = restTemplate.getForEntity("http://35.197.132.204:30005/api/v1/announcements/company/"+id,BaseApiResponseWithPagination.class);
        return response;
    }

    //TODO: Select announcements by id =========================================================
    @GetMapping("/companies/announcements/{id}")
    public ResponseEntity<BaseApiResponseWithPagination> findAnnouncementById(@PathVariable int id){
        ResponseEntity<BaseApiResponseWithPagination> response = restTemplate.getForEntity("http://35.197.132.204:30005/api/v1/announcements/"+id,BaseApiResponseWithPagination.class);
        return response;
    }

    //TODO: Ban company=========================================================
    @PostMapping("/companies/ban/{id}")
    public ResponseEntity<BaseApiResponse> banCompany(@PathVariable int id){
        ResponseEntity<BaseApiResponse> response = restTemplate.postForEntity("http://localhost:8080/api/v1/companies/ban/"+id,null,BaseApiResponse.class);
        return response;
    }

    //TODO: Unban company=========================================================
    @PostMapping("/companies/unban/{id}")
    public ResponseEntity<BaseApiResponse> unbanCompany(@PathVariable int id){
        ResponseEntity<BaseApiResponse> response = restTemplate.postForEntity("http://35.197.132.204:30005/api/v1/companies/unban/"+id,null,BaseApiResponse.class);
        return response;
    }


    //TODO: Ban announcement=========================================================
    @PostMapping("/announcements/ban/{id}")
    public ResponseEntity<BaseApiResponse> banAnnouncement(@PathVariable int id){
        ResponseEntity<BaseApiResponse> response = restTemplate.postForEntity("http://35.197.132.204:30005/api/v1/announcements/ban/"+id,null,BaseApiResponse.class);
        return response;
    }

    //TODO: Unban announcement=========================================================
    @PostMapping("/announcements/unban/{id}")
    public ResponseEntity<BaseApiResponse> unbanAnnouncement(@PathVariable int id){
        ResponseEntity<BaseApiResponse> response = restTemplate.postForEntity("http://35.197.132.204:30005/api/v1/announcements/unban/"+id,null,BaseApiResponse.class);
        return response;
    }
}
