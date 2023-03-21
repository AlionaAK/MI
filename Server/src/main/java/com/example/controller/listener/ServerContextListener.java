package com.example.controller.listener;

import com.example.dao.core.pool.impl.DatabaseConnectionPool;
import com.example.domain.server.Server;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class ServerContextListener {
    ////
    private static final String DB_INFO_FILE_PATH = "D:\\Cursach\\CW\\Server\\src\\main\\resources\\bd_info.properties";
    private static final String DRIVER = "bd.driver";
    private static final String URL = "bd.url";
    private static final String LOGIN = "bd.login";
    private static final String PASSWORD = "bd.password";
    private static final String POOL_SIZE = "bd.pool_size";
    private static final String SERVER_PORT = "server.port";
    private static final int DEFAULT_PORT = 4545;
    ////

    private static final ServerContextListener instance = new ServerContextListener();

    public static ServerContextListener getInstance() {
        return instance;
    }

    private ServerContextListener() {
    }


    public void init() {
        try {
            final InputStream fileInputStream = new FileInputStream(DB_INFO_FILE_PATH);
            final Properties properties = new Properties();
            properties.load(fileInputStream);
            ///
            final String driver = properties.getProperty(DRIVER);
            final String url = properties.getProperty(URL);
            final String login = properties.getProperty(LOGIN);
            final String password = properties.getProperty(PASSWORD);
            final String pool_size = properties.getProperty(POOL_SIZE);
            ///
            try {
                Server.PORT = Integer.valueOf(properties.getProperty(SERVER_PORT));
            } catch (NumberFormatException ex) {
                Server.PORT = DEFAULT_PORT;
            }
            ///
            DatabaseConnectionPool.getInstance().init(driver, url, login, password, pool_size);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void destroy() {
        DatabaseConnectionPool.getInstance().destroyPool();
    }
}
