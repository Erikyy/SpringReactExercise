package ee.erik.backend.util;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

@Configuration
@EnableScheduling
@EnableWebMvc
public class AppConfiguration extends AcceptHeaderLocaleResolver implements WebMvcConfigurer {
    
    //supported locales
    List<Locale> LOCALES = Arrays.asList(
         new Locale("en"),
         new Locale("et"));

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String headerLang = request.getHeader("Accept-Language");
        return headerLang == null || headerLang.isEmpty()
            ? Locale.getDefault()
            : Locale.lookup(Locale.LanguageRange.parse(headerLang), LOCALES);
    }

    @Bean
    public GroupedOpenApi packageApi() {
        return GroupedOpenApi.builder()
            .group("Tv Packages")
            .packagesToScan("ee.erik.backend")
            .build();
    }
}
