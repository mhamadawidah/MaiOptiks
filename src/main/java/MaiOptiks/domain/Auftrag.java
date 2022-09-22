package MaiOptiks.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Set;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Auftrag {

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
    private Integer auftragsnummer;

    @Column
    private Boolean rezepturvorhanden;

    @Column(length = 254)
    private String womit;

    @Column(length = 254)
    private String wann;

    @Column
    private Boolean fertig;

    @Column
    private Boolean abgeholt;

    @Column
    private Boolean bezahlt;

    @Column(length = 254)
    private String auftragsbestaetigung;

    @Column(length = 254)
    private String rechnung;

    @Column
    private Boolean ersteMahnung;

    @Column
    private Boolean zweiteMahnung;

    @Column
    private Boolean dritteMahnung;

    @Column
    private LocalDate datum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kunden_nr_id")
    private Kunde kundenNr;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "berater_id")
    private Mitarbeiter berater;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "werkstatt_id")
    private Mitarbeiter werkstatt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "refraktion_id")
    private RefraktionDurchgefuert refraktion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "abrechnungs_id")
    private Abrechnungsart abrechnungs;

    @OneToMany(mappedBy = "auftragsNr")
    private Set<Auftragsartikel> auftragsNrAuftragsartikels;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

}
