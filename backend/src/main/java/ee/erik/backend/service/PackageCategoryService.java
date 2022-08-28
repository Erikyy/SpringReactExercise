package ee.erik.backend.service;

import ee.erik.backend.model.PackageCategory;

import java.util.List;

public interface PackageCategoryService {
    List<PackageCategory> getAllCategories();
}
