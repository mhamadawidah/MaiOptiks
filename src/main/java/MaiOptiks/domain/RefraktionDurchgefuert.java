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
public class RefraktionDurchgefuert {

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
    private Integer refraktionsnr;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mitarbeiter_nr")
    private Mitarbeiter mitarbeiterNr;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "arzt_nr_id")
    private Arzt arztNr;

    @OneToMany(mappedBy = "refraktion")
    private Set<Auftrag> refraktionAuftrags;

}
