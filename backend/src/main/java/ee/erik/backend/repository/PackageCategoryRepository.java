package ee.erik.backend.repository;

import ee.erik.backend.model.PackageCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PackageCategoryRepository extends JpaRepository<PackageCategory, Long> {
    @Query(value = "select p from PackageCategory p join fetch p.descriptions pd where pd.language = :language")
    List<PackageCategory> findAllByLanguage(@Param("language") String language);
}
