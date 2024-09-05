package ifpr.roteiropromo.core.pagesource.domain;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasicPointDTO {

    private Long id;
    private String name;
    private String imageCoverUrl;
    private String interestPointType;
}
