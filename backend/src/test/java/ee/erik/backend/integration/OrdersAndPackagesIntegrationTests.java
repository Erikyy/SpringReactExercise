package ee.erik.backend.integration;

import org.hibernate.Hibernate;
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
import ee.erik.backend.model.PackageDescription;
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
    private PackageDescriptionRepository packageDescriptionRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldCreatePackageAndOrderAndReflectChanges() throws Exception {
        PackageEntity testPackageEntity = initPackageAndDescription(
            PackageType.PREMIUM, 
            24.99, 
            PackageCategory.TV, 
            new ArrayList<PackageDescription>(Arrays.asList(
                new PackageDescription("en", "Premium Package"),
                new PackageDescription("et", "Preemium Pakett")
                )
            ));
        assertThat(packageRepository.findAll()).isNotEmpty();

        //assert that should return empty when querying packages with unsupported locale
        assertThat(packageRepository.findAllPackagesByDescriptionLocale("es")).isEmpty();
        assertThat(packageRepository.findPackagesByCategoryAndDescriptionLocale(PackageCategory.TV, "es")).isEmpty();
        //same with category
        assertThat(packageRepository.findPackagesByCategoryAndDescriptionLocale(PackageCategory.OTHER, "en")).isEmpty();
        //usually PackageService takes care of that problem, by returning packages with descriptions using default locale
        assertThat(packageService.getPackagesByCategory(Optional.of(PackageCategory.TV), null)).isNotEmpty();

        MvcResult result =  mockMvc.perform(get("/packages").param("category", "TV").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        
        
        String response = result.getResponse().getContentAsString();
        String expected = new ObjectMapper().writeValueAsString(packageRepository.findAllPackagesByDescriptionLocale("en"));
        assertEquals(expected, response);

        Optional<PackageEntity> actualPackageEntity = packageRepository.findById(testPackageEntity.getId());

        assertThat(actualPackageEntity).isNotEmpty();

        assertEquals(actualPackageEntity.get().getId(), testPackageEntity.getId());
        

        mockMvc.perform(post("/orders").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(new CreateOrderDto(testPackageEntity.getId()))))
                .andExpect(status().isOk())
                .andDo(print());

        List<Order> orders = orderRepository.findAll();
        
        

        assertThat(orders).isNotEmpty();
        assertThat(orderService.getOrders(null)).isNotEmpty();
        assertThat(orderService.getOrders(null)).containsAll(orders);

        MvcResult ordersResponse = mockMvc.perform(get("/orders").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        List<Order> actual = Arrays.asList(new ObjectMapper().readValue(ordersResponse.getResponse().getContentAsString(), Order[].class));
        
        assertEquals(orders, actual);
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
