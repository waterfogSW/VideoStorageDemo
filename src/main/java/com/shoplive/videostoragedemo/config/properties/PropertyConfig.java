package com.shoplive.videostoragedemo.config.properties;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(VideoStorageProperties.class)
public class PropertyConfig {

}
