package ee.erik.backend.unit.service;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import ee.erik.backend.exception.PackageNotFoundException;
import ee.erik.backend.model.PackageCategory;
import ee.erik.backend.model.PackageEntity;
import ee.erik.backend.model.PackageDescription;
import ee.erik.backend.model.PackageType;
import ee.erik.backend.repository.PackageRepository;
import ee.erik.backend.service.PackageService;
import ee.erik.backend.service.PackageServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;

@ExtendWith(MockitoExtension.class)
public class PackageServiceTest {
    
    @Mock
    private PackageRepository packageRepository;

    @InjectMocks
    private PackageService packageService = new PackageServiceImpl();

    private PackageEntity packageEntity;

    private String defaultLocale = "en";
    
    @BeforeEach
    public void setup() throws Exception {
        MockitoAnnotations.openMocks(this);
        packageEntity = new PackageEntity(
            PackageType.PREMIUM,
            5.99, 
            PackageCategory.TV
        );
        packageEntity.setId(1L);

        List<PackageDescription> descriptions = new ArrayList<PackageDescription>();
        descriptions.add(new PackageDescription(defaultLocale, "Test"));

        packageEntity.setDescriptions(descriptions);
        
        
    }

    @Test
    public void serviceShouldReturnPackagesWithAndWithoutCategory() {
        // with category
        given(packageRepository.findPackagesByCategoryAndDescriptionLocale(PackageCategory.TV, defaultLocale)).willReturn(List.of(packageEntity));

        List<PackageEntity> packageEntities = packageService.getPackagesByCategory(Optional.of(PackageCategory.TV), null);

        assertThat(packageEntities).isNotEmpty();
        assertThat(packageEntities).contains(packageEntity);
        
        //without category
        given(packageRepository.findAllPackagesByDescriptionLocale(defaultLocale)).willReturn(List.of(packageEntity));

        List<PackageEntity> noCategoryPackageEntities = packageService.getPackagesByCategory(Optional.empty(), null);
        
        assertThat(noCategoryPackageEntities).isNotEmpty();
        assertThat(noCategoryPackageEntities).contains(packageEntity);
    }

    @Test
    public void serviceShouldReturnPackageById() {
        
        given(packageRepository.findPackageByIdAndDescriptionLocale(1L, defaultLocale)).willReturn(Optional.of(packageEntity));

        assertThat(packageService.getPackageById(1L, null)).isEqualTo(packageEntity);
    }

    @Test
    public void serviceShouldThrowExceptionWhenPackageNotFound() {
        given(packageRepository.findPackageByIdAndDescriptionLocale(2L, defaultLocale)).willReturn(Optional.empty());

        assertThrows(PackageNotFoundException.class, () -> {
            packageService.getPackageById(2L, null);
        });
    }
}
