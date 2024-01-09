package com.ksga.auth.service.Imp;

import com.ksga.auth.message.BaseApiResponse;
import com.ksga.auth.model.Token;
import com.ksga.auth.model.User;
import com.ksga.auth.model.company.CompanyRequest;
import com.ksga.auth.model.company.CompanyResponse;
import com.ksga.auth.model.employee.EmployeeRequest;
import com.ksga.auth.model.employee.EmployeeResponse;
import com.ksga.auth.model.user.UserDetails;
import com.ksga.auth.model.user.UserDetailsDto;
import com.ksga.auth.repository.UserDetailsRepository;
import com.ksga.auth.service.RegisterService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

@Service
public class RegisterServiceImp implements RegisterService {

    @Autowired
    UserDetailsRepository userDetailsRepository;


    private RestTemplate restTemplate = new RestTemplate();

    ModelMapper modelMapper = new ModelMapper();

    HttpHeaders headers;

    HttpEntity entity;

    Token token = new Token();

    @Override
    public UserDetailsDto registerCompany(CompanyRequest companyRequest) {

        ParameterizedTypeReference<BaseApiResponse<CompanyResponse>> parameterizedTypeReference =
                new ParameterizedTypeReference<BaseApiResponse<CompanyResponse>>() {
                };

        getToken();

        headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        entity = new HttpEntity<CompanyRequest>(companyRequest, headers);
        headers.add("Authorization", "Bearer "+token.getAccess_token());

        ResponseEntity<BaseApiResponse<CompanyResponse>> result = restTemplate.exchange("https://gateway.kshrd-ite.com/hr/companies",HttpMethod.POST,entity,parameterizedTypeReference);

        CompanyResponse companyResponse = result.getBody().getData();


        UserDetails userDetail = new UserDetails();
        userDetail.setEmail(companyResponse.getPrimaryEmail());
        userDetail.setPassword(companyResponse.getPassword());
        userDetail.setRole("company");
        userDetail.setBanned(companyResponse.isBanned());
        userDetail.setStatus(true);
        userDetail.setUserId(companyResponse.getId());

        UserDetails userDetails = userDetailsRepository.save(userDetail);

        UserDetailsDto userDetailsDto = modelMapper.map(userDetails,UserDetailsDto.class);


        return userDetailsDto;
    }

    @Override
    public UserDetailsDto registerEmployee(EmployeeRequest employeeRequest) {

        ParameterizedTypeReference<BaseApiResponse<EmployeeResponse>> parameterizedTypeReference =
                new ParameterizedTypeReference<>() {
                };

        getToken();

        headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        entity = new HttpEntity<>(employeeRequest, headers);
        headers.add("Authorization", "Bearer "+token.getAccess_token());

        ResponseEntity<BaseApiResponse<EmployeeResponse>> result = restTemplate.exchange("https://gateway.kshrd-ite.com/employee/employees",HttpMethod.POST,entity,parameterizedTypeReference);

        System.out.println(result.getBody().getMessage());
        EmployeeResponse employeeResponse = Objects.requireNonNull(result.getBody()).getData();
        System.out.println(employeeResponse);

        UserDetails userDetail = new UserDetails();
        userDetail.setEmail(employeeResponse.getEmail());
        userDetail.setPassword(employeeRequest.getPassword());
        userDetail.setRole("employee");
        userDetail.setBanned(false);
        userDetail.setStatus(true);
        userDetail.setUserId(employeeResponse.getId());


        UserDetails userDetails = userDetailsRepository.save(userDetail);

        return modelMapper.map(userDetails,UserDetailsDto.class);

    }

    @Override
    public UserDetailsDto login(String email, String password) {

        ParameterizedTypeReference<BaseApiResponse<CompanyResponse>> parameterizedTypeReference =
                new ParameterizedTypeReference<BaseApiResponse<CompanyResponse>>() {
                };

        headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        entity = new HttpEntity<>(headers);

        UserDetails userDetails = userDetailsRepository.findByEmailEqualsAndPasswordEquals(email,password);


        if(userDetails==null)
            return null;
        UserDetailsDto userDetailsDto = modelMapper.map(userDetails,UserDetailsDto.class);

        return userDetailsDto;
    }

    @Override
    public UserDetailsDto changePasswordForCompany(CompanyRequest companyRequest) {

        UserDetails currentUser = userDetailsRepository.findUserDetailsByEmail(companyRequest.getPrimaryEmail());

        currentUser.setPassword(companyRequest.getPassword());

        System.out.println(currentUser.toString());

        UserDetails userDetails = userDetailsRepository.save(currentUser);

        if(userDetails==null)
            return null;

        UserDetailsDto userDetailsDto = modelMapper.map(userDetails,UserDetailsDto.class);

        return userDetailsDto;
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
