package com.kutli.userservice.config;

import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
//@Profile(value= {"dev", "qa", "prod"})
//@Profile("!default")
//@EnableEurekaClient
public class EurekaClientConfiguration {

}
