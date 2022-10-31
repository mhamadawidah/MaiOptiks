package MaiOptiks.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Set;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Lieferant {

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
    private Integer lieferantid;

    @Column(length = 254)
    private String name;

    @Column(length = 254)
    private String strasse;

    @Column(length = 254)
    private String telefonnr;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plz")
    private Stadt plz;

    @OneToMany(mappedBy = "lieferant")
    private Set<Artikel> lieferantArtikels;

    @OneToMany(mappedBy = "lieferant")
    private Set<Glaeser> lieferantGlaesers;

    @OneToMany(mappedBy = "lieferant")
    private Set<Fassungen> lieferantFassungens;

    @OneToMany(mappedBy = "lieferant")
    private Set<Kontaktlinsen> lieferantKontaktlinsens;

}
