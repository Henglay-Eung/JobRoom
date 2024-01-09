package com.ksga.auth.restcontroller;

import com.ksga.auth.message.BaseApiResponse;
import com.ksga.auth.message.ErrorResponse;
import com.ksga.auth.message.MessageProperties;
import com.ksga.auth.model.company.CompanyRequest;
import com.ksga.auth.model.employee.EmployeeDto;
import com.ksga.auth.model.employee.EmployeeRequest;
import com.ksga.auth.model.user.UserDetails;
import com.ksga.auth.model.user.UserDetailsDto;
import com.ksga.auth.model.user.UserDetailsRequest;
import com.ksga.auth.model.user.UserDetailsResponse;
import com.ksga.auth.repository.UserDetailsRepository;
import com.ksga.auth.service.RegisterService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AuthController {

    private MessageProperties messageProperties;

    @Autowired
    public void setMessageProperties(MessageProperties messageProperties) {
        this.messageProperties = messageProperties;
    }

    private RegisterService registerService;

    private UserDetailsRepository userDetailsRepository;

    @Autowired
    public void setUserDetailsRepository(UserDetailsRepository userDetailsRepository) {
        this.userDetailsRepository = userDetailsRepository;
    }

    @Autowired
    public void setRegisterService(RegisterService registerService) {
        this.registerService = registerService;
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

        response.setMessage(messageProperties.insertError("user"));
        response.setError(errors);
        response.setStatus(HttpStatus.BAD_REQUEST);
        response.setTime(new Timestamp(System.currentTimeMillis()));

        return ResponseEntity.ok(response);
    }


    @PostMapping("/register/hr")
    public ResponseEntity<BaseApiResponse<UserDetailsResponse>> registerHR(@RequestBody @Valid CompanyRequest companyRequest){

        BaseApiResponse<UserDetailsResponse> response = new BaseApiResponse<>();
        ModelMapper mapper = new ModelMapper();

        System.out.println(companyRequest);
        UserDetails userDetails = userDetailsRepository.findUserDetailsByEmail(companyRequest.getPrimaryEmail());
        System.out.println(userDetails);

        if(userDetails==null) {
            UserDetailsDto result = registerService.registerCompany(companyRequest);
            UserDetailsResponse userDetailsResponse = mapper.map(result, UserDetailsResponse.class);
            response.setMessage(messageProperties.inserted("user"));
            userDetailsResponse.setId(result.getId());
            response.setData(userDetailsResponse);
            response.setStatus(HttpStatus.CREATED);

            response.setTime(new Timestamp(System.currentTimeMillis()));
        }

        else if(userDetails.getEmail().equals(companyRequest.getPrimaryEmail())){
            response.setMessage("Duplicated email");
            response.setData(null);
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setTime(new Timestamp(System.currentTimeMillis()));
        }
        else{
            return null;
        }
        return ResponseEntity.ok(response);

    }

    @PostMapping("/update/hr")
    public ResponseEntity<BaseApiResponse<UserDetailsResponse>> changePasswordHR(@RequestBody @Valid CompanyRequest companyRequest){

        BaseApiResponse<UserDetailsResponse> response = new BaseApiResponse<>();
        ModelMapper mapper = new ModelMapper();

        UserDetailsDto userDetailsDto = registerService.changePasswordForCompany(companyRequest);

        if(userDetailsDto!=null) {

            UserDetailsResponse userDetailsResponse = mapper.map(userDetailsDto,UserDetailsResponse.class);
            response.setMessage(messageProperties.updated("user"));
            response.setData(userDetailsResponse);
            response.setStatus(HttpStatus.OK);
            response.setTime(new Timestamp(System.currentTimeMillis()));
            response.setData(userDetailsResponse);
        }
        else{
            response.setMessage(messageProperties.updatedError("user"));
            response.setData(null);
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setTime(new Timestamp(System.currentTimeMillis()));
        }
        return ResponseEntity.ok(response);

    }



    @PostMapping("/register/employee")
    public ResponseEntity<BaseApiResponse<UserDetailsResponse>> registerEmployee(@RequestBody @Valid EmployeeRequest employeeRequest){

        BaseApiResponse<UserDetailsResponse> response = new BaseApiResponse<>();
        ModelMapper mapper = new ModelMapper();

        UserDetails userDetails = userDetailsRepository.findUserDetailsByEmail(employeeRequest.getEmail());
        System.out.println(userDetails);

        if(userDetails==null) {
            UserDetailsDto result = registerService.registerEmployee(employeeRequest);
            UserDetailsResponse userDetailsResponse = mapper.map(result, UserDetailsResponse.class);
            response.setMessage(messageProperties.inserted("user"));
            userDetailsResponse.setId(result.getId());
            response.setData(userDetailsResponse);
            response.setStatus(HttpStatus.CREATED);

            response.setTime(new Timestamp(System.currentTimeMillis()));
        }

        else if(userDetails.getEmail().equals(employeeRequest.getEmail())){
            response.setMessage("Duplicated email");
            response.setData(null);
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setTime(new Timestamp(System.currentTimeMillis()));
        }
        else{
            return null;
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getCompanyUser/{id}")
    public ResponseEntity<BaseApiResponse<UserDetailsResponse>> getUser(@PathVariable int id){

        BaseApiResponse<UserDetailsResponse> response = new BaseApiResponse<>();
        ModelMapper mapper = new ModelMapper();

        UserDetails userDetails = userDetailsRepository.findByUserIdAndRoleEquals(id,"company");

        if(userDetails!=null) {
            UserDetailsResponse userDetailsResponse = mapper.map(userDetails, UserDetailsResponse.class);
            response.setMessage(messageProperties.inserted("user"));
            response.setData(userDetailsResponse);
            response.setStatus(HttpStatus.CREATED);
            response.setTime(new Timestamp(System.currentTimeMillis()));

        }
        else{
            response.setMessage(messageProperties.hasNoRecord("user"));
            response.setData(null);
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setTime(new Timestamp(System.currentTimeMillis()));


        }
        return ResponseEntity.ok(response);
    }



    //TODO: Insert company =========================================================
    @PostMapping("/login")
    public ResponseEntity<BaseApiResponse<UserDetailsResponse>> insertCompany(
            @Valid @RequestBody UserDetailsRequest userDetailsRequest) {


        BaseApiResponse<UserDetailsResponse> response = new BaseApiResponse<>();
        ModelMapper mapper = new ModelMapper();

        UserDetailsDto companyDto = registerService.login(userDetailsRequest.getEmail(),userDetailsRequest.getPassword());
        if(companyDto==null){
            response.setMessage("username or password is incorrect");
            response.setData(null);
            response.setStatus(HttpStatus.BAD_REQUEST);
        }
        else {
            UserDetailsResponse userDetailsResponse = mapper.map(companyDto, UserDetailsResponse.class);
            response.setMessage(messageProperties.inserted("User"));
            userDetailsResponse.setId(companyDto.getId());
            response.setData(userDetailsResponse);
            response.setStatus(HttpStatus.CREATED);
        }

        response.setTime(new Timestamp(System.currentTimeMillis()));
        return ResponseEntity.ok(response);
    }
}
