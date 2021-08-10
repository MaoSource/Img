package com.source.utils;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Source
 * @date 2021/08/02/14:34
 */
public class Result extends HashMap<String, Object> {

    private static final long serialVersionUID = -8713837118340960775L;

    public Result message(String message) {
        this.put("message", message);
        return this;
    }

    public Result data(Object data) {
        this.put("data", data);
        return this;
    }

    @Override
    public Result put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public String getMessage() {
        return String.valueOf(get("message"));
    }

    public Object getData() {
        return get("data");
    }
}
