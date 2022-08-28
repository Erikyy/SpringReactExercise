package ee.erik.backend.service;

import ee.erik.backend.model.PackageCategory;
import ee.erik.backend.repository.PackageCategoryRepository;
import ee.erik.backend.util.UtilFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This service does not need testing as it's only purpose is to return all categories from repository
 */
@Service
public class PackageCategoryServiceImpl implements PackageCategoryService {

    @Autowired
    private PackageCategoryRepository packageCategoryRepository;

    @Override
    public List<PackageCategory> getAllCategories() {
        return packageCategoryRepository.findAllByLanguage(UtilFunctions.getLanguageString());
    }
}
