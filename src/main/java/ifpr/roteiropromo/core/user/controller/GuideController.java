package ifpr.roteiropromo.core.user.controller;

import ifpr.roteiropromo.core.user.domain.dtos.UserDTO;
import ifpr.roteiropromo.core.user.domain.dtos.UserDTOForm;
import ifpr.roteiropromo.core.user.domain.entities.Guide;
import ifpr.roteiropromo.core.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/guide")
@Log4j2
@RequiredArgsConstructor
public class GuideController {

    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Guide> createNewGuide(@RequestBody UserDTOForm userDTOForm){
        return ResponseEntity.ok(userService.createGuide(userDTOForm));
    }

    @GetMapping()
    public ResponseEntity<List<Guide>> getOne(){
        return ResponseEntity.ok(userService.getAllGuides());
    }

}
