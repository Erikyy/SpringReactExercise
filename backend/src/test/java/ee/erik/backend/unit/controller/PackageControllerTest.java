package ee.erik.backend.unit.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Optional;

import ee.erik.backend.controller.PackageController;
import ee.erik.backend.model.PackageCategory;
import ee.erik.backend.model.PackageEntity;
import ee.erik.backend.model.PackageType;
import ee.erik.backend.service.PackageService;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(PackageController.class)
@AutoConfigureMockMvc
public class PackageControllerTest {
    

    @MockBean
    private PackageService packageService;

    @Autowired
    private MockMvc mockMvc;

    private PackageEntity packageEntity;

    @BeforeEach
    public void setup() {
        packageEntity = new PackageEntity(PackageType.PREMIUM, 9.99, PackageCategory.TV);
        packageEntity.setId(1L);
    }

    @Test
    public void controllerShouldReturnListOfPackagesWithAndWithoutCategory() throws Exception {
        given(packageService.getPackagesByCategory(Optional.of(PackageCategory.TV), null)).willReturn(List.of(packageEntity));
        
        mockMvc.perform(get("/v1/packages").param("category", "TV").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0]").value(packageEntity))
                .andDo(print());

        given(packageService.getPackagesByCategory(Optional.empty(), null)).willReturn(List.of(packageEntity));
        
        mockMvc.perform(get("/v1/packages").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0]").value(packageEntity))
                .andDo(print());
    }

    @Test
    public void controllerShouldReturnPackageById() throws Exception {
        given(packageService.getPackageById(1L, null)).willReturn(packageEntity);

        mockMvc.perform(get("/v1/packages/{id}", 1L).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(packageEntity))
                .andDo(print());
    }
}
