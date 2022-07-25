package com.example.manageemployee.service.userDAOService;

import com.example.manageemployee.model.dto.CheckinDto;
import com.example.manageemployee.model.dto.UserDto;
import com.example.manageemployee.model.entity.Checkin;
import com.example.manageemployee.model.entity.Role;
import com.example.manageemployee.model.entity.User;
import com.example.manageemployee.repository.CheckinRepository;
import com.example.manageemployee.repository.RoleRepository;
import com.example.manageemployee.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
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
    CheckinRepository checkinRepository;
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
        int codeCheckin=0;
        List<Integer> listCodeCheckin = userRepository.findAllCodecheckin();
        while(true){
            int tmp=0;
            codeCheckin = ThreadLocalRandom.current().nextInt(1000,10000);
            for(Integer i:listCodeCheckin){
                if(i==codeCheckin){
                    tmp++;
                    break;
                }
            }
            if (tmp==0){
                break;
            }
        }
        u.setCodecheckin(codeCheckin);
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
    @Override
    public UserDto Checkin(CheckinDto checkinDto){
        Checkin checkin = this.modelMapper.map(checkinDto,Checkin.class);
        Date date = new Date();
        if(userRepository.findAllByCodecheckin(checkinDto.getCodecheckin()).isEmpty()!=true){
            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
            String today = format.format(date);
            List<Checkin> listcheckin = checkinRepository.findAllByCodecheckin(checkin.getCodecheckin());
            //Check xem employee da checkin trong ngay do chua
            if(listcheckin.isEmpty()!=true){
                Checkin checkinToday = listcheckin.get(0);
                if(format.format(checkinToday.getDatecreated()).equals(today)){
                    checkinToday.setTimecheckout(date);
                    checkinRepository.save(checkinToday);
                }else{
                    checkin.setTimecheckin(date);
                    checkin.setDatecreated(date);
                    checkinRepository.save(checkin);
                }
            }else {
                checkin.setDatecreated(date);
                checkin.setTimecheckin(date);
                checkinRepository.save(checkin);
            }
        }else{
            return null;
        }
        UserDto userDto = this.modelMapper.map(userRepository.findAllByCodecheckin(checkinDto.getCodecheckin()).get(0),UserDto.class);
        System.out.println(userDto.getFullname());
        return userDto;
    }
    public List<User> getUserByUsername(String username){
        return userRepository.findAllByUsername(username);
    }
}
