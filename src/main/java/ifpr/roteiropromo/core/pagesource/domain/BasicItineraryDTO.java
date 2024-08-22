package ifpr.roteiropromo.core.pagesource.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BasicItineraryDTO {
    private Long id;
    private String title;
    private String imageCoverUrl;
}
