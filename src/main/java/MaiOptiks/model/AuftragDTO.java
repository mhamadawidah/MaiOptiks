package MaiOptiks.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Size;
import java.time.LocalDate;


@Getter
@Setter
public class AuftragDTO {

    private Integer auftragsnummer;

    private Boolean rezepturvorhanden;

    @Size(max = 254)
    private String womit;

    @Size(max = 254)
    private String wann;

    private Boolean fertig;

    private Boolean abgeholt;

    private Boolean bezahlt;

    @Size(max = 254)
    private String auftragsbestaetigung;

    @Size(max = 254)
    private String rechnung;

    private Boolean ersteMahnung;

    private Boolean zweiteMahnung;

    private Boolean dritteMahnung;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate datum;

    private Integer kundenNr;

    private Integer berater;

    private Integer werkstatt;

    private Integer refraktion;

    private Integer abrechnungs;

}
