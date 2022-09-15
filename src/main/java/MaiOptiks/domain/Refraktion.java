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
public class Refraktion {

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
    private Integer refraktionId;

    @Column
    private Double sph;

    @Column
    private Double cyl;

    @Column
    private Integer ach;

    @Column
    private Double adds;

    @Column
    private Double pris;

    @Column
    private Integer bas;

    @Column
    private Double visus;

    @OneToMany(mappedBy = "refraktion")
    private Set<Hornhaut> refraktionHornhauts;

    @OneToMany(mappedBy = "werte")
    private Set<Glaeser> werteGlaesers;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

}
