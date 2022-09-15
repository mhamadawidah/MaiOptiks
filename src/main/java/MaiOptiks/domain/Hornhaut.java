package MaiOptiks.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.OffsetDateTime;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Hornhaut {

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
    private Integer hornhautId;

    @Column
    private Double hsir;

    @Column
    private Integer a0R;

    @Column
    private Double h2Shr;

    @Column
    private Integer a90R;

    @Column
    private Double massalR;

    @Column
    private Double tempR;

    @Column
    private Double supR;

    @Column
    private Double intR;

    @Column
    private Double hsil;

    @Column
    private Integer a0L;

    @Column
    private Double hshl;

    @Column
    private Integer a90L;

    @Column
    private Double massalL;

    @Column
    private Double tempL;

    @Column
    private Double supL;

    @Column
    private Double intL;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "refraktion_id", nullable = false)
    private Refraktion refraktion;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

}
