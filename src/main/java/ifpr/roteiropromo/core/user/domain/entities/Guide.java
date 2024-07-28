package ifpr.roteiropromo.core.user.domain.entities;

import ifpr.roteiropromo.core.itinerary.domain.entities.Itinerary;
import ifpr.roteiropromo.core.review.domain.entities.Review;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Guide extends User {
    private String cadasturCode;
    private Boolean isApproved;

    @OneToMany()
    private List<Itinerary> itineraries;

    public double getAverageRating(List<Review> reviews) {
        if (reviews.isEmpty()) {
            return 0.0;
        }
        int sum = reviews.stream().mapToInt(Review::getRating).sum();
        return (double) sum / reviews.size();
    }
}




