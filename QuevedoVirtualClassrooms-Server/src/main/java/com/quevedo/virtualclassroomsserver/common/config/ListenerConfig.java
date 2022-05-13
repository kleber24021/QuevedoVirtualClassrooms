package com.quevedo.virtualclassroomsserver.common.config;

import jakarta.inject.Inject;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class ListenerConfig implements ServletContextListener{
    private final Config config;
    private final QueriesLoader queriesLoader;

    @Inject
    public ListenerConfig(Config config, QueriesLoader queriesLoader){
        this.config = config;
        this.queriesLoader = queriesLoader;
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        /* This method is called when the servlet context is initialized(when the Web application is deployed). */
        config.loadConfig(sce.getServletContext().getResourceAsStream("/WEB-INF/config/config.yaml"));
        queriesLoader.loadQueries(sce.getServletContext().getResourceAsStream("/WEB-INF/sql/queries.yaml"));
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        /* This method is called when the servlet Context is undeployed or Application Server shuts down. */
    }

}
