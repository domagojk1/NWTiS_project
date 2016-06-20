/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.util;

import java.sql.Connection;
import java.sql.SQLException;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;


/**
 *
 * @author domagoj
 */
public class DBConnection {
    private static DBConnection instance = null;
    private DataSource dataSource;
    
    private DBConnection() {
        PoolProperties properties = new PoolProperties();
        properties.setUrl(Configuration.getUrl());
        properties.setDriverClassName(Configuration.getDriver());
        properties.setUsername(Configuration.getUsername());
        properties.setPassword(Configuration.getPassword());
        dataSource = new org.apache.tomcat.jdbc.pool.DataSource(properties);
        dataSource.setPoolProperties(properties);
    }
    
    public static DBConnection getInstance() {
        if (instance == null)
        {
            instance = new DBConnection();
        }
        return instance;
    }
    
    public Connection getConnection() throws SQLException  {
        return dataSource.getConnection();
    }
    
    public void closeConnection(Connection connection) throws SQLException {
        if (connection != null)
            connection.close();
    }
    
    public void closeDatasource() {
        dataSource.close();
    }
}
