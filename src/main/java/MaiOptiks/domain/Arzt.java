package MaiOptiks.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.Set;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Arzt {

    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Integer arztnr;

    @Column(length = 254)
    private String name;

    @Column(length = 254)
    private String vorname;

    @Column(length = 254)
    private String strasse;

    @Column(length = 254)
    private String hausnr;

    @Column(length = 254)
    private String telefonnr;

    @Column(length = 254)
    private String handy;

    @Column(length = 254)
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plz")
    private Stadt plz;

    @OneToMany(mappedBy = "arztnr")
    private Set<RefraktionDurchgefuert> arztNrRefraktionDurchgefuerts;

}
