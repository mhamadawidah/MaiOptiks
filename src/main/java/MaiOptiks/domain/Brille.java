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
    private Integer brillenId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "glas_artikel_idlinks_id")
    private Artikel glasArtikelIdlinks;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "glas_artikel_idrechts_id")
    private Artikel glasArtikelIdrechts;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fassungs_artikel_id")
    private Artikel fassungsArtikel;

    @OneToMany(mappedBy = "sehhilfe")
    private Set<Auftragsartikel> sehhilfeAuftragsartikels;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

}
