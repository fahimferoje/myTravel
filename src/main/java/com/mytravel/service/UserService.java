/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mytravel.service;

/**
 *
 * @author Fahim Feroje Al Jami
 */
//import com.user.login.model.Role;
import com.mytravel.model.Location;
import com.mytravel.model.Role;
import com.mytravel.model.User;
import com.mytravel.repository.LocationRepository;
import com.mytravel.repository.RoleRepository;
import com.mytravel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service("userService")
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    private final LocationRepository locationRepository;
    
    @Autowired
    public UserService(UserRepository userRepository,
            RoleRepository roleRepository,
            LocationRepository locationRepository1,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.locationRepository = locationRepository1;
        this.roleRepository = roleRepository;
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        
        Role userRole = roleRepository.findByRole("ADMIN");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        
        userRepository.save(user);
    }
    
    public List<User> findDummyUsers(){
        System.out.println("Lookinng for dummy ysers");
        return userRepository.findDummyUsers();
    }
    
    public List<Location> findAllLocations() {
        return locationRepository.findAll();
    }
    

}
