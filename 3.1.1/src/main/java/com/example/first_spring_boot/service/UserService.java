package com.example.first_spring_boot.service;

import com.example.first_spring_boot.model.Role;
import com.example.first_spring_boot.model.User;
import com.example.first_spring_boot.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private final
    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findById(Long id) {
        return userRepository.findById(id).get();
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User save(User user) {
        List<Role> roles = user.getRoles();
        user.setRoles(new ArrayList<>());
        userRepository.save(user);
        for(Role role : roles){
            if(role.getName().contains("ADMIN")){
                user.addRole(userRepository.getRole(1L));
            } else {
                user.addRole(userRepository.getRole(2L));
            }
        }
        userRepository.save(user);
        return user;
    }

    public String delete(Long id) {
        userRepository.deleteById(id);
        return "deleted "+id;
    }

    public User update(User user) {
        User updatedUser = userRepository.getById(user.getId());
        updatedUser.setName(user.getName());
        updatedUser.setLastname(user.getLastname());
        updatedUser.setAge(user.getAge());
        updatedUser.setEmail(user.getEmail());
        List<Role> roles = user.getRoles();
        updatedUser.setRoles(new ArrayList<>());
        System.out.println(user);
        System.out.println("_________________________\n" + roles);
        boolean adminCheck = false, userCheck = false;
        for(Role role : updatedUser.getRoles()){
            if(role.getName().contains("ADMIN")){
                adminCheck = true;
            }
            if(role.getName().contains("USER")){
                userCheck = true;
            }
        }
        for (Role role : roles) {
            if (role.getName().contains("ADMIN")) {
                if (!adminCheck) {
                    updatedUser.addRole(userRepository.getRole(1L));
                }
            } else {
                if (!userCheck) {
                    updatedUser.addRole(userRepository.getRole(2L));
                }
            }
        }
        updatedUser.setPassword(user.getPassword());
        userRepository.save(updatedUser);
        return user;
    }

    /*public List<Role> getRoles(){
        return userRepository.getRolesList();
    }*/
}
