package ifpr.roteiropromo.core.pagesource.controller;

import ifpr.roteiropromo.core.pagesource.domain.HomePageDTO;
import ifpr.roteiropromo.core.pagesource.service.PageSourceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/page-source")
public class PageSourceController {

    private final PageSourceService pageSourceService;

    public PageSourceController(PageSourceService pageSourceService) {
        this.pageSourceService = pageSourceService;
    }


    @GetMapping("/home")
    public ResponseEntity<HomePageDTO> getHomePage(){
        return ResponseEntity.ok(pageSourceService.getHomePageData());
    }


}
