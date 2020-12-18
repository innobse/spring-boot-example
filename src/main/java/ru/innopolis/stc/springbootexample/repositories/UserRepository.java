package ru.innopolis.stc.springbootexample.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.innopolis.stc.springbootexample.repositories.entities.User;

public interface UserRepository extends JpaRepository<User, String> {

}
