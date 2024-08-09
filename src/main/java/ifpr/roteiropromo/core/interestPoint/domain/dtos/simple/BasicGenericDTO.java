package ifpr.roteiropromo.core.interestPoint.domain.dtos.simple;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasicGenericDTO {

    private Long id;
    private String name;
    private String shortDescription;
    private String imageCoverUrl;

    public BasicGenericDTO(Long id, String imageCoverUrl, String name, String shortDescription) {
        this.id = id;
        this.imageCoverUrl = imageCoverUrl;
        this.name = name;
        this.shortDescription = shortDescription;
    }
}
