package com.neightec.neightecavserver.config;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "com.neightec.av-server.datasource")
@Log4j2
public class PrimaryDataSourceConfig {

    private String url;
    private String serverName;
    private Integer portNumber;
    private String databaseName;
    private String schema;
    private String driverClassName;
    private String username;
    private String password;
    private Properties primary;

    @Override
    public String toString() {
        return "PostgresConfig{" +
                "serverName='" + serverName + '\'' +
                ", portNumber=" + portNumber +
                ", databaseName='" + databaseName + '\'' +
                ", primary=<masked>" +
                '}';
    }

//    public String getServerName() {
//        return serverName;
//    }
//
//    public void setServerName(String serverName) {
//        this.serverName = serverName;
//    }
//
//    public Integer getPortNumber() {
//        return portNumber;
//    }
//
//    public void setPortNumber(Integer portNumber) {
//        this.portNumber = portNumber;
//    }
//
//    public String getDatabaseName() {
//        return databaseName;
//    }
//
//    public void setDatabaseName(String databaseName) {
//        this.databaseName = databaseName;
//    }
//
//    public Properties getPrimary() {
//        return primary;
//    }
//
//    public void setPrimary(Properties primary) {
//        this.primary = primary;
//    }
}
