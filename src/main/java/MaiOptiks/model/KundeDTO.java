package MaiOptiks.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Size;
import java.time.LocalDate;


@Getter
@Setter
public class KundeDTO {

    private Integer kundenNr;

    @Size(max = 254)
    private String anrede;

    @Size(max = 254)
    private String name;

    @Size(max = 254)
    private String vorname;

    @Size(max = 254)
    private String strasse;

    @Size(max = 254)
    private String hausNr;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate geburtsdatum;

    @Size(max = 254)
    private String telefonNr;

    @Size(max = 254)
    private String handy;

    @Size(max = 254)
    private String email;

    @Size(max = 254)
    private String versicherungsNr;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate gueltigkeit;

    @Size(max = 254)
    private String bemerkung;

    private Integer plz;

    @Size(max = 254)
    private String krankenkassenNr;

}
