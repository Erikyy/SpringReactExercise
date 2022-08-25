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
                    new PackageDescription("en", "Mini Package"),
                    new PackageDescription("et", "Mini Pakett")
                )
            )));

            log.info("Preloading new tv package: " + initPackageAndDescription(
                PackageType.STANDARD, 
                9.99, 
                PackageCategory.TV, 
                new ArrayList<PackageDescription>(Arrays.asList(
                    new PackageDescription("en", "Standard Package"),
                    new PackageDescription("et", "Standard Pakett")
                )
            )));

            log.info("Preloading new tv package: " + initPackageAndDescription(
                PackageType.PREMIUM, 
                24.99, 
                PackageCategory.TV, 
                new ArrayList<PackageDescription>(Arrays.asList(
                    new PackageDescription("en", "Premium Package"),
                    new PackageDescription("et", "Preemium Pakett")
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
