package ifpr.roteiropromo.core.pagesource.controller;


import ifpr.roteiropromo.core.interestPoint.domain.dtos.simple.BasicGenericDTO;
import ifpr.roteiropromo.core.interestPoint.service.PaginatedService;
import ifpr.roteiropromo.core.pagesource.domain.BasicItineraryDTO;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/paginated")
public class PaginationController {

    private final PaginatedService paginatedService;


    public PaginationController(PaginatedService paginatedService) {
        this.paginatedService = paginatedService;
    }

    @GetMapping("/all")
    public Page<BasicGenericDTO> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "") String query){
        return paginatedService.findAllPaginated(page, size, query);
    }

    @GetMapping("/events")
    public Page<BasicGenericDTO> getEvents(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "") String query){
        return paginatedService.findEventPaginated(page, size, query);
    }

    @GetMapping("/hotels")
    public Page<BasicGenericDTO> getHotels(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "") String query){
        return paginatedService.findHotelPaginated(page, size ,query);
    }

    @GetMapping("/experiences")
    public Page<BasicGenericDTO> getExperiences(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "") String query){
        return paginatedService.findExperiencesPaginated(page, size, query);
    }

    @GetMapping("/restaurants")
    public Page<BasicGenericDTO> getRestaurants(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "") String query){
        return paginatedService.findRestaurantsPaginated(page, size, query);
    }

    @GetMapping("/tourist-points")
    public Page<BasicGenericDTO> getTouristPoints(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "") String query){
        return paginatedService.findTouristPointsPaginated(page, size, query);
    }

    @GetMapping("/itineraries")
    public Page<BasicItineraryDTO> getItineraries(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "") String query){
        return paginatedService.findItinerariesPaginated(page, size, query);
    }





}
