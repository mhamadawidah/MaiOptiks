package MaiOptiks.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.OffsetDateTime;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Firmenstamm {

    @Id
    @Column(nullable = false, updatable = false, length = 254)
    private String augenoptikerIknr;

    @Column(length = 254)
    private String steuernummer;

    @Column(length = 254)
    private String geschaeftsname;

    @Column(length = 254)
    private String bankverbindung;

    @Column(length = 254)
    private String strasse;

    @Column(length = 254)
    private String hausNr;

    @Column(length = 254)
    private String telefonNr;

    @Column(length = 254)
    private String inhabername;

    @Column(length = 254)
    private String inhabervorname;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plz_id")
    private Stadt plz;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

}
