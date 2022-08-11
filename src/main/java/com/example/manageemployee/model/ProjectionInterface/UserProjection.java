package com.example.manageemployee.model.ProjectionInterface;

import com.example.manageemployee.model.entity.Role;
import lombok.Value;

@Value
public class UserProjection {
    int id;
    String fullname;
    Role role;

}
