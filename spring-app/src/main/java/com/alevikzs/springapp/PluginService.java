package com.alevikzs.springapp;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.launch.Framework;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class PluginService {

    public static final String PLUGIN_DIRECTORY = "plugins";

    private final Framework felixFramework;

    @SneakyThrows
    public void upload(final MultipartFile multipartFile) {
        if (!Files.exists(Paths.get(PLUGIN_DIRECTORY))) {
            Files.createDirectories(Paths.get(PLUGIN_DIRECTORY));
        }

        Files.write(this.getPath(), multipartFile.getBytes());
    }

    @SneakyThrows
    public Object execute(final String className, final String methodName) {
        final BundleContext bc = this.felixFramework.getBundleContext();

        final Bundle bundle = bc.installBundle("file:" + this.getPath());
        bundle.start();
        bundle.update();

        final Class<?> clazz = bundle.loadClass(className);
        final Method method = clazz.getMethod(methodName);
        final Object instance = clazz.getConstructor().newInstance();
        final Object output = method.invoke(instance);

        bundle.stop();

        return output;
    }

    private Path getPath() {
        return Paths.get(PLUGIN_DIRECTORY + "/artifact.jar");
    }

}
