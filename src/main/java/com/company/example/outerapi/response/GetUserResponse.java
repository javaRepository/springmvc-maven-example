package com.company.example.outerapi.response;

import com.company.example.model.user.User;
import com.jtool.codegenannotation.CodeGenField;

import javax.validation.constraints.NotNull;

public class GetUserResponse {

    @NotNull
    @CodeGenField("状态码, 0：完成")
    private String code;

    @NotNull
    @CodeGenField("用户信息")
    private User user;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "GetUserResponse{" +
                "code='" + code + '\'' +
                ", user=" + user +
                '}';
    }

}
