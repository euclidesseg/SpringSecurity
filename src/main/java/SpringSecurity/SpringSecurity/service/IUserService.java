package SpringSecurity.SpringSecurity.service;

import SpringSecurity.SpringSecurity.dto.SaveUserDTO;
import SpringSecurity.SpringSecurity.persistance.entity.User;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.Optional;


public interface IUserService {
    User createOneCustomer(SaveUserDTO saveUserDTO);

    Optional<User> findByUsername(String username);
}
