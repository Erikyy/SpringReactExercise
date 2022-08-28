package ee.erik.backend.unit.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import ee.erik.backend.repository.PackageCategoryRepository;
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
import ee.erik.backend.model.Description;
import ee.erik.backend.model.PackageEntity;
import ee.erik.backend.model.PackageType;
import ee.erik.backend.repository.PackageDescriptionRepository;
import ee.erik.backend.repository.PackageRepository;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class PackageRepositoryTest {
    

    @Autowired
    private PackageRepository packageRepository;

    //Only used for initializing test category, this repository is not tested here
    @Autowired
    private PackageCategoryRepository packageCategoryRepository;

    //needed for saving package descriptions
    @Autowired
    private PackageDescriptionRepository packageDescriptionRepository;

    private PackageEntity testPackageEntity;
    private PackageCategory testPackageCategory;
    @BeforeEach
    public void setup() {

        testPackageCategory = initCategory(new ArrayList<Description>(Arrays.asList(
                new Description("en", "Premium Package"),
                new Description("et", "Preemium Pakett")
        )
        ));
        testPackageEntity = initPackageAndDescription(
            PackageType.PREMIUM, 
            24.99, 
            testPackageCategory,
            new ArrayList<Description>(Arrays.asList(
                new Description("en", "Premium Package"),
                new Description("et", "Preemium Pakett")
                )
            ));

        packageRepository.save(testPackageEntity);
        
    }

    @AfterEach
    public void cleanup() {
        packageRepository.deleteAll();
    }

    private PackageCategory initCategory(List<Description> descriptions) {
        PackageCategory packageCategory = packageCategoryRepository.save(new PackageCategory());

        for (Description desc : descriptions) {
            desc.setPackageCategory(packageCategory);
        }
        packageDescriptionRepository.saveAll(descriptions);

        return packageCategory;
    }

    private PackageEntity initPackageAndDescription(PackageType packageType, double price, PackageCategory category, List<Description> descriptions) {
        PackageEntity savedPackage = packageRepository.save(new PackageEntity(packageType, price, category));

        for (Description desc : descriptions) {
            desc.setPackageEntity(savedPackage);
            
        }
        packageDescriptionRepository.saveAll(descriptions);

        return savedPackage;
    }

    @Test
    public void repositoryShouldReturnPackagesByCategoryAndLocale() {
        List<PackageEntity> packageEntities = packageRepository.findPackagesByCategoryAndDescriptionLocale(testPackageCategory.getId(), "en");

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

        List<PackageEntity> packageEntities = packageRepository.findPackagesByCategoryAndDescriptionLocale(5L, "en");

        assertThat(packageEntities).isEmpty();

        //set to language that packagedescriptions does not have
        List<PackageEntity> emptyPackageEntities = packageRepository.findPackagesByCategoryAndDescriptionLocale(testPackageCategory.getId(), "es");

        assertThat(emptyPackageEntities).isEmpty();
    }

    @Test
    public void repositoryShouldReturnNoPackageWhenIdOrLocaleNotExists() {
        //use a random id that adds 2 with testPackageEntityId to make sure it does not find it by chance
        Optional<PackageEntity> packageEntity = packageRepository.findPackageByIdAndDescriptionLocale(testPackageEntity.getId() + 2L, "en");

        assertThat(packageEntity).isEqualTo(Optional.empty());
    }
}
