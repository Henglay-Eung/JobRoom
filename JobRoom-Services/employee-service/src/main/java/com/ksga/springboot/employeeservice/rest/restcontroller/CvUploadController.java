package com.ksga.springboot.employeeservice.rest.restcontroller;

import com.ksga.springboot.employeeservice.configuration.BaseApi;
import com.ksga.springboot.employeeservice.model.cv.CvDto;
import com.ksga.springboot.employeeservice.model.cv.CvResponse;
import com.ksga.springboot.employeeservice.model.cvupload.*;
import com.ksga.springboot.employeeservice.repository.CvUploadRepository;
import com.ksga.springboot.employeeservice.rest.message.*;
import com.ksga.springboot.employeeservice.service.Impl.CvUploadServiceImpl;
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
public class CvUploadController {

    private CvUploadServiceImpl cvUploadService;

    @Autowired
    public void setCvUploadService(CvUploadServiceImpl cvUploadService) {
        this.cvUploadService = cvUploadService;
    }

    private CvUploadRepository cvUploadRepository;

    @Autowired
    public void setCvUploadRepository(CvUploadRepository cvUploadRepository) {
        this.cvUploadRepository = cvUploadRepository;
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

        response.setMessage(messageProperties.insertError("Cv-upload"));
        response.setError(errors);
        response.setStatus(HttpStatus.BAD_REQUEST);
        response.setTime(new Timestamp(System.currentTimeMillis()));

        return ResponseEntity.ok(response);
    }

    //TODO: get all posts
    @GetMapping("/cv-upload")
    public ResponseEntity<BaseApiResponseWithPage<List<CvUploadResponse>>> getAllCvUpload(Pageable pageable) {
        BaseApiResponseWithPage<List<CvUploadResponse>> baseApiResponse = new BaseApiResponseWithPage<>();

        ModelMapper mapper = new ModelMapper();
        Page<CvUploadDto> cvUploadDtoPage = cvUploadService.getAllCvUpload(pageable);
        List<CvUploadDto> cvUploadDtoList = cvUploadDtoPage.getContent();

        List<CvUploadResponse> cvUploadResponseList = new ArrayList<>();

        for (CvUploadDto cvUploadDto : cvUploadDtoList) {
            cvUploadResponseList.add(mapper.map(cvUploadDto, CvUploadResponse.class));
        }

        if (cvUploadDtoList.isEmpty()) {
            baseApiResponse.setMessage(messageProperties.hasNoRecords("Cv-upload"));
            baseApiResponse.setData(new ArrayList<>());
            baseApiResponse.setStatus(HttpStatus.NO_CONTENT);
        } else {
            Pagination pagination = new Pagination();

            pagination.setPageNumber(cvUploadDtoPage.getNumber());
            pagination.setPageSize(cvUploadDtoPage.getSize());
            pagination.setTotalPages(cvUploadDtoPage.getTotalPages());
            pagination.setTotalElements(cvUploadDtoPage.getTotalElements());

            baseApiResponse.setMessage(messageProperties.selected("Cv-upload"));
            baseApiResponse.setData(cvUploadResponseList);
            baseApiResponse.setPagination(pagination);
            baseApiResponse.setStatus(HttpStatus.OK);
        }
        baseApiResponse.setTimestamp(new Timestamp(System.currentTimeMillis()));
        return ResponseEntity.ok(baseApiResponse);
    }

    //TODO: post cv-upload
    @PostMapping("/cv-upload")
    public ResponseEntity<BaseApiResponse<CvUploadResponse>> insertCvUpload(
            @Valid @RequestBody CvUploadRequest cvUploadRequest) {

        BaseApiResponse<CvUploadResponse> baseApiResponse = new BaseApiResponse<>();
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        CvUploadDto cvUploadDto = mapper.map(cvUploadRequest, CvUploadDto.class);
        CvUploadDto result = cvUploadService.saveCvUpload(cvUploadDto);
        if (result != null) {

            CvUploadResponse cvUploadResponse = mapper.map(result, CvUploadResponse.class);

            baseApiResponse.setMessage(messageProperties.inserted("Cv-upload"));
            baseApiResponse.setData(cvUploadResponse);
            baseApiResponse.setStatus(HttpStatus.CREATED);
        }else{
            baseApiResponse.setMessage(messageProperties.insertError("Cv-upload"));
            baseApiResponse.setData(null);
            baseApiResponse.setStatus(HttpStatus.BAD_REQUEST);
        }
        baseApiResponse.setTimestamp(new Timestamp(System.currentTimeMillis()));

        return ResponseEntity.ok(baseApiResponse);
    }

