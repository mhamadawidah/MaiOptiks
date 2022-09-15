package MaiOptiks.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;


@Getter
@Setter
public class StadtDTO {

    private Integer plz;

    @Size(max = 254)
    private String ort;

    @Size(max = 254)
    private String land;

}
