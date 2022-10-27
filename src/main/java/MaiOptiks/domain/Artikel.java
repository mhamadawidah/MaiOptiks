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
public class Artikel {

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
    private Integer artikelnr;

    @Column(length = 254)
    private String bezeichnung;

    @Column(length = 254)
    private String artikelart;

    @Column
    private Double einkaufspreis;

    @Column
    private Double verkaufspreis;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material")
    private Material material;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "farbe")
    private Farbe farbe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lieferant")
    private Lieferant lieferant;

    @OneToMany(mappedBy = "glasArtikelIdlinks")
    private Set<Brille> glasArtikelIdlinksBrilles;

    @OneToMany(mappedBy = "glasArtikelIdrechts")
    private Set<Brille> glasArtikelIdrechtsBrilles;

    @OneToMany(mappedBy = "fassungsArtikel")
    private Set<Brille> fassungsArtikelBrilles;

}