    //TODO: get cv-upload by id
    @GetMapping("/cv-upload/{id}")
    public ResponseEntity<BaseApiResponse<CvUploadResponse>> getCvUploadById(@PathVariable int id) {

        BaseApiResponse<CvUploadResponse> baseApiResponse = new BaseApiResponse<>();
        ModelMapper mapper = new ModelMapper();

        CvUploadDto cvUploadDto = cvUploadService.getCvUploadById(id);

        if (cvUploadDto == null) {

            baseApiResponse.setMessage(messageProperties.hasNoRecord("Cv-upload"));
            baseApiResponse.setData(null);
            baseApiResponse.setStatus(HttpStatus.NO_CONTENT);

        } else {

            CvUploadResponse cvUploadResponse = mapper.map(cvUploadDto, CvUploadResponse.class);
            baseApiResponse.setMessage(messageProperties.selectedOne("Cv-upload"));
            baseApiResponse.setData(cvUploadResponse);
            baseApiResponse.setStatus(HttpStatus.OK);

        }

        baseApiResponse.setTimestamp(new Timestamp(System.currentTimeMillis()));
        return ResponseEntity.ok(baseApiResponse);
    }

    //TODO: update cv-upload by id
    @PutMapping("/cv-upload/{id}")
    private ResponseEntity<BaseApiResponse<CvUploadResponse>> updateCvUploadById(
            @PathVariable int id,
            @Valid @RequestBody CvUploadUpdateRequest cvUploadUpdateRequest) {

        BaseApiResponse<CvUploadResponse> baseApiResponse = new BaseApiResponse<>();
        ModelMapper mapper = new ModelMapper();

        CvUploadDto cvUploadDto = mapper.map(cvUploadUpdateRequest, CvUploadDto.class);
        CvUploadDto cvUploadDtoResult = cvUploadService.updateCvUploadById(id, cvUploadDto);

        if (cvUploadDtoResult != null) {
            CvUploadResponse cvUploadResponse = mapper.map(cvUploadDtoResult, CvUploadResponse.class);
            baseApiResponse.setMessage(messageProperties.updated("Cv-upload"));
            baseApiResponse.setData(cvUploadResponse);
            baseApiResponse.setStatus(HttpStatus.OK);

        } else {
            baseApiResponse.setMessage(messageProperties.updatedError("Cv-upload"));
            baseApiResponse.setStatus(HttpStatus.NO_CONTENT);
        }

        baseApiResponse.setTimestamp(new Timestamp(System.currentTimeMillis()));
        return ResponseEntity.ok(baseApiResponse);
    }

    //TODO: delete cv-upload by id
    @DeleteMapping("/cv-upload/{id}")
    private ResponseEntity<BaseApiResponse<CvUploadResponse>> deleteCvUploadById(@PathVariable("id") int id) {
        BaseApiResponse<CvUploadResponse> baseApiResponse = new BaseApiResponse<>();

        ModelMapper mapper = new ModelMapper();
        CvUploadDto cvUploadDto = cvUploadService.deleteCvUploadById(id);

        if (cvUploadDto != null) {

            CvUploadResponse cvUploadResponse = mapper.map(cvUploadDto, CvUploadResponse.class);
            baseApiResponse.setMessage(messageProperties.deleted("Cv-upload"));
            baseApiResponse.setData(cvUploadResponse);
            baseApiResponse.setStatus(HttpStatus.OK);

        } else {
            baseApiResponse.setMessage(messageProperties.deletedError("Cv-upload", "cv-upload"));
            baseApiResponse.setStatus(HttpStatus.NO_CONTENT);
        }

        baseApiResponse.setTimestamp(new Timestamp(System.currentTimeMillis()));
        return ResponseEntity.ok(baseApiResponse);
    }

