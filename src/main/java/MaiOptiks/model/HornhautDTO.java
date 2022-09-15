package MaiOptiks.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;


@Getter
@Setter
public class HornhautDTO {

    private Integer hornhautId;

    private Double hsir;

    private Integer a0R;

    private Double h2Shr;

    private Integer a90R;

    private Double massalR;

    private Double tempR;

    private Double supR;

    private Double intR;

    private Double hsil;

    private Integer a0L;

    private Double hshl;

    private Integer a90L;

    private Double massalL;

    private Double tempL;

    private Double supL;

    private Double intL;

    @NotNull
    private Integer refraktion;

}
