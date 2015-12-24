package org.seanano.coop.restapi;

import org.glassfish.jersey.server.ResourceConfig;
import org.seanano.coop.hardware.CoopFactory;

/**
 * Configuration class for the web application.
 */
public class CoopConfiguration extends ResourceConfig {
    /**
     * Creates a new CoopConfiguration.
     * 
     * @throws Exception if an error occurs initializing the application.
     */
    public CoopConfiguration() throws Exception {
        CoopFactory.initialize();
    }
}
