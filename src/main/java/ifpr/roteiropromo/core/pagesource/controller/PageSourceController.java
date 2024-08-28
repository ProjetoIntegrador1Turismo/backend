package ifpr.roteiropromo.core.pagesource.controller;

import ifpr.roteiropromo.core.pagesource.domain.HomePageDTO;
import ifpr.roteiropromo.core.pagesource.domain.ItineraryPageDTO;
import ifpr.roteiropromo.core.pagesource.domain.TourPageDTO;
import ifpr.roteiropromo.core.pagesource.service.ItineraryPageSourceService;
import ifpr.roteiropromo.core.pagesource.service.PageSourceService;
import ifpr.roteiropromo.core.pagesource.service.TourPageSourceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/page-source")
public class PageSourceController {

    private final PageSourceService pageSourceService;
    private final TourPageSourceService tourPageSourceService;
    private final ItineraryPageSourceService itineraryPageSourceService;

    public PageSourceController(PageSourceService pageSourceService, TourPageSourceService tourPageSourceService, ItineraryPageSourceService itineraryPageSourceService) {
        this.pageSourceService = pageSourceService;
        this.tourPageSourceService = tourPageSourceService;
        this.itineraryPageSourceService = itineraryPageSourceService;
    }


    @GetMapping("/home")
    public ResponseEntity<HomePageDTO> getHomePage(){
        return ResponseEntity.ok(pageSourceService.getHomePageData());
    }


    @GetMapping("/tour/{id}")
    public ResponseEntity<TourPageDTO> getTourPage(@PathVariable Long id){
        return ResponseEntity.ok(tourPageSourceService.getTourPageData(id));
    }

    @GetMapping("/itinerary/{id}")
    public ResponseEntity<ItineraryPageDTO> getItineraryPage(@PathVariable Long id){
        return ResponseEntity.ok(itineraryPageSourceService.getItineraryPageData(id));
    }

}
