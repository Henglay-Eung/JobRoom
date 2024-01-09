package com.admin.admin_service.controller;

import com.admin.admin_service.model.Feedback;
import com.admin.admin_service.rest.message.BaseApiResponseWithPagination;
import com.admin.admin_service.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class FeedbackController {
    private FeedbackService feedbackService;
    @Autowired
    public void setFeedbackService(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @GetMapping("/admin/feedback")
    public String profileSetting(ModelMap modelMap, @RequestParam(defaultValue = "0") int page) {
        ResponseEntity<BaseApiResponseWithPagination<List<Feedback>>> result=feedbackService.findAllFeedback(page);
            modelMap.addAttribute("feedbacks",result.getBody().getData());
            modelMap.addAttribute("pagination",result.getBody().getPagination());
        return "page/feedback";
    }

}
