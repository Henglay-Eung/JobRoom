package com.ksga.springboot.employeeservice.rest.restcontroller;

import com.ksga.springboot.employeeservice.configuration.BaseApi;
import com.ksga.springboot.employeeservice.model.cv.*;
import com.ksga.springboot.employeeservice.repository.CvRepository;
import com.ksga.springboot.employeeservice.rest.message.*;
import com.ksga.springboot.employeeservice.service.Impl.CvServiceImpl;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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


@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping(value = BaseApi.BASE_API_URL)
public class CvController {
    private CvServiceImpl cvService;

    @Autowired
    public void setCvService(CvServiceImpl cvService) {
        this.cvService = cvService;
    }

    private CvRepository cvRepository;

    @Autowired
    public void setCvRepository(CvRepository cvRepository) {
        this.cvRepository = cvRepository;
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

        response.setMessage(messageProperties.insertError("Cv"));
        response.setError(errors);
        response.setStatus(HttpStatus.BAD_REQUEST);
        response.setTime(new Timestamp(System.currentTimeMillis()));

        return ResponseEntity.ok(response);
    }

    //TODO: get all posts
    @GetMapping("/cv")
    public ResponseEntity<BaseApiResponseWithPage<List<CvResponse>>> getAllCv(Pageable pageable) {
        BaseApiResponseWithPage<List<CvResponse>> baseApiResponse = new BaseApiResponseWithPage<>();

        ModelMapper mapper = new ModelMapper();
        Page<CvDto> cvDtoPage = cvService.getAllCv(pageable);

        List<CvDto> cvDtoList = cvDtoPage.getContent();

        List<CvResponse> cvResponseList = new ArrayList<>();

        for (CvDto cvDto : cvDtoList) {
            cvResponseList.add(mapper.map(cvDto, CvResponse.class));
        }

        if (cvDtoList.isEmpty()) {
            baseApiResponse.setMessage(messageProperties.hasNoRecords("Cv"));
            baseApiResponse.setData(new ArrayList<>());
            baseApiResponse.setStatus(HttpStatus.NO_CONTENT);
        } else {
            Pagination pagination = new Pagination();

            pagination.setPageNumber(cvDtoPage.getNumber());
            pagination.setPageSize(cvDtoPage.getSize());
            pagination.setTotalPages(cvDtoPage.getTotalPages());
            pagination.setTotalElements(cvDtoPage.getTotalElements());

            baseApiResponse.setMessage(messageProperties.selected("Cv"));
            baseApiResponse.setData(cvResponseList);
            baseApiResponse.setPagination(pagination);
            baseApiResponse.setStatus(HttpStatus.OK);
        }
        baseApiResponse.setTimestamp(new Timestamp(System.currentTimeMillis()));
        return ResponseEntity.ok(baseApiResponse);
    }

    //TODO: post cv
    @PostMapping("/cv")
    public ResponseEntity<BaseApiResponse<CvResponse>> insertCv(
            @Valid @RequestBody CvRequest cvRequest) {
        BaseApiResponse<CvResponse> baseApiResponse = new BaseApiResponse<>();
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        CvDto cvDto = mapper.map(cvRequest, CvDto.class);
        CvDto result = cvService.saveCv(cvDto);
        if (result != null) {
            CvResponse cvUploadResponse = mapper.map(result, CvResponse.class);

            baseApiResponse.setMessage(messageProperties.inserted("Cv"));
            baseApiResponse.setData(cvUploadResponse);
            baseApiResponse.setStatus(HttpStatus.CREATED);
        }else{
            baseApiResponse.setMessage(messageProperties.insertError("Cv"));
            baseApiResponse.setData(null);
            baseApiResponse.setStatus(HttpStatus.NO_CONTENT);
        }
        baseApiResponse.setTimestamp(new Timestamp(System.currentTimeMillis()));

        return ResponseEntity.ok(baseApiResponse);
    }

    //TODO: get cv by id
    @GetMapping("/cv/{id}")
    public ResponseEntity<BaseApiResponse<CvResponse>> getCvById(@PathVariable int id) {

        BaseApiResponse<CvResponse> baseApiResponse = new BaseApiResponse<>();
        ModelMapper mapper = new ModelMapper();

        CvDto cvDto = cvService.getCvById(id);

        if (cvDto == null) {

            baseApiResponse.setMessage(messageProperties.hasNoRecord("Cv"));
            baseApiResponse.setData(null);
            baseApiResponse.setStatus(HttpStatus.NO_CONTENT);

        } else {

            CvResponse cvResponse = mapper.map(cvDto, CvResponse.class);
            baseApiResponse.setMessage(messageProperties.selectedOne("Cv"));
            baseApiResponse.setData(cvResponse);
            baseApiResponse.setStatus(HttpStatus.OK);

        }

        baseApiResponse.setTimestamp(new Timestamp(System.currentTimeMillis()));
        return ResponseEntity.ok(baseApiResponse);
    }

    //TODO: update cv by id
    @PutMapping("/cv/{id}")
    private ResponseEntity<BaseApiResponse<CvResponse>> updateCvById(
            @PathVariable int id,
            @Valid @RequestBody CVUpdate cvUpdate) {

        BaseApiResponse<CvResponse> baseApiResponse = new BaseApiResponse<>();
        ModelMapper mapper = new ModelMapper();

        CvDto cvDto = mapper.map(cvUpdate, CvDto.class);
        CvDto cvDtoResult = cvService.updateCvById(id, cvDto);

        if (cvDtoResult != null) {
            CvResponse cvResponse = mapper.map(cvDtoResult, CvResponse.class);
            baseApiResponse.setMessage(messageProperties.updated("Cv"));
            baseApiResponse.setData(cvResponse);
            baseApiResponse.setStatus(HttpStatus.OK);

        } else {
            baseApiResponse.setMessage(messageProperties.updatedError("Cv"));
            baseApiResponse.setStatus(HttpStatus.NO_CONTENT);
        }

        baseApiResponse.setTimestamp(new Timestamp(System.currentTimeMillis()));
        return ResponseEntity.ok(baseApiResponse);
    }

