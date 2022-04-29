package com.quevedo.quevedovirtualclassroomsserver.common.config;

import jakarta.inject.Inject;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class ListenerConfig implements ServletContextListener{
    private final Config config;

    @Inject
    public ListenerConfig(Config config){
        this.config = config;
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        /* This method is called when the servlet context is initialized(when the Web application is deployed). */
        config.loadConfig(sce.getServletContext().getResourceAsStream("/WEB-INF/config/config.yaml"));

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        /* This method is called when the servlet Context is undeployed or Application Server shuts down. */
    }

}
