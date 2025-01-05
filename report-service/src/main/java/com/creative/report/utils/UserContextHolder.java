package com.creative.report.utils;

import org.springframework.util.Assert;

public class UserContextHolder {

    private static final ThreadLocal<UserContext> userContext = new ThreadLocal<UserContext>();

    public static final UserContext getContext() {
        UserContext uCtx = userContext.get();
        if (uCtx == null) {
            uCtx = createEmptyContext();
            userContext.set(uCtx);
        }
        return userContext.get();
    }

    public static final void setContext(UserContext context) {
        Assert.notNull(context, "Only non-null UserContext instances are permitted");
        userContext.set(context);
    }
    public static final UserContext createEmptyContext() {
        return new UserContext();
    }
}
