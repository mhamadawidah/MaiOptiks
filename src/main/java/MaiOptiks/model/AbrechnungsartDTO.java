package MaiOptiks.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;


@Getter
@Setter
public class AbrechnungsartDTO {

    private Integer id;

    @Size(max = 254)
    private String art;

}
