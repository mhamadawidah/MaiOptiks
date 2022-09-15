package MaiOptiks.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import MaiOptiks.model.MailDTO;
import MaiOptiks.service.MailService;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(value = "/api/mails", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Mail")
public class MailResource {

    private final MailService mailService;

    public MailResource(final MailService mailService) {
        this.mailService = mailService;
    }

    @GetMapping
    public ResponseEntity<List<MailDTO>> getAllMails() {
        return ResponseEntity.ok(mailService.findAll());
    }

    @GetMapping("/{email}")
    public ResponseEntity<MailDTO> getMail(@PathVariable final String email) {
        return ResponseEntity.ok(mailService.get(email));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Void> createMail(@RequestBody @Valid final MailDTO mailDTO,
            final BindingResult bindingResult) throws MethodArgumentNotValidException {
        if (!bindingResult.hasFieldErrors("email") && mailDTO.getEmail() == null) {
            bindingResult.rejectValue("email", "NotNull");
        }
        if (!bindingResult.hasFieldErrors("email") && mailService.emailExists(mailDTO.getEmail())) {
            bindingResult.rejectValue("email", "Exists.mail.email");
        }
        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(new MethodParameter(
                    this.getClass().getDeclaredMethods()[0], -1), bindingResult);
        }
        mailService.create(mailDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{email}")
    public ResponseEntity<Void> updateMail(@PathVariable final String email,
            @RequestBody @Valid final MailDTO mailDTO) {
        mailService.update(email, mailDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{email}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteMail(@PathVariable final String email) {
        mailService.delete(email);
        return ResponseEntity.noContent().build();
    }

}
