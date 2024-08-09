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
    public Page<BasicGenericDTO> getEvent(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        return paginatedService.findPaginated(page, size);
    }




}
