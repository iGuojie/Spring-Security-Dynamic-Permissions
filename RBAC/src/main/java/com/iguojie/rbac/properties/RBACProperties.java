package com.iguojie.rbac.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
@ConfigurationProperties(prefix = "rbac")
public class RBACProperties {
    private String ignoreAuthUrls = "/verificationCode.do";

    public String getIgnoreAuthUrls() {
        return ObjectUtils.isEmpty(ignoreAuthUrls) ? "" : ignoreAuthUrls;
    }

    public void setIgnoreAuthUrls(String ignoreAuthUrls) {
        this.ignoreAuthUrls = ignoreAuthUrls;
    }
}
