package com.neightec.neightecavserver.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * contaons necessary filepaths for neightec server
 * @author nsu
 * @version 20230309
 */
@Configuration
@ConfigurationProperties(prefix = "com.neightec.av-server.filepathsconfig")
@Data
public class NeightecFilePathsConfig {

    private String fileSignatureCsv;
    private String fileSignatureXlsx;
}
