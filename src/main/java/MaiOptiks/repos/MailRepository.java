package MaiOptiks.repos;

import MaiOptiks.domain.Mail;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MailRepository extends JpaRepository<Mail, String> {

    boolean existsByEmailIgnoreCase(String email);

}
