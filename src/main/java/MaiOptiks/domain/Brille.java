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
public class Brille {

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
    private Integer brillenid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "glasartikelidlinks")
    private Artikel glasArtikelIdlinks;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "glasartikelidrechts")
    private Artikel glasArtikelIdrechts;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fassungsartikelid")
    private Artikel fassungsArtikel;

    @OneToMany(mappedBy = "sehhilfe")
    private Set<Auftragsartikel> sehhilfeAuftragsartikels;

}
