package nl.mib.cam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class MibCamApplication extends WebMvcConfigurerAdapter {

    public static final String PROPERTY_CAM_IMAGES_ROOT_DIR = "cam.images.root.dir";
    public static final String IMAGES_CAM1 = "/images/cam1/";
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
}
