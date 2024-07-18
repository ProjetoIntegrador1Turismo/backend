package ifpr.roteiropromo.core.pagesource.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class HomePageDTO implements Serializable {

    private List<InterestPointCardDTO> top3InterestPoints;

}
