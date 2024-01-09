package com.admin.admin_service.controller;

import com.admin.admin_service.model.auth.User;
import com.admin.admin_service.model.auth.UserRequest;
import com.admin.admin_service.repository.UserRepository;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserController {
    private UserRepository userRepository;
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/user")
    public ResponseEntity<Object>register(@RequestBody UserRequest request){
        BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        ModelMapper modelMapper=new ModelMapper();
        User user=modelMapper.map(request,User.class);
        String endcodedPassword=passwordEncoder.encode(request.getPassword());
        user.setPassword(endcodedPassword);
        user.setEnabled(true);
        user.setRole("ROLE ADMIN");
        userRepository.save(user);
        return ResponseEntity.ok("User Inserted Successfully!");
    }
    @PutMapping("/user/{id}")
    public ResponseEntity<Object>Update(@PathVariable Long id,@RequestBody UserRequest request){
        Optional<User> result=userRepository.findById(id);
        BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        ModelMapper modelMapper=new ModelMapper();
        if(result.isEmpty()){
            return ResponseEntity.ok("Cannot find User");
        }else{
            User user=modelMapper.map(result.get(),User.class);
            String endcodedPassword=passwordEncoder.encode(request.getPassword());
            user.setPassword(endcodedPassword);
            user.setUsername(request.getUsername());
            user.setEnabled(true);
            user.setRole("ROLE ADMIN");
            userRepository.save(user);
            return ResponseEntity.ok("User Updated Successfully!");
        }

    }




}
