package org.cooze.bean.domain;

import com.sun.tools.javac.util.List;

/**
 * @author xianzhe_song
 * @version 1.0.0
 * @desc
 * @date 2017/4/19
 */
public class Student {

    private String name;
    private String clazz;

    private List<String> orgs;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public List<String> getOrgs() {
        return orgs;
    }

    public void setOrgs(List<String> orgs) {
        this.orgs = orgs;
    }
}
