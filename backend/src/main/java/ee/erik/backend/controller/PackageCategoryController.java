package ee.erik.backend.controller;

import ee.erik.backend.model.PackageCategory;
import ee.erik.backend.service.PackageCategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/categories")
@Tag(name = "PackageCategories", description = "Package Category Api")
public class PackageCategoryController {

    @Autowired
    private PackageCategoryService packageCategoryService;

    @GetMapping(produces = { "application/json" })
    public List<PackageCategory> getAllCategories() {
        return packageCategoryService.getAllCategories();
    }
}
