package MaiOptiks.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Auftragsartikel {

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
    private Integer auftragsartikelid;

    @Column
    private Integer sehhilfenart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auftragsnr")
    private Auftrag auftragsNr;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sehhilfeid")
    private Brille sehhilfe;

}
