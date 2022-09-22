package MaiOptiks.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;


@Getter
@Setter
public class FarbeDTO {

    private Integer farbeId;

    @Size(max = 254)
    private String bezeichnung;

    @Size(max = 254)
    private String info;

}
