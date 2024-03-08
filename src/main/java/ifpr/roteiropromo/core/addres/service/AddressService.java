package ifpr.roteiropromo.core.addres.service;

import ifpr.roteiropromo.core.addres.model.entities.Address;
import ifpr.roteiropromo.core.addres.repository.AddressRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    public Address create(Address address) {
        return addressRepository.save(address);
    }


}
