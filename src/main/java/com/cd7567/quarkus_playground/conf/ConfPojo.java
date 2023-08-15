package com.cd7567.quarkus_playground.conf;

import io.smallrye.config.ConfigMapping;

/**
 * Config pojo to read app configuration
 */
@ConfigMapping(prefix = "app")
public interface ConfPojo {
    Temporary tmp();

    /**
     * Represents app tmp storage
     */
    interface Temporary {
        /**
         * @return Absolute path of tmp directory
         */
        String path();

        /**
         * @return Suffix of storage subdirectory
         */
        String storage();

        /**
         * @return Suffix of log subdirectory
         */
        String log();
    }
}