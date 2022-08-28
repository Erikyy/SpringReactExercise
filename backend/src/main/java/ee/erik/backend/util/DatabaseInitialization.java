package ee.erik.backend.util;

import ee.erik.backend.repository.PackageCategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ee.erik.backend.model.PackageEntity;
import ee.erik.backend.model.PackageCategory;
import ee.erik.backend.model.Description;
import ee.erik.backend.model.PackageType;
import ee.erik.backend.repository.PackageDescriptionRepository;
import ee.erik.backend.repository.PackageRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class DatabaseInitialization {
    
    private final static Logger log = LoggerFactory.getLogger(DatabaseInitialization.class);

    private PackageRepository packageRepository;
    private PackageDescriptionRepository packageDescriptionRepository;
    private PackageCategoryRepository packageCategoryRepository;
    @Bean
    CommandLineRunner initPackages(PackageRepository packageRepository, PackageDescriptionRepository packageDescriptionRepository, PackageCategoryRepository packageCategoryRepository) {
        return args -> {
            this.packageDescriptionRepository = packageDescriptionRepository;
            this.packageRepository = packageRepository;
            this.packageCategoryRepository = packageCategoryRepository;
            PackageCategory tvCategory = initCategory(
                    new ArrayList<>(new ArrayList<Description>(Arrays.asList(
                        new Description("en", "TV Package"),
                        new Description("et", "TV Pakett")
                    ))));

            log.info("Preloading new tv package: " + initPackageAndDescription(
                PackageType.MINI,
                5.99, 
                tvCategory,
                new ArrayList<Description>(Arrays.asList(
                    new Description("en", "Mini tv Package. Contains basic stuff. 20+ channels included."),
                    new Description("et", "Mini tv Pakett. Sisaldab põhilise paketti asju. Sisaldab 20+ kanalit.")
                )
            )));

            log.info("Preloading new tv package: " + initPackageAndDescription(
                PackageType.STANDARD, 
                9.99, 
                tvCategory,
                new ArrayList<Description>(Arrays.asList(
                    new Description("en", "Standard tv Package. Contains basic stuff + some extra features. 40+ channels included."),
                    new Description("et", "Standard tv Pakett. Sisaldab põhi paketti asju + veel mõnda ekstra tunnuseid. Sisaldab 40+ kanalit.")
                )
            )));

            log.info("Preloading new tv package: " + initPackageAndDescription(
                PackageType.PREMIUM, 
                24.99, 
                tvCategory,
                new ArrayList<Description>(Arrays.asList(
                    new Description("en", "Premium tv Package. Contains standard package stuff + some extra features. 100+ channels included."),
                    new Description("et", "Preemium tv Pakett. Sisaldab standard paketti asju + veel mõnda ekstra tunnuseid. Sisaldab 100+ kanalit.")
                    )
                )));
        };
    }

    private PackageEntity initPackageAndDescription(PackageType packageType, double price, PackageCategory category, List<Description> descriptions) {
        PackageEntity savedPackage = packageRepository.save(new PackageEntity(packageType, price, category));

        for (Description desc : descriptions) {
            desc.setPackageEntity(savedPackage);
            
        }
        packageDescriptionRepository.saveAll(descriptions);

        return savedPackage;
    }

    private PackageCategory initCategory(List<Description> descriptions) {
        PackageCategory packageCategory = packageCategoryRepository.save(new PackageCategory());

        for (Description desc : descriptions) {
            desc.setPackageCategory(packageCategory);
        }
        packageDescriptionRepository.saveAll(descriptions);

        return packageCategory;
    }
}
