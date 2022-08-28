package ee.erik.backend.service;

import java.util.List;
import java.util.Optional;

import ee.erik.backend.model.PackageCategory;
import ee.erik.backend.model.PackageEntity;

public interface PackageService {
    /**
     * Returns all packages in the category with translated description. If Accept-Language header is not provided or language is not supported,
     * it will return translations in english
     * 
     * @param category Package category for instance: <pre>service.getPackagesByCategory(PackageCategory.TV)</pre>
     * @return List of packages
     */
    List<PackageEntity> getPackagesByCategory(Optional<Long> category, String currency);

    /**
     * Returns a package with translated description. If Accept-Language header is not provided or language is not supported,
     * it will return translations in english
     * 
     * @param id Id of package
     * @return package
     */
    PackageEntity getPackageById(Long id, String currency);
}
