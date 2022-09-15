package MaiOptiks.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;


@Getter
@Setter
public class LandDTO {

    @Size(max = 254)
    private String landId;

    @Size(max = 254)
    private String name;

}
