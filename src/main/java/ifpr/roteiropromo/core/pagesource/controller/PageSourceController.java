package ifpr.roteiropromo.core.pagesource.controller;

import ifpr.roteiropromo.core.pagesource.domain.HomePageDTO;
import ifpr.roteiropromo.core.pagesource.domain.TourPageDTO;
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


    public PageSourceController(PageSourceService pageSourceService, TourPageSourceService tourPageSourceService) {
        this.pageSourceService = pageSourceService;
        this.tourPageSourceService = tourPageSourceService;
    }


    @GetMapping("/home")
    public ResponseEntity<HomePageDTO> getHomePage(){
        return ResponseEntity.ok(pageSourceService.getHomePageData());
    }


    @GetMapping("/tour/{id}")
    public ResponseEntity<TourPageDTO> getTourPage(@PathVariable Long id){
        return ResponseEntity.ok(tourPageSourceService.getTourPageData(id));
    }
}
