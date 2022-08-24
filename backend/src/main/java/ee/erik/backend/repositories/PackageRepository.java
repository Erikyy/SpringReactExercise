package ee.erik.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ee.erik.backend.models.Package;

public interface PackageRepository extends JpaRepository<Package, Long> {
    
}
