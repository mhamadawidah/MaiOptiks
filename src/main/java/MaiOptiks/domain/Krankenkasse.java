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
public class Krankenkasse {

    @Id
    @Column(nullable = false, updatable = false, length = 254)
    private String krankenkassenID;

    @Column(length = 254)
    private String name;

    @Column(length = 254)
    private String strasse;

    @Column(length = 254)
    private String telefonNummer;

    @Column(length = 254)
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plz_id")
    private Stadt plz;

    @OneToMany(mappedBy = "krankenkassenID")
    private Set<Kunde> krankenkassenIDKunde;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

}
