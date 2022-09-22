package MaiOptiks.service;

import MaiOptiks.domain.Mail;
import MaiOptiks.model.MailDTO;
import MaiOptiks.repos.MailRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class MailService {

    private final MailRepository mailRepository;

    public MailService(final MailRepository mailRepository) {
        this.mailRepository = mailRepository;
    }

    public List<MailDTO> findAll() {
        return mailRepository.findAll(Sort.by("email"))
                .stream()
                .map(mail -> mapToDTO(mail, new MailDTO()))
                .collect(Collectors.toList());
    }

    public MailDTO get(final String email) {
        return mailRepository.findById(email)
                .map(mail -> mapToDTO(mail, new MailDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public String create(final MailDTO mailDTO) {
        final Mail mail = new Mail();
        mapToEntity(mailDTO, mail);
        mail.setEmail(mailDTO.getEmail());
        return mailRepository.save(mail).getEmail();
    }

    public void update(final String email, final MailDTO mailDTO) {
        final Mail mail = mailRepository.findById(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(mailDTO, mail);
        mailRepository.save(mail);
    }

    public void delete(final String email) {
        mailRepository.deleteById(email);
    }

    private MailDTO mapToDTO(final Mail mail, final MailDTO mailDTO) {
        mailDTO.setEmail(mail.getEmail());
        mailDTO.setBenutzername(mail.getBenutzername());
        mailDTO.setPasswort(mail.getPasswort());
        mailDTO.setSmtpserver(mail.getSmtpserver());
        mailDTO.setSmtpport(mail.getSmtpport());
        mailDTO.setSmtpauthentifizierung(mail.getSmtpauthentifizierung());
        return mailDTO;
    }

    private Mail mapToEntity(final MailDTO mailDTO, final Mail mail) {
        mail.setBenutzername(mailDTO.getBenutzername());
        mail.setPasswort(mailDTO.getPasswort());
        mail.setSmtpserver(mailDTO.getSmtpserver());
        mail.setSmtpport(mailDTO.getSmtpport());
        mail.setSmtpauthentifizierung(mailDTO.getSmtpauthentifizierung());
        return mail;
    }

    public boolean emailExists(final String email) {
        return mailRepository.existsByEmailIgnoreCase(email);
    }

}
