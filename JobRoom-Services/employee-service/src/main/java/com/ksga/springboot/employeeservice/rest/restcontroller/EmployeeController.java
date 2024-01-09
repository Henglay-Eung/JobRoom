package com.ksga.springboot.employeeservice.rest.restcontroller;

import com.ksga.springboot.employeeservice.configuration.BaseApi;
import com.ksga.springboot.employeeservice.model.cv.CVUpdatePublic;
import com.ksga.springboot.employeeservice.model.cv.CvDto;
import com.ksga.springboot.employeeservice.model.cv.CvResponse;
import com.ksga.springboot.employeeservice.model.employee.*;
import com.ksga.springboot.employeeservice.repository.EmployeeRepository;
import com.ksga.springboot.employeeservice.rest.message.*;
import com.ksga.springboot.employeeservice.service.Impl.EmployeeServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping(value = BaseApi.BASE_API_URL)
public class EmployeeController {

    private EmployeeServiceImpl employeeService;
    private EmployeeRepository employeeRepository;

    @Autowired
    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Autowired
    public void setEmployeeService(EmployeeServiceImpl employeeService) {
        this.employeeService = employeeService;
    }

    BCryptPasswordEncoder encoder;

    @Autowired
    public void setEncoder(BCryptPasswordEncoder encoder) {
        this.encoder = encoder;
    }

    private MessageProperties messageProperties;

    @Autowired
    public void setMessageProperties(MessageProperties messageProperties) {
        this.messageProperties = messageProperties;
    }

    //TODO: Exception
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {

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

        response.setMessage(messageProperties.insertError("Employee"));
        response.setError(errors);
        response.setStatus(HttpStatus.BAD_REQUEST);
        response.setTime(new Timestamp(System.currentTimeMillis()));

        return ResponseEntity.ok(response);
    }

    //TODO: get all employees
    @GetMapping("/employees")
    public ResponseEntity<BaseApiResponseWithPage<List<EmployeeResponse>>> getAllEmployees(Pageable pageable){

        BaseApiResponseWithPage<List<EmployeeResponse>> baseApiResponse = new BaseApiResponseWithPage<>();

        ModelMapper mapper = new ModelMapper();

        Page<EmployeeDto> employeeDtoPage =  employeeService.getAllEmployees(pageable);

        List<EmployeeDto> employeeDtoList = employeeDtoPage.getContent();

        List<EmployeeResponse> employeeResponseList = new ArrayList<>();

        for (EmployeeDto employeeDto : employeeDtoList) {
            employeeResponseList.add(mapper.map(employeeDto, EmployeeResponse.class));
        }
        if(!employeeDtoList.isEmpty()) {
            Pagination pagination = new Pagination();

            pagination.setPageNumber(employeeDtoPage.getNumber());
            pagination.setPageSize(employeeDtoPage.getSize());
            pagination.setTotalPages(employeeDtoPage.getTotalPages());
            pagination.setTotalElements(employeeDtoPage.getTotalElements());

            baseApiResponse.setMessage(messageProperties.selected("Employee"));
            baseApiResponse.setData(employeeResponseList);
            baseApiResponse.setPagination(pagination);
            baseApiResponse.setStatus(HttpStatus.OK);
        }
        else{
            baseApiResponse.setMessage(messageProperties.hasNoRecords("Employee"));
            baseApiResponse.setData(new ArrayList<>());
            baseApiResponse.setStatus(HttpStatus.NO_CONTENT);
        }
        baseApiResponse.setTimestamp(new Timestamp(System.currentTimeMillis()));
        return ResponseEntity.ok(baseApiResponse);
    }

