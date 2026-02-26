package com.cyber.portal.sharedResources.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.SerializationHelper;

import java.io.InputStream;

@Configuration
public class WekaModelConfig {

    @Bean
    public FilteredClassifier complaintModel() {
        try {
            ClassPathResource resource =
                    new ClassPathResource("models/complaint.model");

            InputStream inputStream = resource.getInputStream();

            return (FilteredClassifier)
                    SerializationHelper.read(inputStream);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load complaint.model", e);
        }
    }
}