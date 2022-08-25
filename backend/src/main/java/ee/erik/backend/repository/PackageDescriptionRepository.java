package ee.erik.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ee.erik.backend.model.PackageDescription;

public interface PackageDescriptionRepository extends JpaRepository<PackageDescription, Long> {
    
}