    //TODO: get post by caption
    @GetMapping("/search/cv-upload")
    public ResponseEntity<BaseApiResponseWithPage<List<CvUploadResponse>>> getCvUploadByName(@RequestParam("name") String name, Pageable pageable) {

        BaseApiResponseWithPage<List<CvUploadResponse>> baseApiResponse = new BaseApiResponseWithPage<>();
        ModelMapper mapper = new ModelMapper();

        Page<CvUploadDto> cvUploadDtoPage = cvUploadService.searchCvUploadByName(name, pageable);

        List<CvUploadResponse> cvResponseList = new ArrayList<>();

        for (CvUploadDto cvDto : cvUploadDtoPage) {
            cvResponseList.add(mapper.map(cvDto, CvUploadResponse.class));
        }

        if (cvUploadDtoPage.isEmpty()) {
            baseApiResponse.setMessage(messageProperties.hasNoRecords("Cv"));
            baseApiResponse.setData(new ArrayList<>());
            baseApiResponse.setStatus(HttpStatus.NO_CONTENT);
        } else {
            Pagination pagination = new Pagination();

            pagination.setPageNumber(cvUploadDtoPage.getNumber());
            pagination.setPageSize(cvUploadDtoPage.getSize());
            pagination.setTotalPages(cvUploadDtoPage.getTotalPages());
            pagination.setTotalElements(cvUploadDtoPage.getTotalElements());

            baseApiResponse.setMessage(messageProperties.selected("Cv"));
            baseApiResponse.setData(cvResponseList);
            baseApiResponse.setPagination(pagination);
            baseApiResponse.setStatus(HttpStatus.OK);
        }
        baseApiResponse.setTimestamp(new Timestamp(System.currentTimeMillis()));
        return ResponseEntity.ok(baseApiResponse);
    }

    @GetMapping("/cv-upload/userId/{id}")
    public ResponseEntity<BaseApiResponse<List<CvUploadResponse>>> getCvByUserId(@PathVariable("id") int id) {
        BaseApiResponse<List<CvUploadResponse>> baseApiResponse = new BaseApiResponse<>();
        ModelMapper mapper = new ModelMapper();

        List<CvUploadDto> cvDtoList =  cvUploadService.getCvByUserId(id);
        List<CvUploadResponse> cvResponses = new ArrayList<>();

        for (CvUploadDto cvDto : cvDtoList) {
            cvResponses.add(mapper.map(cvDto, CvUploadResponse.class));
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

    @PutMapping("/cv-upload/isPublic/{id}")
    public ResponseEntity<BaseApiResponse<CvUploadResponse>> updateIsPublic(@RequestBody CVUpdateUploadPublic cvUpdateUploadPublic, @PathVariable("id") int id) {
        BaseApiResponse<CvUploadResponse> baseApiResponse = new BaseApiResponse<>();
        ModelMapper mapper = new ModelMapper();
        CvUploadDto cvDto = cvUploadService.updateCVPublic(cvUpdateUploadPublic.isPublic(),id);

        if (cvDto == null) {
            baseApiResponse.setMessage(messageProperties.hasNoRecord("Cv"));
            baseApiResponse.setData(null);
            baseApiResponse.setStatus(HttpStatus.NO_CONTENT);
        }else{
            baseApiResponse.setMessage(messageProperties.updated("Cv"));
            baseApiResponse.setData(mapper.map(cvDto, CvUploadResponse.class));
            baseApiResponse.setStatus(HttpStatus.CREATED);
        }
        baseApiResponse.setTimestamp(new Timestamp(System.currentTimeMillis()));
        return ResponseEntity.ok(baseApiResponse);
    }
}
