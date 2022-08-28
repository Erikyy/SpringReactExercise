package ee.erik.backend.util;

import ee.erik.backend.model.Description;
import ee.erik.backend.model.PackageCategory;
import ee.erik.backend.model.PackageEntity;
import ee.erik.backend.model.PackageType;
import ee.erik.backend.repository.PackageCategoryRepository;
import ee.erik.backend.repository.PackageDescriptionRepository;
import ee.erik.backend.repository.PackageRepository;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.List;

/**
 * Some helper functions
 */
public class UtilFunctions {
    //Gets locale string, if language is unsupported, will return 'en' by default
    public static String getLocaleString() {
        String locale = LocaleContextHolder.getLocale().getLanguage();
        if (locale == null || locale == "") {
            return "en";
        } else {
            return locale;
        }
    }

    /**
     * Converts package price
     */
    public static PackageEntity convertPrice(PackageEntity packageEntity, String currency) {
        packageEntity.setPrice(CurrencyConverter.convertTo(packageEntity.getPrice(), currency));
        return packageEntity;
    }

    public static PackageEntity initPackageAndDescription(
            PackageRepository packageRepository,
            PackageDescriptionRepository packageDescriptionRepository,
            PackageType packageType,
            double price,
            PackageCategory category,
            List<Description> descriptions
    ) {
        PackageEntity savedPackage = packageRepository.save(new PackageEntity(packageType, price, category));

        for (Description desc : descriptions) {
            desc.setPackageEntity(savedPackage);

        }
        packageDescriptionRepository.saveAll(descriptions);

        return savedPackage;
    }

    public static PackageCategory initCategory(
            PackageCategoryRepository packageCategoryRepository,
            PackageDescriptionRepository packageDescriptionRepository,
            List<Description> descriptions
    ) {
        PackageCategory packageCategory = packageCategoryRepository.save(new PackageCategory());

        for (Description desc : descriptions) {
            desc.setPackageCategory(packageCategory);
        }
        packageDescriptionRepository.saveAll(descriptions);

        return packageCategory;
    }
}
