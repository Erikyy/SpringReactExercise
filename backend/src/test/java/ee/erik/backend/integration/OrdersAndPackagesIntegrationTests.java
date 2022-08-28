package ee.erik.backend.integration;

import ee.erik.backend.repository.PackageCategoryRepository;
import ee.erik.backend.service.PackageCategoryService;
import ee.erik.backend.util.UtilFunctions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import ee.erik.backend.dto.CreateOrderDto;
import ee.erik.backend.model.Order;
import ee.erik.backend.model.PackageCategory;
import ee.erik.backend.model.Description;
import ee.erik.backend.model.PackageEntity;
import ee.erik.backend.model.PackageType;
import ee.erik.backend.repository.OrderRepository;
import ee.erik.backend.repository.PackageDescriptionRepository;
import ee.erik.backend.repository.PackageRepository;
import ee.erik.backend.service.OrderService;
import ee.erik.backend.service.PackageService;



@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class OrdersAndPackagesIntegrationTests {


    @Autowired
    private OrderService orderService;

    @Autowired
    private PackageService packageService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PackageRepository packageRepository;

    @Autowired
    private PackageCategoryRepository packageCategoryRepository;

    @Autowired
    private PackageDescriptionRepository packageDescriptionRepository;
    @Autowired
    private PackageCategoryService packageCategoryService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldCreatePackageAndOrderAndReflectChanges() throws Exception {
        PackageCategory testCategory = UtilFunctions.initCategory(
                packageCategoryRepository,
                packageDescriptionRepository,
                "test",
                new ArrayList<Description>(Arrays.asList(
                    new Description("en", "TV Packages"),
                    new Description("et", "TV Paketid")
                )
        ));

        PackageEntity testPackageEntity = UtilFunctions.initPackageAndDescription(
                packageRepository,
            packageDescriptionRepository,
            PackageType.PREMIUM, 
            24.99, 
            testCategory,
            new ArrayList<Description>(Arrays.asList(
                new Description("en", "Premium Package"),
                new Description("et", "Preemium Pakett")
                )
            ));

        assertThat(packageRepository.findAll()).isNotEmpty();
        assertThat(packageCategoryRepository.findAll()).isNotEmpty();
        assertThat(packageCategoryService.getAllCategories()).isNotEmpty();
        //assert that should return empty when querying packages with unsupported locale
        assertThat(packageRepository.findAllPackagesByDescriptionLocale("es")).isEmpty();
        assertThat(packageRepository.findPackagesByCategoryAndDescriptionLocale(testCategory.getName(), "es")).isEmpty();
        //same with category that does not exist in database
        assertThat(packageRepository.findPackagesByCategoryAndDescriptionLocale("nonexistant_category", "en")).isEmpty();
        //usually PackageService takes care of that problem, by returning packages with descriptions using default locale
        assertThat(packageService.getPackagesByCategory(Optional.of(testCategory.getName()), null)).isNotEmpty();

        MvcResult result =  mockMvc.perform(get("/v1/packages").param("category", testCategory.getName()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        
        
        List<PackageEntity> response = Arrays.asList(new ObjectMapper().readValue(result.getResponse().getContentAsString(), PackageEntity[].class));
        List<PackageEntity> expected = packageRepository.findPackagesByCategoryAndDescriptionLocale(testCategory.getName(), "en");
        assertThat(expected).isNotEmpty();
        assertThat(response).isNotEmpty();

        //Only one package in test category
        assertEquals(expected.get(0).getId(), response.get(0).getId());

        Optional<PackageEntity> actualPackageEntity = packageRepository.findById(testPackageEntity.getId());

        assertThat(actualPackageEntity).isNotEmpty();

        assertEquals(actualPackageEntity.get().getId(), testPackageEntity.getId());
        

        mockMvc.perform(post("/v1/orders").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(new CreateOrderDto(testPackageEntity.getId()))))
                .andExpect(status().isOk())
                .andDo(print());

        List<Order> orders = orderRepository.findAll();
        
        

        assertThat(orders).isNotEmpty();
        assertThat(orderService.getOrders(null)).isNotEmpty();
        assertThat(orderService.getOrders(null)).containsAll(orders);

        MvcResult ordersResponse = mockMvc.perform(get("/v1/orders").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        List<Order> actual = Arrays.asList(new ObjectMapper().readValue(ordersResponse.getResponse().getContentAsString(), Order[].class));
        
        assertEquals(orders, actual);
    }
    

}
