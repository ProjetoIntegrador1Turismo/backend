package ifpr.roteiropromo.core.user.domain.entities;

import ifpr.roteiropromo.core.itinerary.domain.entities.Itinerary;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Guide extends User{

    private String cadasturCode;

    private Boolean isApproved;

    @OneToMany
    private List<Itinerary> itineraries;

    @ElementCollection
    private List<Integer> ratings;

    public double getAverageRating() {
        if (ratings == null || ratings.isEmpty()) {
            return 0.0;
        }
        int sum = 0;
        for (int rating : ratings) {
            sum += rating;
        }
        return (double) sum / ratings.size();
    }
}
