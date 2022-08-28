package ee.erik.backend.util;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ee.erik.backend.model.PackageEntity;
import ee.erik.backend.model.PackageCategory;
import ee.erik.backend.model.PackageDescription;
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

    @Bean
    CommandLineRunner initPackages(PackageRepository packageRepository, PackageDescriptionRepository packageDescriptionRepository) {
        return args -> {
            this.packageDescriptionRepository = packageDescriptionRepository;
            this.packageRepository = packageRepository;

            log.info("Preloading new tv package: " + initPackageAndDescription(
                PackageType.MINI,
                5.99, 
                PackageCategory.TV, 
                new ArrayList<PackageDescription>(Arrays.asList(
                    new PackageDescription("en", "Mini tv Package. Contains basic stuff. 20+ channels included."),
                    new PackageDescription("et", "Mini tv Pakett. Sisaldab põhilise paketti asju. Sisaldab 20+ kanalit.")
                )
            )));

            log.info("Preloading new tv package: " + initPackageAndDescription(
                PackageType.STANDARD, 
                9.99, 
                PackageCategory.TV, 
                new ArrayList<PackageDescription>(Arrays.asList(
                    new PackageDescription("en", "Standard tv Package. Contains basic stuff + some extra features. 40+ channels included."),
                    new PackageDescription("et", "Standard tv Pakett. Sisaldab põhi paketti asju + veel mõnda ekstra tunnuseid. Sisaldab 40+ kanalit.")
                )
            )));

            log.info("Preloading new tv package: " + initPackageAndDescription(
                PackageType.PREMIUM, 
                24.99, 
                PackageCategory.TV,
                new ArrayList<PackageDescription>(Arrays.asList(
                    new PackageDescription("en", "Premium tv Package. Contains standard package stuff + some extra features. 100+ channels included."),
                    new PackageDescription("et", "Preemium tv Pakett. Sisaldab standard paketti asju + veel mõnda ekstra tunnuseid. Sisaldab 100+ kanalit.")
                    )
                )));
        };
    }

    private PackageEntity initPackageAndDescription(PackageType packageType, double price, PackageCategory category, List<PackageDescription> descriptions) {
        PackageEntity savedPackage = packageRepository.save(new PackageEntity(packageType, price, category));

        for (PackageDescription desc : descriptions) {
            desc.setPackageEntity(savedPackage);
            
        }
        packageDescriptionRepository.saveAll(descriptions);

        return savedPackage;
    }
}
