/**
 * @outhor Salah, Tom & Fabio
 * @vision 1.0
 * @Zuletzt bearbeiret: 17.11.22 by Salah
 */

package org.hbrs.project.wram.control.reviewer;

import com.vaadin.flow.component.notification.Notification;
import lombok.RequiredArgsConstructor;
import org.hbrs.project.wram.model.reviewer.Reviewer;
import org.hbrs.project.wram.model.reviewer.ReviewerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ReviewerService {

    // dint der CRUD von Reviewer Daten von DB
    @Autowired
    public ReviewerRepository reviewerRepository;


    // Reviewer in DB mittel reviewerRepository speichern
    public Reviewer doCreateReviewer(Reviewer reviewer) {
        return this.reviewerRepository.save(reviewer);
    }

    /**
     * mittels UserID wird Reviewer von DB geholt
     * @param  id
     * @return Reviewer
     */
    public Reviewer getByUserId(UUID id) {
        if (id == null) {
            new Notification("userId is null!!!");
            return null;
        }
        return this.reviewerRepository.findByUserId(id);
    }

    /**
     * mittels UserID wird Reviewer von DB geholt
     * @param id
     * @return Reviewer
     */
    public Reviewer findReviewerByUserId(UUID id) {
        return this.reviewerRepository.findByUserId(id);
    }

    /**
     * mittels ReviewerID wird Reviewer von DB gelöscht
     *
     */
    public void deleteReviewerById(UUID id) {
        this.reviewerRepository.deleteById(id);
    }
}
