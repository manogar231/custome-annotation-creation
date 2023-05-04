package com.custome.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.custome.entity.User;
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findUserBymail(String mail);

}
