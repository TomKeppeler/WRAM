/**
 * @outhor Salah  & Tom
 * @vision 1.0
 * @Zuletzt bearbeitet: 14.11.22 by Salah
 */
package org.hbrs.project.wram.control.user;

import com.vaadin.flow.component.UI;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.hbrs.project.wram.control.RegisterControl;
import org.hbrs.project.wram.control.entwickler.EntwicklerService;
import org.hbrs.project.wram.control.manager.ManagerService;
import org.hbrs.project.wram.control.reviewer.ReviewerService;
import org.hbrs.project.wram.model.entwickler.Entwickler;
import org.hbrs.project.wram.model.manager.Manager;
import org.hbrs.project.wram.model.reviewer.Reviewer;
import org.hbrs.project.wram.model.user.User;
import org.hbrs.project.wram.model.user.UserDTO;
import org.hbrs.project.wram.model.user.UserRepository;
import org.hbrs.project.wram.util.Constants;
import org.hbrs.project.wram.views.routes.Notify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
/**
 * dient der CRUD von User Daten von DB
 * statt Repository
 */
public class UserService {

    // dint der CRUD von User Daten von DB
    @Autowired
    private UserRepository userRepository;

    //dint um die Rolle der User Daten von DB
    @Autowired
    private ManagerService managerService;

    //dint um die Rolle der User Daten von DB
    @Autowired
    private EntwicklerService entwicklerService;

    //dint um die Rolle der User Daten von DB
    @Autowired
    private ReviewerService reviewerService;

    @Autowired
    private JavaMailSender mailSender;


    // User in DB mittel userRepository speichern
    public User doCreateUser(User user) {
        return this.userRepository.save(user);
    }

    // Überprüfen, ob übergebene userDTO-Email schon in der Datenbank vorhanden ist
    public boolean isEmailAlreadyInDatabase(UserDTO userDTO) {
        return userRepository.isEmailInUse(userDTO.getEmail());
    }

    // Überprüfen, ob übergebene userDTO-Username schon in der Datenbank vorhanden ist

    public boolean isUsernameAlreadyInDatabase(UserDTO userDTO) {
        return userRepository.isUsernameInUse(userDTO.getUsername());
    }


    /**
     * überprüfe, welche Rolle der User hat
     *
     * @return Rolle m: Manager e: Entwickler, r: Reviewer
     */
    public String getRolle() {
        Manager manager = managerService.getByUserId((UUID) UI.getCurrent().getSession().getAttribute(Constants.CURRENT_USER));

        if (manager != null) {
            return "m";

        }
        Entwickler entwickler = entwicklerService.getByUserId((UUID) UI.getCurrent().getSession().getAttribute(Constants.CURRENT_USER));
        if (entwickler != null) {
            return "e";

        }
        Reviewer reviewer = reviewerService.getByUserId((UUID) UI.getCurrent().getSession().getAttribute(Constants.CURRENT_USER));

        if (reviewer != null) {
            return "r";

        }

        return "User ist nicht zugeordnet";
    }

    /**
     * find alle User, die im DB vorhanden sind
     *
     * @return Liste aller User
     */
    public List<User> findAllUsers() {
        return this.userRepository.findAll();
    }

    /**
     * #
     * find User die von DB mit username und pw
     *
     * @param username
     * @param pw
     * @return ein User
     */
    public User findUserByUsernameAndPassword(String username, String pw) {
        return this.userRepository.findUserByUsernameAndPassword(username, pw);
    }

    public User findUserByUsername(String username) {
        return this.userRepository.findUserByUsername(username);
    }

    /**
     * User mit ID x wird von DB gelöscht
     *
     * @param id
     */
    public void deleteUserById(UUID id) {
        this.userRepository.deleteById(id);
    }

    public User findUserById(UUID id) {
        return this.userRepository.findUserById(id);
    }

    /**
     * Überprüfe, ob verificationcode valide und unverbraucht ist
     * * @param   verificationCode Code für die Verifikation
     *
     * @return boolean
     */
    public boolean verify(String verificationCode) {
        User user = userRepository.findByVerificationCode(verificationCode);
        if (user == null) {
            return false;
        } else {
            user.setVerificationCode(null);
            user.setVerified(true);
            userRepository.save(user);
            return true;
        }
    }

    /**
     * * user erhält registrationcode, email wird aufgerufen
     *
     * @param user    aktueller benutzer
     * @param siteURL url für die Verifiationpage
     */
    public void register(User user, String siteURL) throws UnsupportedEncodingException, MessagingException {
        String randomCode = RandomString.make(64);
        user.setVerificationCode(randomCode);
        user.setVerified(false);
        userRepository.save(user);
        try {
            sendVerificationEmail(user, siteURL);
        } catch (Exception e) {
            Notify.notifyAfterUpdateWithOkay("Message:" + e.getMessage());
            userRepository.delete(user);
        }


    }

    /**
     * Überprüfe, ob temporäres Passwort valide und unverbraucht ist
     * * @param   verificationCode Code für das temporäre pw
     *
     * @return boolean
     */
    public boolean verifyNewPassword(String pw) {
        return RegisterControl.passwortCheck(pw);
    }

    /**
     * * user erhält registrationcode, email wird aufgerufen
     *
     * @param username aktueller benutzer
     * @param siteURL  url für die Verifiationpage
     */
    public void generatePassword(String username, String siteURL) throws UnsupportedEncodingException, MessagingException {
        User user = userRepository.findUserByUsername(username);
        sendForgotPasswordEmail(user, siteURL);

    }

    /**
     * * Schicke email mit link. Dann wird mit dem link die Passwortpage aufgerufen und der verificationcode übergeben
     *
     * @param user    aktueller benutzer
     * @param siteURL url für die Verifiationpage
     */
    private void sendForgotPasswordEmail(User user, String siteURL)
            throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = "wac.wram@web.de";
        String senderName = "WRAM Support";
        String subject = "Please update your password";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to update your password:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Your WRAM-Team.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getUsername());
        String verifyURL = siteURL + "/passwort_bestaetigen/" + user.getUsername();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);

    }

    /**
     * * Schicke email mit link. Dann wird mit dem link die Verificationpage aufgerufen und der verificationcode übergeben
     *
     * @param user    aktueller benutzer
     * @param siteURL url für die Verifiationpage
     */
    private void sendVerificationEmail(User user, String siteURL)
            throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = "wac.wram@web.de";
        String senderName = "WRAM Support";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Your WRAM-Team.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getUsername());
        String verifyURL = siteURL + "/verifizieren/" + user.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);

    }


}