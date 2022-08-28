package ee.erik.backend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ee.erik.backend.model.PackageEntity;
import ee.erik.backend.model.ErrorResponse;
import ee.erik.backend.model.PackageCategory;
import ee.erik.backend.service.PackageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/v1/packages")
@Tag(name = "Package", description = "Package Api")
public class PackageController {

    @Autowired
    private PackageService packageService;

    @Operation(summary = "List packages by category", description = "Returns all packages in the category with translated description. Locale is used for description translations. Default locale is set to en. Other suppored locales are: et, any other unsupported locales wil be set to default locale. Custom locale can be set using 'Accept-Language' header.", tags = {"Package"})
    @ApiResponse(responseCode = "200", description = "Success", content = @Content(array = @ArraySchema(schema = @Schema(implementation = PackageEntity.class))))
    @GetMapping(produces = {"application/json"})
    public List<PackageEntity> getPackagesByCategroy(
        @RequestParam("category") Optional<PackageCategory> category, 
        @RequestHeader(name = "Accept-Currency", required = false) String currency
    ) {   
        return packageService.getPackagesByCategory(category, currency);
    }

    @Operation(summary = "Get package by package id", description = "Returns a package with translated description. Locale is used for description translations. Default locale is set to en. Other suppored locales are: et, any other unsupported locales wil be set to default locale. Custom locale can be set using 'Accept-Language' header.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = PackageEntity.class))),
        @ApiResponse(responseCode = "404", description = "Not found. Returs error with status.", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @GetMapping(value = "/{id}", produces = {"application/json"})
    public PackageEntity getPackageById(
        @PathVariable Long id,
        @RequestHeader(name = "Accept-Currency", required = false) String currency
    ) {
        return packageService.getPackageById(id, currency);
    }
}
