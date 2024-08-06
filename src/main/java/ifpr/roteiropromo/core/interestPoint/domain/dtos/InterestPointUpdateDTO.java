package ifpr.roteiropromo.core.interestPoint.domain.dtos;


import lombok.Data;

@Data
public class InterestPointUpdateDTO {
    private String name;
    private String shortDescription;
    private String interestPointType;
    private String duration;
    private String averageValue;
    private String road;
    private String number;
    private String zipCode;
    private String longDescription;
    private String requiredAge;
    private String date;
    private Integer starsNumber;
    private Boolean isResort;
    private Boolean breakfastIncluded;
    private String foodType;
}
