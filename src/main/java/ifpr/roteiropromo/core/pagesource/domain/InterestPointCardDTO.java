package ifpr.roteiropromo.core.pagesource.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class InterestPointCardDTO implements Serializable {

    private Long id;
    private String name;
    private Integer averageValue;
    private String duration;
    private String imageCoverUrl;





}
