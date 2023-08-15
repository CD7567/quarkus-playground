package com.cd7567.quarkus_playground.conf;

import io.smallrye.config.ConfigMapping;

import java.util.Map;

@ConfigMapping(prefix = "app.test")
public interface MapConfPojo {
    Map<String, String> map();
}
