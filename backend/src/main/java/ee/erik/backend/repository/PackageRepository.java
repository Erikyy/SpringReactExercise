package ee.erik.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ee.erik.backend.model.Package;
import ee.erik.backend.model.PackageCategory;

public interface PackageRepository extends JpaRepository<Package, Long> {
    List<Package> findPackagesByCategory(PackageCategory category);
}
