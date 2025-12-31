package com.study.studentOA.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_user")
@TableName(value = "tb_user")
public class User {
    @Id
    @Column(name = "user_id")
    @TableField("user_id")
    private Long userId;

    @Column(name = "username")
    @TableField("username")
    private String username;

    @Column(name = "password")
    @TableField("password")
    private String password;

    @Column(name = "identify")
    @TableField("identify")
    private Integer identify;

    @Column(name = "class_name")
    @TableField("class_name")
    private Integer className;

    public User() {
    }

    public User(Long userId, String username, String password, Integer identify, Integer className) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.identify = identify;
        this.className = className;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getIdentify() {
        return identify;
    }

    public void setIdentify(Integer identify) {
        this.identify = identify;
    }

    public Integer getClassName() {
        return className;
    }

    public void setClassName(Integer className) {
        this.className = className;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", identify=" + identify +
                ", className=" + className +
                '}';
    }
}
