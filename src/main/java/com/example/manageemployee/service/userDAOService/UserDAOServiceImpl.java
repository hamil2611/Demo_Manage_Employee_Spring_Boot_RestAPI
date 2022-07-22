package com.example.manageemployee.service.userDAOService;

import com.example.manageemployee.dto.UserDto;
import com.example.manageemployee.entity.Role;
import com.example.manageemployee.entity.User;
import com.example.manageemployee.repository.RoleRepository;
import com.example.manageemployee.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class UserDAOServiceImpl implements  UserDAOService{
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public boolean addUser(UserDto userDto) {
        User u = this.modelMapper.map(userDto,User.class);
        List<User> userCheckUsername = userRepository.findAllByUsername(u.getUsername());
        if(!userCheckUsername.isEmpty()){
            return false;
        }
        Date date = new Date();
        int codecheckin=0;
        List<Integer> listCodeCheckin = userRepository.findAllCodecheckin();
        while(true){
            int tmp=0;
            codecheckin = ThreadLocalRandom.current().nextInt(1000,10000);
            for(Integer i:listCodeCheckin){
                if(i==codecheckin){
                    tmp++;
                    break;
                }
            }
            if (tmp==0){
                break;
            }
        }
        u.setCodecheckin(codecheckin);
        u.setDatecreated(date);
        u.setPassword(bCryptPasswordEncoder.encode(u.getPassword()));
        userRepository.save(u);
        return true;
    }

    @Override
    public boolean editUser(int id) {
        return false;
    }

    @Override
    public List<UserDto> findAll() {
        Optional<Role> role = roleRepository.findById(2);
        List<User> listEmployee= userRepository.findAllByRole(role);
        List<UserDto> listUserDto = listEmployee.stream().map(user->modelMapper.map(user,UserDto.class)).collect(Collectors.toList());
        return listUserDto;
    }

    @Override
    public List<UserDto> deleteUser(int id) {
        Optional<User> usercheck = userRepository.findById(id);
        if(usercheck.isEmpty()){
            Optional<Role> role = roleRepository.findById(2);
            List<User> listEmployee= userRepository.findAllByRole(role);
            List<UserDto> listUserDto = listEmployee.stream().map(user->modelMapper.map(user,UserDto.class)).collect(Collectors.toList());
            return listUserDto;
        }
        userRepository.deleteById(id);
        Optional<Role> role = roleRepository.findById(2);
        List<User> listEmployee= userRepository.findAllByRole(role);
        List<UserDto> listUserDto = listEmployee.stream().map(user->modelMapper.map(user,UserDto.class)).collect(Collectors.toList());
        return listUserDto;
    }

    @Override
    public UserDto getCheckinUser(int id) {
        Optional<User> user = userRepository.findById(id);
        UserDto userDto = this.modelMapper.map(user,UserDto.class);
        return userDto;
    }
}
