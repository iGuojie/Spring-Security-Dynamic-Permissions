package com.iguojie.rbac.config.filter;

import com.iguojie.rbac.bo.RoleWithMenu;
import com.iguojie.rbac.dao.RoleDao;
import com.iguojie.rbac.dao.UserDao;
import com.iguojie.rbac.entity.Menu;
import com.iguojie.rbac.entity.RoleMenu;
import com.iguojie.rbac.properties.RBACProperties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.log.LogMessage;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Component
public class MyFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    @Autowired
    private RoleDao roleDao;

    @Autowired
    private RBACProperties rbacProperties;

    private Map<String, Object> ignoreUrlMap = new HashMap<>();

    private Map<String, List<String>> menuRoleListMap = new HashMap<>();

    AntPathMatcher antPathMatcher = new AntPathMatcher();


    @PostConstruct
    public void initialize() {
        //设置已经忽略的url
        String ignoreUrl = rbacProperties.getIgnoreAuthUrls();
        String[] split = ignoreUrl.split(",");
        for (String s : split) {
            ignoreUrlMap.put(s, new Object());
        }

        // name - url
        List<RoleWithMenu> roleWithMenus = roleDao.queryRoleWithMenu();

        for (RoleWithMenu roleWithMenu : roleWithMenus) {
            String[] urls = roleWithMenu.getUrl().split(",");
            for (String url : urls) {
                List<String> roleList = menuRoleListMap.get(url);
                if (ObjectUtils.isEmpty(roleList)) {
                    List<String> roles = new ArrayList<>();
                    roles.add(roleWithMenu.getRoleName());
                    menuRoleListMap.put(url, roles);
                } else {
                    roleList.add(roleWithMenu.getRoleName());
                }
            }
        }
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws AccessDeniedException {
        final HttpServletRequest request = ((FilterInvocation) o).getRequest();
        String requestURI = request.getRequestURI();
        if (!ObjectUtils.isEmpty(ignoreUrlMap.get(requestURI))) {
            return null;
        }
        for (Map.Entry<String, List<String>> entry : menuRoleListMap.entrySet()) {
            if (antPathMatcher.match(entry.getKey(), requestURI)) {
                return SecurityConfig.createList(String.valueOf(entry.getValue()));
            }
        }
        throw new AccessDeniedException("没有找到匹配的权限");
    }


    protected final Log logger = LogFactory.getLog(getClass());

    private final Map<RequestMatcher, Collection<ConfigAttribute>> requestMap;

    public MyFilterInvocationSecurityMetadataSource() {
        this.requestMap = new LinkedHashMap<>();
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        Set<ConfigAttribute> allAttributes = new HashSet<>();
        this.requestMap.values().forEach(allAttributes::addAll);
        return allAttributes;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
