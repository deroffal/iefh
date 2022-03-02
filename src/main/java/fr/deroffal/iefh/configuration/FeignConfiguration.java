package fr.deroffal.iefh.configuration;

import fr.deroffal.iefh.IefhApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackageClasses = IefhApplication.class)
public class FeignConfiguration {
}
