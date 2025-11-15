package com.docfaq.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

/**
 * AWS configuration class for setting up S3 client.
 */
@Configuration
public class AwsConfig {

    @Value("${aws.s3.region}")
    private String region;

    /**
     * Creates and configures the S3 client bean.
     * Uses default credential provider chain which will look for credentials in:
     * 1. Environment variables (AWS_ACCESS_KEY_ID, AWS_SECRET_ACCESS_KEY)
     * 2. Java system properties (aws.accessKeyId, aws.secretKey)
     * 3. Credential profiles file (~/.aws/credentials)
     * 4. IAM roles for EC2 instances
     *
     * @return configured S3Client
     */
    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .region(Region.of(region))
                .build();
    }
}