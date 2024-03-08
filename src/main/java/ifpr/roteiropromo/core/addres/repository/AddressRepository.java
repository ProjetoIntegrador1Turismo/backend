package ifpr.roteiropromo.core.addres.repository;

import ifpr.roteiropromo.core.addres.model.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {}
