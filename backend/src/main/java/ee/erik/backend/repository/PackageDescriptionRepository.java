package ee.erik.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ee.erik.backend.model.Description;

//no tests for this as there is no custom functions to test
public interface PackageDescriptionRepository extends JpaRepository<Description, Long> {
    
}
