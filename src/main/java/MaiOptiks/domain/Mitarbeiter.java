package MaiOptiks.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Mitarbeiter {

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
    private Integer mitarbeiternr;

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

    @Column
    private LocalDate geburtsdatum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plz")
    private Stadt plz;

    @OneToMany(mappedBy = "mitarbeiternr")
    private Set<RefraktionDurchgefuert> mitarbeiterNrRefraktionDurchgefuerts;

    @OneToMany(mappedBy = "berater")
    private Set<Auftrag> beraterAuftrags;

    @OneToMany(mappedBy = "werkstatt")
    private Set<Auftrag> werkstattAuftrags;

}
