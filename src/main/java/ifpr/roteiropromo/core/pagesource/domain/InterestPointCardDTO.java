package ifpr.roteiropromo.core.pagesource.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class InterestPointCardDTO implements Serializable {

    private Long id;
    private String name;
    private String averageValue; //1, 2, 3
    private String duration;
    private String imageCoverUrl;


    public void setaverageValue(Integer price) {
        if(price <= 150){
            this.averageValue = "1";
        }else if (price <= 300){
            this.averageValue = "2";
        }else{
            this.averageValue = "3";
        }
    }


}
