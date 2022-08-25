package ee.erik.backend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import ee.erik.backend.model.PackageEntity;
import ee.erik.backend.model.PackageCategory;
import ee.erik.backend.repository.PackageRepository;

@RestController
@RequestMapping("/packages")
public class PackageController {

    @Autowired
    private PackageRepository packageRepository;

    @GetMapping
    public List<PackageEntity> getPackagesByCategroy(@RequestParam("category") Optional<PackageCategory> category) {   
        if (category.isPresent()) {
            return packageRepository.findPackagesByCategoryAndDescriptionLocale(category.get(), getLocaleString());
        } else {
            return packageRepository.findAllPackagesByDescriptionLocale(getLocaleString());
        }
    }

    @GetMapping("/{id}")
    public PackageEntity getPackageById(@PathVariable Long id) {
        PackageEntity foundPackage = packageRepository.findPackageByIdAndDescriptionLocale(id, getLocaleString()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Package not found: " + id));
        return foundPackage;
    }

    private String getLocaleString() {
        
        String locale = LocaleContextHolder.getLocale().getLanguage(); 
        if (locale == null || locale == "") {
            return "en";
        } else {
            return locale;
        }
    }
}
