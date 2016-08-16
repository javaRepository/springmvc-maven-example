package com.company.example.outerapi.request;

import com.jtool.codegenannotation.CodeGenField;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class GetUserRequest {

    @NotNull
    @Size(min = 1, max = 100)
    @CodeGenField("查询的username")
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "GetUserRequest{" +
                "username='" + username + '\'' +
                '}';
    }

}
