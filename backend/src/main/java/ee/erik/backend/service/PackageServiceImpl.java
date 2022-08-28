package ee.erik.backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import ee.erik.backend.exception.PackageNotFoundException;
import ee.erik.backend.model.PackageCategory;
import ee.erik.backend.model.PackageEntity;
import ee.erik.backend.repository.PackageRepository;
import ee.erik.backend.util.CurrencyConverter;

import static ee.erik.backend.util.UtilFunctions.convertPrice;
import static ee.erik.backend.util.UtilFunctions.getLocaleString;

@Service
public class PackageServiceImpl implements PackageService {

    @Autowired
    private PackageRepository packageRepository;
    




    @Override
    public List<PackageEntity> getPackagesByCategory(Optional<Long> categoryId, String currency) {
        List<PackageEntity> packageEntities = new ArrayList<>();
        if (categoryId.isPresent()) {
            packageEntities = packageRepository.findPackagesByCategoryAndDescriptionLocale(categoryId.get(), getLocaleString());
        } else {
            packageEntities = packageRepository.findAllPackagesByDescriptionLocale(getLocaleString());
        }

        return packageEntities.stream().map(packageEntity -> convertPrice(packageEntity, currency)).toList();
    }

    @Override
    public PackageEntity getPackageById(Long id, String currency) {
        Optional<PackageEntity> foundPackage = packageRepository.findPackageByIdAndDescriptionLocale(id, getLocaleString());
        if (!foundPackage.isPresent()) {
            throw new PackageNotFoundException(id);
        }
        return convertPrice(foundPackage.get(), currency);
    }
}
