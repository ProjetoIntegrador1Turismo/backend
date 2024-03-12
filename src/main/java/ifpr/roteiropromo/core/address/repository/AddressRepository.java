package ifpr.roteiropromo.core.address.repository;

import ifpr.roteiropromo.core.address.model.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {}
