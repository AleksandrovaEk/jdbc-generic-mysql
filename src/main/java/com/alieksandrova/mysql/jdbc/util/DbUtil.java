package com.alieksandrova.mysql.jdbc.util;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DbUtil {
    private static final Logger LOG = LogManager.getLogger(DbUtil.class);
    private static BasicDataSource dataSource;

    static {
        try {
            dataSource = new BasicDataSource();
            Properties properties = new Properties();
            InputStream inputStream = DbUtil.class.getClassLoader().getResourceAsStream("dbprops.properties");
            if (inputStream == null) {
                throw new IOException("File not found");
            }
            properties.load(inputStream);
            dataSource.setDriverClassName(properties.getProperty("DB.DRIVER_CLASS"));
            dataSource.setUrl(properties.getProperty("DB.URL"));
            dataSource.setUsername(properties.getProperty("DB.USER"));
            dataSource.setPassword(properties.getProperty("DB.PASSWORD"));
            dataSource.setMaxIdle(20);
            dataSource.setMaxTotal(10);
        } catch (IOException e) {
            LOG.error("Something went wrong. Connection isn't established. " + "Cause: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static BasicDataSource getDataSource() {
        return dataSource;
    }

}
