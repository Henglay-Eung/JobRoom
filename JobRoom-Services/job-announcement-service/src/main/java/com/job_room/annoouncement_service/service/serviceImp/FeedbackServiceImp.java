package com.job_room.annoouncement_service.service.serviceImp;

import com.job_room.annoouncement_service.model.announcement.Announcement;
import com.job_room.annoouncement_service.model.announcement.AnnouncementDto;
import com.job_room.annoouncement_service.model.feedback.Feedback;
import com.job_room.annoouncement_service.model.feedback.Feedbackdto;
import com.job_room.annoouncement_service.repository.FeedbackRepository;
import com.job_room.annoouncement_service.service.FeedbackService;
import com.job_room.annoouncement_service.utils.PaginationUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.sql.Timestamp;
import java.util.Optional;

@Service
public class FeedbackServiceImp implements FeedbackService {
    private FeedbackRepository feedbackRepository;
    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public void setFeedbackRepository(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    @Override
    public Feedbackdto postFeedback(Feedbackdto feedbackdto) {
        Feedback feedback = modelMapper.map(feedbackdto, Feedback.class);
        feedback.setCreated(new Timestamp(System.currentTimeMillis()));
        Feedback result = feedbackRepository.save(feedback);
        if (result != null) {
            Feedbackdto feedbackdto1 = modelMapper.map(result, Feedbackdto.class);
            return feedbackdto1;
        } else {
            return null;
        }
    }

    @Override
    public Page<Feedbackdto> findAllFeedback(Pageable pageable) {
        Page<Feedback> feedbacks = feedbackRepository.findAll(pageable);
        PaginationUtils<Feedbackdto, Page<Feedback>> paging = new PaginationUtils<>(Feedbackdto.class);
        paging.setData(feedbacks);
        Page<Feedbackdto> feedbackdtos = paging;

        return feedbackdtos;
    }

    @Override
    public Feedbackdto delete(int id) {
        Optional<Feedback> feedback=feedbackRepository.findById(id);
        if(feedback.isEmpty()){
            return null;
        }else {
            Feedbackdto result=modelMapper.map(feedback.get(),Feedbackdto.class);
            feedbackRepository.delete(feedback.get());
            return result;
        }
    }

    @Override
    public long countFeedback() {
        return feedbackRepository.count();
    }
}
