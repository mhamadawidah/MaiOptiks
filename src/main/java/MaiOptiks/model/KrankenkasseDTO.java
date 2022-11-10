package MaiOptiks.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;


@Getter
@Setter
public class KrankenkasseDTO {

    @Size(max = 254)
    private String krankenkassenID;

    @Size(max = 254)
    private String name;

    @Size(max = 254)
    private String starsse;

    @Size(max = 254)
    private String telefonNr;

    @Size(max = 254)
    private String email;

    private Integer plz;

}
