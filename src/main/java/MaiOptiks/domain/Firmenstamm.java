package MaiOptiks.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Firmenstamm {

    @Id
    @Column(nullable = false, updatable = false, length = 254)
    private String augenoptikeriknr;

    @Column(length = 254)
    private String steuernummer;

    @Column(length = 254)
    private String geschaeftsname;

    @Column(length = 254)
    private String bankverbindung;

    @Column(length = 254)
    private String strasse;

    @Column(length = 254)
    private String hausnr;

    @Column(length = 254)
    private String telefonnr;

    @Column(length = 254)
    private String inhabername;

    @Column(length = 254)
    private String inhabervorname;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plz")
    private Stadt plz;

}
