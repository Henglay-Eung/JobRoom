package com.ksga.com.chat.repository;

import com.ksga.com.chat.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<Person, Integer> {
}
