package ifpr.roteiropromo.core.address.controller;

import ifpr.roteiropromo.core.address.model.entities.Address;
import ifpr.roteiropromo.core.address.service.AddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/address")
public class AddressController {


    private final AddressService addressService;

    public AddressController(AddressService addressService){
        this.addressService = addressService;
    }

    public ResponseEntity<Address> creatOne(
            @RequestBody Address address
    ){
        return ResponseEntity.ok(addressService.create(address));
    }


}
