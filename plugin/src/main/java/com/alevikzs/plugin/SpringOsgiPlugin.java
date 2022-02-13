package com.alevikzs.plugin;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import java.util.Map;

public final class SpringOsgiPlugin implements BundleActivator {

    public void start(final BundleContext context) {
    }

    public void stop(final BundleContext context) {
    }

    public Map<String, Object> execute() {
        return Map.of("key", "value");
    }

}
