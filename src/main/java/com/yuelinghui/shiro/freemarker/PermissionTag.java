package com.yuelinghui.shiro.freemarker;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModelException;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>Equivalent to {@link org.apache.shiro.web.tags.PermissionTag}</p>
 */
public abstract class PermissionTag extends SecureTag {

    String getName(Map params) {
        return getParam(params, "name");
    }

    private static Map<Integer,String> map = new HashMap<Integer,String>();

    @Override
    protected void verifyParameters(Map params) throws TemplateModelException {
        String permission = getName(params);

        if (permission == null || permission.length() == 0) {
            throw new TemplateModelException("The 'name' tag attribute must be set.");
        }
    }

    @Override
    public void render(Environment env, Map params, TemplateDirectiveBody body) throws IOException, TemplateException {
        String p = getName(params);
        boolean show = showTagBody(p);
        if (show) {
            renderBody(env, body);
        }
    }

    /**
     * 判断有没有权限 （多个权限用逗号，并且是拥有所有权限，如果是只要拥有部分权限用 |）
     * @param p
     * @return
     */
    protected boolean isPermitted(String p) {
        if (getSubject() == null) {
            return false;
        }
        if (p.contains("|")) {
            String[] arrays = p.split("\\|");
            List<String> list = Arrays.stream(arrays).filter(s -> {
                String[] arrays1 = s.split(",");
                return getSubject() != null && getSubject().isPermittedAll(arrays1);
            }).collect(Collectors.toList());
            if (!list.isEmpty()) {
                return true;
            }
            return false;
        }
        // 表示只要有里面的部分权限，就表示有权限
        if (p.contains(",")) {
            String[] arrays = p.split(",");
            return getSubject() != null && getSubject().isPermittedAll(arrays);
        }
        return getSubject().isPermitted(p);
    }

    protected abstract boolean showTagBody(String p);
}
