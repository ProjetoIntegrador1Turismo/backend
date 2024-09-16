package ifpr.roteiropromo.core.user.domain.dtos;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasicTouristDTO {

    private Long id;
    private String touristName;
    private String profileImageUrl;
}
