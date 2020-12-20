package com.kaithy.xu.springboot01.autoconfiguration.prop;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Properties;

/**
 * @author kaithy.xu
 * @date 2020-12-20 15:44
 */
@ConfigurationProperties(prefix = "spring.scheool")
@Getter
@Setter
public final class SchoolPropertiesConfiguration {

    private String studentIds;

    private String studentNames;

    private String klassIds;

    private String klassNames;


}
