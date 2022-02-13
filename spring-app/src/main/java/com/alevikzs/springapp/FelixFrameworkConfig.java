package com.alevikzs.springapp;

import lombok.SneakyThrows;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.ServiceLoader;

@Configuration
public class FelixFrameworkConfig {

    private Framework felixFramework;

    @Bean
    @SneakyThrows
    public Framework felixFramework() {
        final ServiceLoader<FrameworkFactory> frameworkServiceLoader = ServiceLoader.load(FrameworkFactory.class);
        final Optional<FrameworkFactory> frameworkFactoryOptional = frameworkServiceLoader.findFirst();

        if (frameworkFactoryOptional.isPresent()) {
            this.felixFramework = frameworkFactoryOptional.get().newFramework(Map.of());
            this.felixFramework.init();
            this.felixFramework.start();

            return this.felixFramework;
        } else {
            throw new RuntimeException("Felix framework was not started");
        }
    }

    @PreDestroy
    @SneakyThrows
    public void preDestroy() {
        if (Objects.nonNull(this.felixFramework)) {
            this.felixFramework.stop();
            this.felixFramework.waitForStop(0);
        }
    }

}
