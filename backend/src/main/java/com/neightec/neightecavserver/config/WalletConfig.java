package com.neightec.neightecavserver.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Neightec wallet configuration
 * @author bajahitam29
 * @version 20260826
 */
@Configuration
@ConfigurationProperties(prefix = "com.neightec.wallet")
@Data
public class WalletConfig {

    private String sessionIdTemp;
}
