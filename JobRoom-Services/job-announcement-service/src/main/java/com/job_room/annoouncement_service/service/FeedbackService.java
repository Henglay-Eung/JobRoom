package com.job_room.annoouncement_service.service;

import com.job_room.annoouncement_service.model.feedback.Feedback;
import com.job_room.annoouncement_service.model.feedback.Feedbackdto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FeedbackService {
    Feedbackdto postFeedback(Feedbackdto feedbackdto);
    Page<Feedbackdto>findAllFeedback(Pageable pageable);
    Feedbackdto delete(int id);
    long countFeedback();
}
