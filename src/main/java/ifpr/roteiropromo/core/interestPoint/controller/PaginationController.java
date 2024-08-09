package ifpr.roteiropromo.core.interestPoint.controller;


import ifpr.roteiropromo.core.interestPoint.domain.dtos.simple.BasicGenericDTO;
import ifpr.roteiropromo.core.interestPoint.service.PaginatedService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/paginated")
public class PaginationController {

    private final PaginatedService paginatedService;


    public PaginationController(PaginatedService eventService) {
        this.paginatedService = eventService;
    }

    @GetMapping("/events")
    public Page<BasicGenericDTO> getEvents(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        return paginatedService.findEventPaginated(page, size);
    }

    @GetMapping("/hotels")
    public Page<BasicGenericDTO> getHotels(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        return paginatedService.findHotelPaginated(page, size);
    }

    @GetMapping("/experiences")
    public Page<BasicGenericDTO> getExperiences(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        return paginatedService.findExperiencesPaginated(page, size);
    }




}
