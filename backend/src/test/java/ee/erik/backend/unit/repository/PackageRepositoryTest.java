package ee.erik.backend.unit.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import ee.erik.backend.model.PackageCategory;
import ee.erik.backend.model.PackageDescription;
import ee.erik.backend.model.PackageEntity;
import ee.erik.backend.model.PackageType;
import ee.erik.backend.repository.PackageDescriptionRepository;
import ee.erik.backend.repository.PackageRepository;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class PackageRepositoryTest {
    

    @Autowired
    private PackageRepository packageRepository;

    //needed for saving package descriptions
    @Autowired
    private PackageDescriptionRepository packageDescriptionRepository;

    private PackageEntity testPackageEntity;

    @BeforeEach
    public void setup() {
        
        testPackageEntity = initPackageAndDescription(
            PackageType.PREMIUM, 
            24.99, 
            PackageCategory.TV, 
            new ArrayList<PackageDescription>(Arrays.asList(
                new PackageDescription("en", "Premium Package"),
                new PackageDescription("et", "Preemium Pakett")
                )
            ));

        packageRepository.save(testPackageEntity);
        
    }

    @AfterEach
    public void cleanup() {
        packageRepository.deleteAll();
    }

    private PackageEntity initPackageAndDescription(PackageType packageType, double price, PackageCategory category, List<PackageDescription> descriptions) {
        PackageEntity savedPackage = packageRepository.save(new PackageEntity(packageType, price, category));

        for (PackageDescription desc : descriptions) {
            desc.setPackageEntity(savedPackage);
            
        }
        packageDescriptionRepository.saveAll(descriptions);

        return savedPackage;
    }

    @Test
    public void repositoryShouldReturnPackagesByCategoryAndLocale() {
        List<PackageEntity> packageEntities = packageRepository.findPackagesByCategoryAndDescriptionLocale(PackageCategory.TV, "en");

        assertThat(packageEntities).isNotEmpty();
    }

    @Test
    public void repositoryShouldReturnPackagesByLocale() {
        List<PackageEntity> packageEntities = packageRepository.findAllPackagesByDescriptionLocale("en");

        assertThat(packageEntities).isNotEmpty();
    }

    @Test
    public void repositoryShouldReturnPackageByIdAndLocale() {
        Optional<PackageEntity> packageEntity = packageRepository.findPackageByIdAndDescriptionLocale(testPackageEntity.getId(), "en");

        assertThat(packageEntity).isNotEqualTo(Optional.empty());

        assertEquals(packageEntity.get(), testPackageEntity);
    }

    @Test
    public void repositoryShouldReturnNoPackagesWhenCategoryOrLocaleNotExists() {

        List<PackageEntity> packageEntities = packageRepository.findPackagesByCategoryAndDescriptionLocale(PackageCategory.OTHER, "en");

        assertThat(packageEntities).isEmpty();

        //set to language that packagedescriptions does not have
        List<PackageEntity> emptyPackageEntities = packageRepository.findPackagesByCategoryAndDescriptionLocale(PackageCategory.TV, "es");

        assertThat(emptyPackageEntities).isEmpty();
    }

    @Test
    public void repositoryShouldReturnNoPackageWhenIdOrLocaleNotExists() {
        //use a random id that adds 2 with testPackageEntityId to make sure it does not find it by chance
        Optional<PackageEntity> packageEntity = packageRepository.findPackageByIdAndDescriptionLocale(testPackageEntity.getId() + 2L, "en");

        assertThat(packageEntity).isEqualTo(Optional.empty());
    }
}
