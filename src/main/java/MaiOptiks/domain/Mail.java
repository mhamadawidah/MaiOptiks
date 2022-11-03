package MaiOptiks.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import java.time.OffsetDateTime;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Mail {

    @Id
    @Column(nullable = false, updatable = false, length = 254)
    private String email;

    @Column(length = 254)
    private String benutzername;

    @Column(length = 254)
    private String passwort;

    @Column(length = 254)
    private String smtpserver;

    @Column
    private Integer smtpport;

    @Column
    private Boolean smtpauthentifizierung;
}
