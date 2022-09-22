package MaiOptiks.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;


@Getter
@Setter
public class MaterialDTO {

    private Integer materialId;

    @Size(max = 254)
    private String bezeichung;

    @Size(max = 254)
    private String info;

}
