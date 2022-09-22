package MaiOptiks.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;


@Getter
@Setter
public class ArztDTO {

    private Integer arztNr;

    @Size(max = 254)
    private String name;

    @Size(max = 254)
    private String vorname;

    @Size(max = 254)
    private String strasse;

    @Size(max = 254)
    private String hausNr;

    @Size(max = 254)
    private String telefonNr;

    @Size(max = 254)
    private String handy;

    @Size(max = 254)
    private String email;

    private Integer plz;

}
