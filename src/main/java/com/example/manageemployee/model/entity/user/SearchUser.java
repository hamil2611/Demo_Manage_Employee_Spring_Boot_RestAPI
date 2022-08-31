package com.example.manageemployee.model.entity.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchUser {
    enum Operation{
        LESS,GREATER,LIKE,EQUAL,SORTDESC,SORTASC
    }
    private String property;
    private Operation operation;
    private Object value;
}
