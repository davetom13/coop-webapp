package org.seanano.coop.restapi;

import org.glassfish.jersey.server.ResourceConfig;
import org.seanano.coop.hardware.CoopFactory;

import io.swagger.jaxrs.config.BeanConfig;

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
        configureSwagger();

        CoopFactory.initialize();
    }

    /**
     * Performs the swagger configuration.
     */
    private void configureSwagger() {
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setTitle("CoopControl");
        beanConfig.setDescription("Monitoring and control of one or more chicken coops");
        beanConfig.setVersion("0.1.0");
        beanConfig.setSchemes(new String[] { "http" });
        beanConfig.setBasePath("/CoopControl/rest");
        beanConfig.setResourcePackage("io.swagger.resources,org.seanano.coop.restapi.services");
        beanConfig.setPrettyPrint(true);
        beanConfig.setScan(true);
    }
}
