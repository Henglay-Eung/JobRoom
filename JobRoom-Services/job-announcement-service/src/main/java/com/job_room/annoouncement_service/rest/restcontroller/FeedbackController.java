package com.job_room.annoouncement_service.rest.restcontroller;

import com.job_room.annoouncement_service.model.feedback.FeedbackRequest;
import com.job_room.annoouncement_service.model.feedback.FeedbackResponse;
import com.job_room.annoouncement_service.model.feedback.Feedbackdto;
import com.job_room.annoouncement_service.rest.message.*;
import com.job_room.annoouncement_service.service.FeedbackService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
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
@RequestMapping("/api/v1")
public class FeedbackController {
    private FeedbackService feedbackService;
    private ModelMapper modelMapper = new ModelMapper();

    private MessageProperties messageProperties;

    @Autowired
    public void setMessageProperties(MessageProperties messageProperties) {
        this.messageProperties = messageProperties;
    }

    @Autowired
    public void setFeedbackService(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    //TODO: Exception =========================================================
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

        response.setMessage(messageProperties.insertError("feedback"));
        response.setError(errors);
        response.setStatus(HttpStatus.BAD_REQUEST);
        response.setTime(new Timestamp(System.currentTimeMillis()));

        return ResponseEntity.ok(response);
    }


    @PostMapping("/feedback")
    public ResponseEntity<Object> postFeedBack(@Valid @RequestBody FeedbackRequest request) {
        Feedbackdto feedbackdto = modelMapper.map(request, Feedbackdto.class);
        Feedbackdto result = feedbackService.postFeedback(feedbackdto);
        BaseApiResponse<FeedbackResponse> feedbackResponseBaseApiResponse = new BaseApiResponse<>();
        FeedbackResponse response;
        if (result != null) {
            response = modelMapper.map(result, FeedbackResponse.class);
            feedbackResponseBaseApiResponse.setData(response);
            feedbackResponseBaseApiResponse.setMessage(messageProperties.inserted("feedback"));
            feedbackResponseBaseApiResponse.setStatus(HttpStatus.OK);
            feedbackResponseBaseApiResponse.setTime(new Timestamp(System.currentTimeMillis()));
            return ResponseEntity.ok(feedbackResponseBaseApiResponse);
        } else {
            feedbackResponseBaseApiResponse.setData(null);
            feedbackResponseBaseApiResponse.setMessage(messageProperties.inserted("feedback"));
            feedbackResponseBaseApiResponse.setStatus(HttpStatus.BAD_GATEWAY);
            feedbackResponseBaseApiResponse.setTime(new Timestamp(System.currentTimeMillis()));
            return ResponseEntity.ok(feedbackResponseBaseApiResponse);
        }

    }

    @DeleteMapping("/feedback/{id}")
    public ResponseEntity<Object> deleteFeedback(@PathVariable() int id) {
        Feedbackdto feedbackdto = feedbackService.delete(id);
        BaseApiResponse<FeedbackResponse> feedbackResponseBaseApiResponse = new BaseApiResponse<>();
        FeedbackResponse response;
        if (feedbackdto != null) {
            response = modelMapper.map(feedbackdto, FeedbackResponse.class);
            feedbackResponseBaseApiResponse.setData(response);
            feedbackResponseBaseApiResponse.setMessage(messageProperties.deleted("feedback"));
            feedbackResponseBaseApiResponse.setStatus(HttpStatus.OK);
            feedbackResponseBaseApiResponse.setTime(new Timestamp(System.currentTimeMillis()));
            return ResponseEntity.ok(feedbackResponseBaseApiResponse);
        } else {
            feedbackResponseBaseApiResponse.setData(null);
            feedbackResponseBaseApiResponse.setMessage(messageProperties.deletedError("feedback", "feedback"));
            feedbackResponseBaseApiResponse.setStatus(HttpStatus.BAD_GATEWAY);
            feedbackResponseBaseApiResponse.setTime(new Timestamp(System.currentTimeMillis()));
            return ResponseEntity.ok(feedbackResponseBaseApiResponse);
        }

    }


    @GetMapping("/feedback")
    public ResponseEntity<Object> findAllFeedback(@RequestParam(defaultValue = "0", required = false) int page, @RequestParam(defaultValue = "10", required = false) int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("id").descending());
        Page<Feedbackdto> feedbackdtoPage = feedbackService.findAllFeedback(pageable);
        BaseApiResponseWithPagination<List<FeedbackResponse>> baseApiResponseWithPagination = new BaseApiResponseWithPagination<>();
        List<FeedbackResponse> feedbackResponseList = new ArrayList<>();
        Pagination pagination = new Pagination();
        pagination.setCurrentPage(feedbackdtoPage.getNumber());
        pagination.setPageSize(feedbackdtoPage.getSize());
        pagination.setTotalItems(feedbackdtoPage.getTotalElements());
        pagination.setTotalPages(feedbackdtoPage.getTotalPages());
        for (int i = 0; i < feedbackdtoPage.getContent().size(); i++) {
            feedbackResponseList.add(modelMapper.map(feedbackdtoPage.getContent().get(i), FeedbackResponse.class));
        }
        if (feedbackdtoPage.getContent().isEmpty()) {
            baseApiResponseWithPagination.setData(feedbackResponseList);
            baseApiResponseWithPagination.setMessage(messageProperties.selectedError("feedback"));
            baseApiResponseWithPagination.setPagination(pagination);
            baseApiResponseWithPagination.setStatus(HttpStatus.NO_CONTENT);
            baseApiResponseWithPagination.setTime(new Timestamp(System.currentTimeMillis()));
            return ResponseEntity.ok(baseApiResponseWithPagination);
        }
        baseApiResponseWithPagination.setData(feedbackResponseList);
        baseApiResponseWithPagination.setMessage(messageProperties.selected("feedback"));
        baseApiResponseWithPagination.setPagination(pagination);
        baseApiResponseWithPagination.setStatus(HttpStatus.OK);
        baseApiResponseWithPagination.setTime(new Timestamp(System.currentTimeMillis()));
        return ResponseEntity.ok(baseApiResponseWithPagination);
    }

    @GetMapping("/feedback/count")
    public ResponseEntity<Object> countFeedback() {
        long count=feedbackService.countFeedback();
        BaseApiResponse<Integer> baseApiResponse=new BaseApiResponse<>();
        baseApiResponse.setTime(new Timestamp(System.currentTimeMillis()));
        baseApiResponse.setStatus(HttpStatus.OK);
        baseApiResponse.setMessage("count feedback successfully!");
        baseApiResponse.setData((int) count);
        return ResponseEntity.ok(baseApiResponse);
    }

}
