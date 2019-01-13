package com.ctrip.framework.cs;

import com.ctrip.framework.cs.cacheRefresh.CacheCell;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jiang.j on 2016/5/17.
 */
public class RedisCache implements CacheCell {
    private String id;

    public RedisCache(String id) {
        this.id = id;

    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public boolean refresh() {
        return true;
    }

    @Override
    public Map<String, Object> getStatus() {
        HashMap<String, Object> rtn = new HashMap<>();
        rtn.put("hello", id);
        rtn.put("ni", "dddd");
        return rtn;
    }

    @Override
    public Object getByKey(String key) {
        Person p = new Person();
        p.name = "peter";
        p.des = "secret";
        return p;
    }

    @Override
    public Iterable<String> keys() {
        return null;
    }

    @Override
    public int size() {
        return 1;
    }

    public class Person {
        public String name;
        public String des;
    }
}
