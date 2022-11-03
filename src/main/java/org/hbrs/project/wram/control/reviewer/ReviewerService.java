package org.hbrs.project.wram.control.reviewer;

import org.hbrs.project.wram.model.reviewer.Reviewer;
import org.hbrs.project.wram.model.reviewer.ReviewerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ReviewerService {
    
    public ReviewerRepository reviewerRepository;
    
    public Reviewer doCreateReviewer(Reviewer reviewer) {
        return this.reviewerRepository.save(reviewer);
    }

}
