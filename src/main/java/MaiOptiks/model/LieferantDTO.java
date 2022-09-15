package MaiOptiks.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;


@Getter
@Setter
public class LieferantDTO {

    private Integer lieferantId;

    @Size(max = 254)
    private String name;

    @Size(max = 254)
    private String strasse;

    @Size(max = 254)
    private String telefonNr;

    private Integer plz;

}
