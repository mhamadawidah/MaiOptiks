package MaiOptiks.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;


@Getter
@Setter
public class GlaeserDTO {

    private Integer artikelNr;

    private Double einkaufspreis;

    private Double verkaufspreis;

    @NotNull
    private Integer art;

    private Integer werte;

    private Integer material;

    private Integer farbe;

    private Integer lieferant;

}