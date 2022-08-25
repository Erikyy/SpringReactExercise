package ee.erik.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ee.erik.backend.model.PackageEntity;
import ee.erik.backend.model.PackageCategory;

public interface PackageRepository extends JpaRepository<PackageEntity, Long> {
    
    /**
     * Return all packages that also have assigned description with locale, if there is no description with the locale that query tries to fetch,
     * no packages will be returned, packages with empty descriptions should not be displayed
    */
    @Query(value = "select p from PackageEntity p join fetch p.descriptions pd where p.category = :category and pd.locale = :locale")
    List<PackageEntity> findPackagesByCategoryAndDescriptionLocale(@Param("category") PackageCategory category, @Param("locale") String locale);
    
    /**
     * Return all packages that also have assigned description with locale, if there is no description with the locale that query tries to fetch,
     * no packages will be returned, packages with empty descriptions should not be displayed
    */
    @Query(value = "select p from PackageEntity p join fetch p.descriptions pd where pd.locale = :locale")
    List<PackageEntity> findAllPackagesByDescriptionLocale(@Param("locale") String locale);
    
    /**
     * Return all packages that also have assigned description with locale, if there is no description with the locale that query tries to fetch,
     * no packages will be returned, packages with empty descriptions should not be displayed
    */
    @Query(value = "select p from PackageEntity p join fetch p.descriptions pd where p.id = :id and pd.locale = :locale")
    Optional<PackageEntity> findPackageByIdAndDescriptionLocale(@Param("id") Long id, @Param("locale") String locale);
}
