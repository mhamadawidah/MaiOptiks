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
public class Arzt {

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
    private Integer arztNr;

    @Column(length = 254)
    private String name;

    @Column(length = 254)
    private String vorname;

    @Column(length = 254)
    private String strasse;

    @Column(length = 254)
    private String hausNr;

    @Column(length = 254)
    private String telefonNr;

    @Column(length = 254)
    private String handy;

    @Column(length = 254)
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plz_id")
    private Stadt plz;

    @OneToMany(mappedBy = "arztNr")
    private Set<RefraktionDurchgefuert> arztNrRefraktionDurchgefuerts;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

}