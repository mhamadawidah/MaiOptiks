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
public class Auftrag {

    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "auftrag_sequence",
            sequenceName = "auftrag_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "auftrag_sequence"
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
    private Boolean erstemahnung;

    @Column
    private Boolean zweitemahnung;

    @Column
    private Boolean drittemahnung;

    @Column
    private LocalDate datum;

    @Column
    private String bemerkung;

    @Column
    private Boolean rueckgabe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kundennr")
    private Kunde kundenNr;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Berater")
    private Mitarbeiter berater;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "werkstatt")
    private Mitarbeiter werkstatt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AbrechnungsID")
    private Abrechnungsart abrechnungs;

    @OneToMany(mappedBy = "auftragsNr")
    private Set<Auftragsartikel> auftragsNrAuftragsartikels;
}
