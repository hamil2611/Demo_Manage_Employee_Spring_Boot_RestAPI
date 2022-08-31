package com.example.manageemployee;

import com.example.manageemployee.controller.AdminController;
import com.example.manageemployee.model.dto.UserDto;
import com.example.manageemployee.model.entity.Role;
import com.example.manageemployee.model.entity.user.User;
import com.example.manageemployee.service.userservice.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@WebMvcTest(AdminController.class)
public class AdminControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    UserServiceImpl userServiceImpl;

    @Test
    public void testFindAllEmployee() throws Exception{
        List<UserDto> userList = new ArrayList<>();

        Role role = new Role();
        role.setId(1);
        role.setName("ROLE_EMPLOYEE");

        UserDto user1 = new UserDto();
        user1.setId(1);
        user1.setFullname("Dinh An");
        user1.setRole(role);
        userList.add(user1);

        UserDto user2 = new UserDto();
        user2.setId(2);
        user2.setFullname("Dinh An");
        user2.setRole(role);
        userList.add(user2);

        UserDto user3 = new UserDto();
        user3.setId(3);
        user3.setFullname("Dinh An");
        user3.setRole(role);
        userList.add(user3);
        given(userServiceImpl.findAllEmployee()).willReturn(userList);

        mockMvc.perform(get("/admin/viewemployee"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(3)));
    }
}
