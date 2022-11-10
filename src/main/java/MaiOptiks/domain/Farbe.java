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
public class Farbe {

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
    private Integer farbeid;

    @Column(length = 254)
    private String bezeichnung;

    @Column(length = 254)
    private String info;

    @OneToMany(mappedBy = "farbe")
    private Set<Artikel> farbeArtikels;

    @OneToMany(mappedBy = "farbe")
    private Set<Glaeser> farbeGlaesers;

    @OneToMany(mappedBy = "farbe")
    private Set<Fassungen> farbeFassungens;

    @OneToMany(mappedBy = "farbe")
    private Set<Kontaktlinsen> farbeKontaktlinsens;

}
