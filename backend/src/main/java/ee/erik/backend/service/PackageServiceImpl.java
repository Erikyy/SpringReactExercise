package ee.erik.backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.erik.backend.exception.PackageNotFoundException;
import ee.erik.backend.model.PackageEntity;
import ee.erik.backend.repository.PackageRepository;

import static ee.erik.backend.util.UtilFunctions.convertPrice;
import static ee.erik.backend.util.UtilFunctions.getLanguageString;

@Service
public class PackageServiceImpl implements PackageService {

    @Autowired
    private PackageRepository packageRepository;
    




    @Override
    public List<PackageEntity> getPackagesByCategory(Optional<String> category, String currency) {
        List<PackageEntity> packageEntities;
        if (category.isPresent() && !category.get().equals("")) {
            packageEntities = packageRepository.findPackagesByCategoryAndDescriptionLocale(category.get(), getLanguageString());
        } else {
            packageEntities = packageRepository.findAllPackagesByDescriptionLocale(getLanguageString());
        }

        return packageEntities.stream().map(packageEntity -> convertPrice(packageEntity, currency)).toList();
    }

    @Override
    public PackageEntity getPackageById(Long id, String currency) {
        Optional<PackageEntity> foundPackage = packageRepository.findPackageByIdAndDescriptionLocale(id, getLanguageString());
        if (!foundPackage.isPresent()) {
            throw new PackageNotFoundException(id);
        }
        return convertPrice(foundPackage.get(), currency);
    }
}
