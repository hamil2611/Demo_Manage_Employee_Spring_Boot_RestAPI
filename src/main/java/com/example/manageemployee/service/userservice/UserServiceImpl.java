package com.example.manageemployee.service.userservice;

import com.example.manageemployee.model.dto.CheckinDto;
import com.example.manageemployee.model.dto.OnLeaveDto;
import com.example.manageemployee.model.dto.UserDto;
import com.example.manageemployee.model.entity.*;
import com.example.manageemployee.model.entity.user.SearchUser;
import com.example.manageemployee.model.entity.user.User;
import com.example.manageemployee.model.entity.user.UserSpecification;
import com.example.manageemployee.repository.*;
import com.example.manageemployee.service.mailservice.MailService;
import com.example.manageemployee.service.roleservice.RoleService;
import com.example.manageemployee.service.roleservice.RoleServiceImpl;
import com.example.manageemployee.webConfig.securityConfig.UserPrinciple;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    CheckinRepository checkinRepository;
    @Autowired
    OnLeaveRepository onLeaveRepository;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    RoleService roleDAOServiceImpl;
    @Autowired
    MailService mailService;
    @Override
    public boolean addUser(UserDto userDto) {
        User userCheckUsername = userRepository.findByUsername(userDto.getUsername());
        if(userCheckUsername!=null)
            return false;

        User userCheckEmail = userRepository.findByEmail(userDto.getEmail());
        if(userCheckEmail!=null)
            return false;

        User u = this.modelMapper.map(userDto,User.class);
        Date date = new Date();
        int codeCheckin=0;
        List<Integer> codeCheckinList = userRepository.findAllCodecheckin();
        while(true){
            int tmp=0;
            codeCheckin = ThreadLocalRandom.current().nextInt(1000,10000);
            for(Integer i:codeCheckinList){
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
    public boolean updateUser(UserDto userDto) {
        if(!userRepository.findById(userDto.getId()).isEmpty()){
            User user = this.modelMapper.map(userDto, User.class);
            userRepository.save(user);
            return true;
        }
        else{
            return  false;
        }
    }
    @Override
    public List<UserDto> findAllEmployee() {
        List<User> listEmployee= userRepository.findAllEmployee();
        List<UserDto> listUserDto = listEmployee.stream().map(user->modelMapper.map(user,UserDto.class)).collect(Collectors.toList());
        return listUserDto;
    }
    @Override
    public UserDto getUser(int id){
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty())
            return null;
        UserDto userDto = this.modelMapper.map(user,UserDto.class);
        return userDto;
    }
    @Override
    public boolean deleteUser(int id) {
        Optional<User> usercheck = userRepository.findById(id);
        if(usercheck.isEmpty()){
            return false;
        }
        userRepository.deleteById(id);
        return true;
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
        Calendar calendar = Calendar.getInstance();
        int dayofweek = calendar.get(Calendar.DAY_OF_WEEK);
        int weekofyear = calendar.get(Calendar.WEEK_OF_YEAR);
        int monthofyear = calendar.get(Calendar.MONTH)+1;
        int year = calendar.get(Calendar.YEAR);
        if(!userRepository.findAllByCodecheckin(checkinDto.getCodecheckin()).isEmpty()){
            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
            String today = format.format(date);
            List<Checkin> listcheckin = checkinRepository.findAllByCodecheckin(checkin.getCodecheckin());
            //Check xem employee da checkin trong ngay do chua
            if(!listcheckin.isEmpty()){
                Checkin checkinToday = listcheckin.get(0);
                if(format.format(checkinToday.getDatecreated()).equals(today)){
                    checkinToday.setTimecheckout(date);
                    checkinRepository.save(checkinToday);
                }else{
                    checkin.setTimecheckin(date);
                    checkin.setDatecreated(date);
                    checkin.setDayofweek(dayofweek);
                    checkin.setWeekofyear(weekofyear);
                    checkin.setMonthofyear(monthofyear);
                    checkin.setYear(year);
                    checkinRepository.save(checkin);
                }
            }else {
                checkin.setDatecreated(date);
                checkin.setTimecheckin(date);
                checkin.setDayofweek(dayofweek);
                checkin.setWeekofyear(weekofyear);
                checkin.setMonthofyear(monthofyear);
                checkin.setYear(year);
                checkinRepository.save(checkin);
            }
        }else{
            return null;
        }
        UserDto userDto = this.modelMapper.map(userRepository.findAllByCodecheckin(checkinDto.getCodecheckin()).get(0),UserDto.class);
        System.out.println(userDto.getFullname());
        return userDto;
    }
    @Override
    public List<UserDto> findUserByName(String name) {
        List<User> userList = userRepository.findUserByFullname(name);
        List<UserDto> userDtoList = userList.stream().map(user->modelMapper.map(user,UserDto.class)).collect(Collectors.toList());
        return userDtoList;
    }
    @Override
    public boolean sendRequestOnLeave(OnLeaveDto onLeaveDto) {
        UserPrinciple userPrinciple = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userPrinciple.getUsername());
        OnLeave onLeave = this.modelMapper.map(onLeaveDto,OnLeave.class);
        System.out.println(onLeave.getDurationonleave());
        onLeave.setUser(user);
        onLeaveRepository.save(onLeave);
        return true;
    }
    @Override
    public int getCodecheckinByUsername() {
        UserPrinciple userPrinciple = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userPrinciple.getUsername());
        return user.getCodecheckin();
    }
    @Override
    public boolean joinPorject(int userid, int projectid) {
        System.out.println("test1");
        Optional<User> userOptional = userRepository.findById(userid);
        Optional<Project> projectOptional = projectRepository.findById(projectid);
        User user = this.modelMapper.map(userOptional,User.class);
        Project project = this.modelMapper.map(projectOptional,Project.class);
        Set<Project> projectSet = new HashSet<>();
        projectSet.add(project);
        user.setProjects(projectSet);
        userRepository.save(user);
        return false;
    }

    @Override
    public List<Role> getRoles() {
        List<Role> roleList =  roleRepository.findAll();
        return roleList;
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user==null)
            return null;
        UserDto userDto = this.modelMapper.map(user,UserDto.class);
        return userDto;
    }

    @Override
    public List<UserDto> SearchUser(SearchUser searchUser) {
        UserSpecification userSpecification = new UserSpecification(searchUser);
        return  userRepository.findAll(Specification.where(userSpecification)).stream().map(user -> modelMapper.map(user,UserDto.class)).collect(Collectors.toList());
    }
}