    //TODO: get all employees
    @GetMapping("/employees/all")
    public ResponseEntity<BaseApiResponseWithPage<List<EmployeeResponse>>> getAllEmployeesRegardlessOfStatus(Pageable pageable){

        BaseApiResponseWithPage<List<EmployeeResponse>> baseApiResponse = new BaseApiResponseWithPage<>();

        ModelMapper mapper = new ModelMapper();

        Page<EmployeeDto> employeeDtoPage =  employeeService.getAllEmployeesRegardlessOfStatus(pageable);

        List<EmployeeDto> employeeDtoList = employeeDtoPage.getContent();

        List<EmployeeResponse> employeeResponseList = new ArrayList<>();

        for (EmployeeDto employeeDto : employeeDtoList) {
            employeeResponseList.add(mapper.map(employeeDto, EmployeeResponse.class));
        }
        if(!employeeDtoList.isEmpty()) {
            Pagination pagination = new Pagination();

            pagination.setPageNumber(employeeDtoPage.getNumber());
            pagination.setPageSize(employeeDtoPage.getSize());
            pagination.setTotalPages(employeeDtoPage.getTotalPages());
            pagination.setTotalElements(employeeDtoPage.getTotalElements());

            baseApiResponse.setMessage(messageProperties.selected("Employee"));
            baseApiResponse.setData(employeeResponseList);
            baseApiResponse.setPagination(pagination);
            baseApiResponse.setStatus(HttpStatus.OK);
        }
        else{
            baseApiResponse.setMessage(messageProperties.hasNoRecords("Employee"));
            baseApiResponse.setData(new ArrayList<>());
            baseApiResponse.setStatus(HttpStatus.NO_CONTENT);
        }
        baseApiResponse.setTimestamp(new Timestamp(System.currentTimeMillis()));
        return ResponseEntity.ok(baseApiResponse);
    }

    //TODO: insert employee
    @PostMapping("/employees")
    public ResponseEntity<BaseApiResponse<EmployeeResponse>> insertEmployee(
            @Valid @RequestBody EmployeeRequest employeeRequest) {

        BaseApiResponse<EmployeeResponse> baseApiResponse = new BaseApiResponse<>();
        ModelMapper mapper = new ModelMapper();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        Employee emp = employeeRepository.findEmployeeByEmail(employeeRequest.getEmail());

        if(emp==null) {

            employeeRequest.setPassword(encoder.encode(employeeRequest.getPassword()));

            EmployeeDto employeeDto = mapper.map(employeeRequest, EmployeeDto.class);
            EmployeeDto result = employeeService.saveEmployee(employeeDto);

            EmployeeResponse employeeResponse = mapper.map(result, EmployeeResponse.class);

            baseApiResponse.setMessage(messageProperties.inserted("Employee"));
            baseApiResponse.setData(employeeResponse);
            baseApiResponse.setStatus(HttpStatus.CREATED);
            baseApiResponse.setTimestamp(new Timestamp(System.currentTimeMillis()));
        }

        else if(emp.getEmail().equals(employeeRequest.getEmail())){
            baseApiResponse.setMessage("Duplicated email");
            baseApiResponse.setData(null);
            baseApiResponse.setStatus(HttpStatus.BAD_REQUEST);
            baseApiResponse.setTimestamp(new Timestamp(System.currentTimeMillis()));
        }
        else{
            return null;
        }

        return ResponseEntity.ok(baseApiResponse);
    }

    //TODO: get employee by id
    @GetMapping("/employees/{id}")
    public ResponseEntity<BaseApiResponse<EmployeeResponse>> getEmployeeById(@PathVariable int id) {

        BaseApiResponse<EmployeeResponse> baseApiResponse = new BaseApiResponse<>();
        ModelMapper mapper = new ModelMapper();

        EmployeeDto employeeDto = employeeService.getEmployeeById(id);

        if (employeeDto==null) {

            baseApiResponse.setMessage(messageProperties.hasNoRecord("Employee"));
            baseApiResponse.setData(null);
            baseApiResponse.setStatus(HttpStatus.NO_CONTENT);

        } else {

            EmployeeResponse employeeResponse = mapper.map(employeeDto, EmployeeResponse.class);
            baseApiResponse.setMessage(messageProperties.selectedOne("Employee"));
            baseApiResponse.setData(employeeResponse);
            baseApiResponse.setStatus(HttpStatus.OK);

        }

        baseApiResponse.setTimestamp(new Timestamp(System.currentTimeMillis()));
        return ResponseEntity.ok(baseApiResponse);
    }


    @GetMapping("/employees/uuid/{id}")
    public ResponseEntity<BaseApiResponse<EmployeeResponse>> getEmployeeById(@PathVariable("id") String uuid) {

        BaseApiResponse<EmployeeResponse> baseApiResponse = new BaseApiResponse<>();
        ModelMapper mapper = new ModelMapper();

        EmployeeDto employeeDto = employeeService.getAllEmployeeByUuid(uuid);

        if (employeeDto==null) {

            baseApiResponse.setMessage(messageProperties.hasNoRecord("Employee"));
            baseApiResponse.setData(null);
            baseApiResponse.setStatus(HttpStatus.NO_CONTENT);

        } else {

            EmployeeResponse employeeResponse = mapper.map(employeeDto, EmployeeResponse.class);
            baseApiResponse.setMessage(messageProperties.selectedOne("Employee"));
            baseApiResponse.setData(employeeResponse);
            baseApiResponse.setStatus(HttpStatus.OK);

        }

        baseApiResponse.setTimestamp(new Timestamp(System.currentTimeMillis()));
        return ResponseEntity.ok(baseApiResponse);
    }

