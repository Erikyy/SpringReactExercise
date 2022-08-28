package ee.erik.backend.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ee.erik.backend.util.AppConfiguration;
import ee.erik.backend.util.CurrencyConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/v1")
@Tag(name = "Utils", description = "Utilities Api")
public class UtilsController {


    @Operation(summary = "List supported currencies", description = "Returns all supported currencies", tags = {"Utils"})
    @ApiResponse(responseCode = "200", description = "Success", content = @Content(array = @ArraySchema(schema = @Schema(implementation = List.class))))
    @GetMapping("/currencies")
    public List<String> getSupportedCurrencies() {
        return new ArrayList<>(CurrencyConverter.rates.keySet());
    }

    @Operation(summary = "List supported languages", description = "Returns all supported languages", tags = {"Utils"})
    @ApiResponse(responseCode = "200", description = "Success", content = @Content(array = @ArraySchema(schema = @Schema(implementation = List.class))))
    @GetMapping("/languages")
    public List<String> getSupportedLanguages() {
        return AppConfiguration.LOCALES.stream().map(lang -> {
            return lang.getLanguage();
        }).toList();
    }
    
}
