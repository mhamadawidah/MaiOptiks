package MaiOptiks.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Set;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Krankenkasse {

    @Id
    @Column(nullable = false, updatable = false, length = 254)
    private String krankenkassennr;

    @Column(length = 254)
    private String name;

    @Column(length = 254)
    private String strasse;

    @Column(length = 254)
    private String telefonnr;

    @Column(length = 254)
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plz")
    private Stadt plz;

    @OneToMany(mappedBy = "krankenkassenNr")
    private Set<Kunde> krankenkassenNrKundes;

}
