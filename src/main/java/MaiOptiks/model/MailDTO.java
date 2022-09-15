package MaiOptiks.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;


@Getter
@Setter
public class MailDTO {

    @Size(max = 254)
    private String email;

    @Size(max = 254)
    private String benutzername;

    @Size(max = 254)
    private String passwort;

    @Size(max = 254)
    private String smtpserver;

    private Integer smtpport;

    private Boolean smtpauthentifizierung;

}
