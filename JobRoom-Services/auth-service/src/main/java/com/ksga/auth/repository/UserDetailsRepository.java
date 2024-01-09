package com.ksga.auth.repository;

import com.ksga.auth.model.user.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails,Integer> {

    UserDetails findById(int id);

    UserDetails findByEmailEqualsAndPasswordEquals(String email,String password);

    UserDetails findUserDetailsByEmail(String name);

    UserDetails findByUserIdAndRoleEquals(int id,String role);
}
