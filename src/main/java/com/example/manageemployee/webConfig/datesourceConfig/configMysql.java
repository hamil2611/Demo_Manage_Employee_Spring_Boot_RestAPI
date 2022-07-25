package com.example.manageemployee.webConfig.datesourceConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.ConfigurableEnvironment;

@PropertySource("classpath: application.properties")
public class configMysql {
    @Autowired
    private ConfigurableEnvironment env;
}
