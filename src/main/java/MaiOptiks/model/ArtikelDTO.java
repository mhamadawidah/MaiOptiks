package MaiOptiks.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;


@Getter
@Setter
public class ArtikelDTO {

    private Integer artikelNr;

    @Size(max = 254)
    private String bezeichnung;

    @Size(max = 254)
    private String artikelart;

    private Double einkaufspreis;

    private Double verkaufspreis;

    private Integer material;

    private Integer farbe;

    private Integer lieferant;

}
