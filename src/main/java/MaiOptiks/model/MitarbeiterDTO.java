package MaiOptiks.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Size;
import java.time.LocalDate;


@Getter
@Setter
public class MitarbeiterDTO {

    private Integer mitarbeiterNr;

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

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate geburtsdatum;

    private Integer plz;

}
