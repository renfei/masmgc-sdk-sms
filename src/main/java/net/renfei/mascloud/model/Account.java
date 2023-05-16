package net.renfei.mascloud.model;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 账户
 *
 * @author renfei
 */
public class Account {
    private String name;
    private String password;
    private String ecname;

    public Account(String name, String password, String ecname) {
        this.setName(name);
        this.setPassword(password);
        this.setEcname(ecname);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("name is blank");
        } else {
            this.name = name;
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password == null) {
            throw new NullPointerException("password");
        } else {
            this.password = DigestUtils.md5Hex(password);
        }
    }

    public String getEcname() {
        return ecname;
    }

    public void setEcname(String ecname) {
        this.ecname = ecname;
    }

    @Override
    public String toString() {
        return "Account{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", ecname='" + ecname + '\'' +
                '}';
    }
}
