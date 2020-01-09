package com.rulsion.file.docTest.entity;

import lombok.Data;

@Data
public class User {
    private Integer id;

    private String account;

    private String nickName;

    private String realName;

    private String mobile;

    private String password;

    private String headImg;

    private String identity;

    private String createTime;

    private String lastLoginTime;

}