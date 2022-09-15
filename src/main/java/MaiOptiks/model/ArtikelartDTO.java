package MaiOptiks.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;


@Getter
@Setter
public class ArtikelartDTO {

    private Integer artId;

    @Size(max = 254)
    private String bezeichnung;

}
