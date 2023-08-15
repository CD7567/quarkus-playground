package com.cd7567.quarkus_playground.conf;

import io.smallrye.config.ConfigMapping;

/**
 * Pojo for collecting microprofile settings
 */
@ConfigMapping(prefix = "app.greeting")
public interface HelloConfPojo {
    Message message();

    interface Message {
        String polite();
        String regular();
    }
}
