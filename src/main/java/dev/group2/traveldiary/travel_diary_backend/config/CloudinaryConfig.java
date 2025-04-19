package dev.group2.traveldiary.travel_diary_backend.config;
import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary cloudinary(){
        Map<String,Object> config = new HashMap<>();
        config.put("cloud_name","wonton79");
        config.put("api_key","633373669143866");
        config.put("api_secret","JYHIrH2KiBBaROItt1yWZQ_FO9Q");
        return new Cloudinary(config);
    }
}
