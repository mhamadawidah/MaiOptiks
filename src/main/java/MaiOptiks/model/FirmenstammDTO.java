package MaiOptiks.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;


@Getter
@Setter
public class FirmenstammDTO {

    @Size(max = 254)
    private String augenoptikerIknr;

    @Size(max = 254)
    private String steuernummer;

    @Size(max = 254)
    private String geschaeftsname;

    @Size(max = 254)
    private String bankverbindung;

    @Size(max = 254)
    private String strasse;

    @Size(max = 254)
    private String hausNr;

    @Size(max = 254)
    private String telefonNr;

    @Size(max = 254)
    private String inhabername;

    @Size(max = 254)
    private String inhabervorname;

    private Integer plz;

}