    //TODO: delete cv by id
    @DeleteMapping("/cv/{id}")
    private ResponseEntity<BaseApiResponse<CvResponse>> deleteCvById(@PathVariable("id") int id) {
        BaseApiResponse<CvResponse> baseApiResponse = new BaseApiResponse<>();

        ModelMapper mapper = new ModelMapper();
        CvDto cvDto = cvService.deleteCvById(id);

        if (cvDto != null) {

            CvResponse cvResponse = mapper.map(cvDto, CvResponse.class);
            baseApiResponse.setMessage(messageProperties.deleted("Cv"));
            baseApiResponse.setData(cvResponse);
            baseApiResponse.setStatus(HttpStatus.OK);

        } else {
            baseApiResponse.setMessage(messageProperties.deletedError("Cv", "cv"));
            baseApiResponse.setStatus(HttpStatus.BAD_REQUEST);
        }

        baseApiResponse.setTimestamp(new Timestamp(System.currentTimeMillis()));
        return ResponseEntity.ok(baseApiResponse);
    }

    //TODO: get post by caption
    @GetMapping("/search/cvs")
    public ResponseEntity<BaseApiResponseWithPage<List<CvResponse>>> getCvByName(@RequestParam("name") String name, Pageable pageable) {

        BaseApiResponseWithPage<List<CvResponse>> baseApiResponse = new BaseApiResponseWithPage<>();
        ModelMapper mapper = new ModelMapper();

        Page<CvDto> cvDtoPage = cvService.searchCvByName(name, pageable);

        List<CvResponse> cvResponseList = new ArrayList<>();

        for (CvDto cvDto : cvDtoPage) {
            cvResponseList.add(mapper.map(cvDto, CvResponse.class));
        }

        if (cvDtoPage.isEmpty()) {
            baseApiResponse.setMessage(messageProperties.hasNoRecords("Cv"));
            baseApiResponse.setData(new ArrayList<>());
            baseApiResponse.setStatus(HttpStatus.NO_CONTENT);
        } else {
            Pagination pagination = new Pagination();

            pagination.setPageNumber(cvDtoPage.getNumber());
            pagination.setPageSize(cvDtoPage.getSize());
            pagination.setTotalPages(cvDtoPage.getTotalPages());
            pagination.setTotalElements(cvDtoPage.getTotalElements());

            baseApiResponse.setMessage(messageProperties.selected("Cv"));
            baseApiResponse.setData(cvResponseList);
            baseApiResponse.setPagination(pagination);
            baseApiResponse.setStatus(HttpStatus.OK);
        }
        baseApiResponse.setTimestamp(new Timestamp(System.currentTimeMillis()));
        return ResponseEntity.ok(baseApiResponse);
    }

    @PutMapping("/cv/isPublic/{id}")
    public ResponseEntity<BaseApiResponse<CvResponse>> updateIsPublic(@PathVariable("id") int id, @RequestBody CVUpdatePublic cvUpdatePublic) {
        BaseApiResponse<CvResponse> baseApiResponse = new BaseApiResponse<>();
        ModelMapper mapper = new ModelMapper();
        CvDto cvDto = cvService.updateCVPublic(cvUpdatePublic.isPublic(),id);

        if (cvDto == null) {
            baseApiResponse.setMessage(messageProperties.hasNoRecord("Cv"));
            baseApiResponse.setData(null);
            baseApiResponse.setStatus(HttpStatus.NO_CONTENT);
        }else{
            baseApiResponse.setMessage(messageProperties.updated("Cv"));
            baseApiResponse.setData(mapper.map(cvDto, CvResponse.class));
            baseApiResponse.setStatus(HttpStatus.CREATED);
        }
        baseApiResponse.setTimestamp(new Timestamp(System.currentTimeMillis()));
        return ResponseEntity.ok(baseApiResponse);
    }
    @GetMapping("/cv/userId/{id}")
    public ResponseEntity<BaseApiResponse<List<CvResponse>>> getCvByUserId(@PathVariable("id") int id) {
        BaseApiResponse<List<CvResponse>> baseApiResponse = new BaseApiResponse<>();
        ModelMapper mapper = new ModelMapper();

        List<CvDto> cvDtoList =  cvService.getCvByUserId(id);
        List<CvResponse> cvResponses = new ArrayList<>();

        for (CvDto cvDto : cvDtoList) {
            cvResponses.add(mapper.map(cvDto, CvResponse.class));
        }

        if (cvResponses.isEmpty()) {
            baseApiResponse.setMessage(messageProperties.hasNoRecords("Cv"));
            baseApiResponse.setData(new ArrayList<>());
            baseApiResponse.setStatus(HttpStatus.NO_CONTENT);
        } else {
            baseApiResponse.setMessage(messageProperties.selected("Cv"));
            baseApiResponse.setData(cvResponses);
            baseApiResponse.setStatus(HttpStatus.OK);
        }
        baseApiResponse.setTimestamp(new Timestamp(System.currentTimeMillis()));
        return ResponseEntity.ok(baseApiResponse);
    }

}
