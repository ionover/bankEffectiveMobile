package org.example.bank2.repository;

import org.example.bank2.entity.User;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<User, Long> {

}
