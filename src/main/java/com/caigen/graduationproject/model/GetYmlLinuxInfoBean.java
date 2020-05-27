package com.caigen.graduationproject.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

/**
 * Created by Yasin on 2020-04-04 22:45
 */
@Service
@ConfigurationProperties(prefix = "terimal-linux")
public class GetYmlLinuxInfoBean {
    private String linuxName;
    private String linuxPassword;
    private String hostName;

}
