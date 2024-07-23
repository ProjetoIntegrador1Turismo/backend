package ifpr.roteiropromo.core.user.domain.entities;

import ifpr.roteiropromo.core.itinerary.domain.entities.Itinerary;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @CollectionTable(name = "guide_ratings", joinColumns = @JoinColumn(name = "guide_id"))
    @MapKeyColumn(name = "user_email")
    @Column(name = "rating")
    private Map<String, Integer> ratings = new HashMap<>();

    public double getAverageRating() {
        if (ratings.isEmpty()) {
            return 0.0;
        }
        int sum = ratings.values().stream().mapToInt(Integer::intValue).sum();
        return (double) sum / ratings.size();
    }
}

