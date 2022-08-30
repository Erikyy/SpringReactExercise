package ee.erik.backend.repository;

import ee.erik.backend.model.PackageType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PackageTypeRepository extends JpaRepository<PackageType, Long> {
}
