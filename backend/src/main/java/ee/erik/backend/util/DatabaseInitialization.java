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

    @Bean
    CommandLineRunner initPackages(PackageRepository packageRepository, PackageDescriptionRepository packageDescriptionRepository, PackageCategoryRepository packageCategoryRepository) {
        return args -> {
            PackageCategory tvCategory = UtilFunctions.initCategory(
                    packageCategoryRepository,
                    packageDescriptionRepository,
                    "tv",
                    new ArrayList<>(new ArrayList<Description>(Arrays.asList(
                        new Description("en", "TV Packages"),
                        new Description("et", "TV Paketid")
                    ))));

            /**
             *
             * Categories are flexible, more can be added plus changes also display on frontend side too
             * Here for example is Mobile package category

             PackageCategory tvCategory = UtilFunctions.initCategory(
                    packageCategoryRepository,
                    packageDescriptionRepository,
                    "mobile",
                    new ArrayList<>(new ArrayList<Description>(Arrays.asList(
                    new Description("en", "Mobile Packages"),
                    new Description("et", "Mobiil Paketid")
             ))));
             after that we can create packages for mobile category too

             log.info("Preloading new mobile package: " + UtilFunctions.initPackageAndDescription(
                    packageRepository,
                    packageDescriptionRepository,
                    PackageType.MINI,
                    5.99,
                    mobile,
                    new ArrayList<Description>(Arrays.asList(
                    new Description("en", "Mini mobile Package. Contains basic stuff. 20+ channels included."),
                    new Description("et", "Mini mobiili Pakett. Sisaldab põhilise paketi asju. Sisaldab 20+ kanalit.")
                )
             )));
            */
            log.info("Preloading new tv package: " + UtilFunctions.initPackageAndDescription(
                    packageRepository,
                packageDescriptionRepository,
                PackageType.MINI,
                5.99, 
                tvCategory,
                new ArrayList<Description>(Arrays.asList(
                    new Description("en", "Mini tv Package. Contains basic stuff. 20+ channels included."),
                    new Description("et", "Mini tv Pakett. Sisaldab põhilise paketi asju. Sisaldab 20+ kanalit.")
                )
            )));

            log.info("Preloading new tv package: " + UtilFunctions.initPackageAndDescription(
                    packageRepository,
                    packageDescriptionRepository,
                PackageType.STANDARD, 
                9.99, 
                tvCategory,
                new ArrayList<Description>(Arrays.asList(
                    new Description("en", "Standard tv Package. Contains basic stuff + some extra features. 40+ channels included."),
                    new Description("et", "Standard tv Pakett. Sisaldab põhi paketi asju + veel mõnda ekstra tunnuseid. Sisaldab 40+ kanalit.")
                )
            )));

            log.info("Preloading new tv package: " + UtilFunctions.initPackageAndDescription(
                    packageRepository,
                    packageDescriptionRepository,
                PackageType.PREMIUM, 
                24.99, 
                tvCategory,
                new ArrayList<Description>(Arrays.asList(
                    new Description("en", "Premium tv Package. Contains standard package stuff + some extra features. 100+ channels included."),
                    new Description("et", "Premium tv Pakett. Sisaldab standard paketi asju + ekstra tunnused. Sisaldab 100+ kanalit.")
                    ))));
        };
    }


}
