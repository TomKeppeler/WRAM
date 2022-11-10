package org.hbrs.project.wram.control.reviewer;

import com.vaadin.flow.component.notification.Notification;
import org.hbrs.project.wram.model.reviewer.Reviewer;
import org.hbrs.project.wram.model.reviewer.ReviewerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import java.util.UUID;


@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ReviewerService {

    @Autowired
    public ReviewerRepository reviewerRepository;
    
    public Reviewer doCreateReviewer(Reviewer reviewer) {
        return this.reviewerRepository.save(reviewer);
    }

    public Reviewer getByUserId(UUID id) {
        if(id == null) {
            new Notification("userId is null!!!");
            return null;
        }
        return this.reviewerRepository.findByUserId(id);
    }

    public Reviewer findReviewerByUserId(UUID id) {
        return this.reviewerRepository.findByUserId(id);
    }

    public void deleteReviewerById(UUID id) {
        this.reviewerRepository.findByUserId(id);
    }
}
