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
public class Stadt {

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
    private Integer plz;

    @Column(length = 254)
    private String ort;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "land")
    private Land land;

    @OneToMany(mappedBy = "plz")
    private Set<Krankenkasse> plzKrankenkasses;

    @OneToMany(mappedBy = "plz")
    private Set<Kunde> plzKundes;

    @OneToMany(mappedBy = "plz")
    private Set<Firmenstamm> plzFirmenstamms;

    @OneToMany(mappedBy = "plz")
    private Set<Mitarbeiter> plzMitarbeiters;

    @OneToMany(mappedBy = "plz")
    private Set<Arzt> plzArzts;

    @OneToMany(mappedBy = "plz")
    private Set<Lieferant> plzLieferants;
}
