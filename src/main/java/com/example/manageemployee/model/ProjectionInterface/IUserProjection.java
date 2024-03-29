package com.example.manageemployee.model.ProjectionInterface;

import org.springframework.beans.factory.annotation.Value;

public interface IUserProjection {
    int getId();
    String getFullname();
    Role getRole();
    interface Role{
        String getName();
    }
    @Value("#{target.id.toString() + ' ' + target.fullname}")
    String getIdFullName();
}
