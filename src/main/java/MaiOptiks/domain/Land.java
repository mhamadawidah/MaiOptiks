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
public class Land {

    @Id
    @Column(nullable = false, updatable = false, length = 254)
    private String landid;

    @Column(length = 254)
    private String name;

    @OneToMany(mappedBy = "land")
    private Set<Stadt> landStadts;

}
