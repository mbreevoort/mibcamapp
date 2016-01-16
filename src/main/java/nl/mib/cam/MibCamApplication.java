package nl.mib.cam;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class MibCamApplication extends WebMvcConfigurerAdapter {

    public static final String PROPERTY_CAM_IMAGES_ROOT_DIR = "cam.images.root.dir";
    public static final String IMAGES_CAM1 = "/cam/images/cam1/";
    @Autowired
    Environment env;

    public static void main(String[] args) {
        SpringApplication.run(MibCamApplication.class, args);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String dir = env.getRequiredProperty(PROPERTY_CAM_IMAGES_ROOT_DIR);
        registry.addResourceHandler(IMAGES_CAM1 + "**")
                .addResourceLocations(
                        dir)
                .setCachePeriod(0);

    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        jsonConverter.setObjectMapper(objectMapper);
        return jsonConverter;
    }
}
