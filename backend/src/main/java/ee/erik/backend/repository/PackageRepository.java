package ee.erik.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ee.erik.backend.model.PackageEntity;

public interface PackageRepository extends JpaRepository<PackageEntity, Long> {
    
    /**
     * Return all packages that also have assigned description with locale, if there is no description with the locale that query tries to fetch,
     * no packages will be returned, packages with empty descriptions should not be displayed
    */
    @Query(value = "select p from PackageEntity p join fetch p.category pc join fetch p.descriptions pd where pc.name = :category and pd.language = :language")
    List<PackageEntity> findPackagesByCategoryAndDescriptionLocale(@Param("category") String category, @Param("language") String language);
    
    /**
     * Return all packages that also have assigned description with locale, if there is no description with the locale that query tries to fetch,
     * no packages will be returned, packages with empty descriptions should not be displayed
    */
    @Query(value = "select p from PackageEntity p join fetch p.category join fetch p.descriptions pd where pd.language = :language")
    List<PackageEntity> findAllPackagesByDescriptionLocale(@Param("language") String language);
    
    /**
     * Return all packages that also have assigned description with locale, if there is no description with the locale that query tries to fetch,
     * no packages will be returned, packages with empty descriptions should not be displayed
    */
    @Query(value = "select p from PackageEntity p join fetch p.category join fetch p.descriptions pd where p.id = :id and pd.language = :language")
    Optional<PackageEntity> findPackageByIdAndDescriptionLocale(@Param("id") Long id, @Param("language") String language);
}
