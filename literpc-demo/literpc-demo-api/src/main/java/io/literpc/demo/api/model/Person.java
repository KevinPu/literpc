package io.literpc.demo.api.model;

import java.io.Serializable;

/**
 * @author kevin Pu
 */
public class Person implements Serializable{

    private static final long serialVersionUID = -3886354859497128035L;

    private Integer userId;

    private String userName;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "Person{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                '}';
    }
}
