package ifpr.roteiropromo.core.pagesource.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class HomePageDTO implements Serializable {

    private List<InterestPointCardDTO> top3InterestPoints;

    private List<BasicPointDTO> firstSlider; //Passeios (ponto turistico unitário), roteiros ou experiencias
    private List<BasicPointDTO> secondSlider; //restaurantes, hoteis e eventos

}