    //TODO: Update employee by id
    @PutMapping("/employees/{id}")
    private ResponseEntity<BaseApiResponse<EmployeeResponse>> updatePostById(
            @PathVariable int id,
            @Valid @RequestBody EmployeeRequest employeeRequest) {

        BaseApiResponse<EmployeeResponse> baseApiResponse = new BaseApiResponse<>();
        ModelMapper mapper = new ModelMapper();

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        employeeRequest.setPassword(encoder.encode(employeeRequest.getPassword()));

        EmployeeDto employeeDto = mapper.map(employeeRequest, EmployeeDto.class);
        EmployeeDto employeeDtoResult = employeeService.updateEmployeeById(id, employeeDto);

        if (employeeDtoResult!=null) {
            EmployeeResponse employeeResponse = mapper.map(employeeDtoResult, EmployeeResponse.class);
            baseApiResponse.setMessage(messageProperties.updated("Employee"));
            baseApiResponse.setData(employeeResponse);
            baseApiResponse.setStatus(HttpStatus.OK);

        } else {
            baseApiResponse.setMessage(messageProperties.updatedError("Employee"));
            baseApiResponse.setStatus(HttpStatus.NO_CONTENT);
        }

        baseApiResponse.setTimestamp(new Timestamp(System.currentTimeMillis()));
        return ResponseEntity.ok(baseApiResponse);
    }

    //TODO: delete employee by id
    @DeleteMapping("/employees/{id}")
    private ResponseEntity<BaseApiResponse<EmployeeResponse>> deleteEmployeeById(@PathVariable("id") int id) {
        BaseApiResponse<EmployeeResponse> baseApiResponse = new BaseApiResponse<>();

        ModelMapper mapper = new ModelMapper();
        EmployeeDto employeeDto = employeeService.deleteEmployeeById(id);

        if (employeeDto!=null) {

            EmployeeResponse employeeResponse = mapper.map(employeeDto, EmployeeResponse.class);
            baseApiResponse.setMessage(messageProperties.deleted("Employee"));
            baseApiResponse.setData(employeeResponse);
            baseApiResponse.setStatus(HttpStatus.OK);

        } else {
            baseApiResponse.setMessage(messageProperties.deletedError("Employee", "employee"));
            baseApiResponse.setStatus(HttpStatus.NO_CONTENT);
        }

        baseApiResponse.setTimestamp(new Timestamp(System.currentTimeMillis()));
        return ResponseEntity.ok(baseApiResponse);
    }

    //TODO: get employee by name
    @GetMapping("search/employees")
    private ResponseEntity<BaseApiResponseWithPage<List<EmployeeResponse>>> getEmployeeByName(@RequestParam("name") String name, Pageable pageable){

        BaseApiResponseWithPage<List<EmployeeResponse>> baseApiResponse = new BaseApiResponseWithPage<>();

        ModelMapper mapper = new ModelMapper();

        Page<EmployeeDto> employeeDtoPage =  employeeService.getEmployeeByName(name, pageable);

        List<EmployeeResponse> employeeResponseList = new ArrayList<>();

        for (EmployeeDto employeeDto : employeeDtoPage) {
            employeeResponseList.add(mapper.map(employeeDto, EmployeeResponse.class));
        }

        if(employeeResponseList.isEmpty()) {
            baseApiResponse.setMessage(messageProperties.hasNoRecords("Employee"));
            baseApiResponse.setData(new ArrayList<>());
            baseApiResponse.setStatus(HttpStatus.NO_CONTENT);
        }
        else{
            Pagination pagination = new Pagination();

            pagination.setPageNumber(employeeDtoPage.getNumber());
            pagination.setPageSize(employeeDtoPage.getSize());
            pagination.setTotalPages(employeeDtoPage.getTotalPages());
            pagination.setTotalElements(employeeDtoPage.getTotalElements());

            baseApiResponse.setMessage(messageProperties.selected("Employee"));
            baseApiResponse.setData(employeeResponseList);
            baseApiResponse.setPagination(pagination);
            baseApiResponse.setStatus(HttpStatus.OK);
        }
        baseApiResponse.setTimestamp(new Timestamp(System.currentTimeMillis()));
        return ResponseEntity.ok(baseApiResponse);
    }

