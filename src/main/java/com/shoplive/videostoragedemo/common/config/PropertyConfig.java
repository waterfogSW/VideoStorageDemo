package com.shoplive.videostoragedemo.common.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.shoplive.videostoragedemo.common.properties.VideoStorageProperties;

@Configuration
@EnableConfigurationProperties(VideoStorageProperties.class)
public class PropertyConfig {

}
