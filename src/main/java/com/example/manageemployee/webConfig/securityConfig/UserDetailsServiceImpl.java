package com.example.manageemployee.webConfig.securityConfig;

import com.example.manageemployee.model.entity.user.User;
import com.example.manageemployee.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements IUserDetailsServiceImpl {
    @Autowired
    UserRepository userRepository;
    @Override
    public List<User> getUser(String username) {
        return this.userRepository.findAllByUsername(username);
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<User> users = this.getUser(username);
        System.out.println("loadUserByUsername");
        if(users.isEmpty())//Check username
            throw  new UsernameNotFoundException("User does not exist");
        User user = users.get(0);
        Set<GrantedAuthority> auth = new HashSet<>();
        auth.add(new SimpleGrantedAuthority(user.getRole().getName()));//addRole
        //return userdetails (username, password, role)
        System.out.println(auth);
        return new UserPrinciple(user.getUsername(),user.getPassword(),auth);
    }
}