    //TODO: get employee by email
    @GetMapping("/employees/email")
    private ResponseEntity<BaseApiResponse<EmployeeResponse>> getEmployeeByName(@RequestParam("email") String email){

        BaseApiResponse<EmployeeResponse> baseApiResponse = new BaseApiResponse<>();

        ModelMapper mapper = new ModelMapper();

        EmployeeDto employeeDto =  employeeService.getEmployeeByEmail(email);

        if(employeeDto==null) {
            baseApiResponse.setMessage(messageProperties.hasNoRecords("Employee"));
            baseApiResponse.setData(null);
            baseApiResponse.setStatus(HttpStatus.NO_CONTENT);
        }
        else{
            EmployeeResponse employeeResponse = mapper.map(employeeDto,EmployeeResponse.class);
            baseApiResponse.setMessage(messageProperties.selected("Employee"));
            baseApiResponse.setData(employeeResponse);
            baseApiResponse.setStatus(HttpStatus.OK);
        }
        baseApiResponse.setTimestamp(new Timestamp(System.currentTimeMillis()));
        return ResponseEntity.ok(baseApiResponse);
    }

    // TODO: update employee status
    @PutMapping("/employees/status/{id}")
    public ResponseEntity<BaseApiResponse<EmployeeResponse>> updateEmployeeStatus(@PathVariable("id") int id, @RequestBody EmployeeUpdateStatus employeeUpdateStatus) {
        BaseApiResponse<EmployeeResponse> baseApiResponse = new BaseApiResponse<>();
        ModelMapper mapper = new ModelMapper();
        EmployeeDto employeeDto = employeeService.updateEmployeeStatus(employeeUpdateStatus.isStatus(),id);

        if (employeeDto == null) {
            baseApiResponse.setMessage(messageProperties.hasNoRecord("Employee"));
            baseApiResponse.setData(null);
            baseApiResponse.setStatus(HttpStatus.NO_CONTENT);
        }else{
            baseApiResponse.setMessage(messageProperties.updated("Employee"));
            baseApiResponse.setData(mapper.map(employeeDto, EmployeeResponse.class));
            baseApiResponse.setStatus(HttpStatus.CREATED);
        }
        baseApiResponse.setTimestamp(new Timestamp(System.currentTimeMillis()));
        return ResponseEntity.ok(baseApiResponse);
    }


//    //TODO: get all comments
//    @GetMapping("/empleee/search")
//    public ResponseEntity<BaseApiResponseWithPage<List<EmployeeResponse>>> searchEmployeeByUsername(@RequestParam("fullname") String name,Pageable pageable) {
//
//        BaseApiResponseWithPage<List<EmployeeResponse>> baseApiResponse = new BaseApiResponseWithPage<>();
//
//        ModelMapper mapper = new ModelMapper();
//
//        Page<EmployeeDto> employeeDtos = employeeService.findEmployeeByFullNameContainingIgnoreCase(name,pageable);
//
//        List<EmployeeDto> employeeDtoList = employeeDtos.getContent();
//
//        List<EmployeeResponse> commentResponseList = new ArrayList<>();
//
//        for (EmployeeDto employeeDto : employeeDtoList) {
//            commentResponseList.add(mapper.map(employeeDto, EmployeeResponse.class));
//        }
//
//        if (employeeDtoList.isEmpty()) {
//            baseApiResponse.setMessage(messageProperties.hasNoRecords("Employee"));
//            baseApiResponse.setData(new ArrayList<>());
//            baseApiResponse.setStatus(HttpStatus.NO_CONTENT);
//        } else {
//            Pagination pagination = new Pagination();
//
//            pagination.setPageNumber(employeeDtos.getNumber());
//            pagination.setPageSize(employeeDtos.getSize());
//            pagination.setTotalPages(employeeDtos.getTotalPages());
//            pagination.setTotalElements(employeeDtos.getTotalElements());
//
//            baseApiResponse.setMessage(messageProperties.selected("Employee"));
//            baseApiResponse.setData(commentResponseList);
//            baseApiResponse.setPagination(pagination);
//            baseApiResponse.setStatus(HttpStatus.OK);
//        }
//        baseApiResponse.setTimestamp(new Timestamp(System.currentTimeMillis()));
//        return ResponseEntity.ok(baseApiResponse);
//    }
}
