package SpringSecurity.SpringSecurity.service.impl;

import SpringSecurity.SpringSecurity.dto.SaveUserDTO;
import SpringSecurity.SpringSecurity.exception.InvalidPasswordException;
import SpringSecurity.SpringSecurity.exception.ObjectNotFoundException;
import SpringSecurity.SpringSecurity.persistance.entity.security.Role;
import SpringSecurity.SpringSecurity.persistance.entity.security.User;
import SpringSecurity.SpringSecurity.persistance.repository.security.IUserRepository;
import SpringSecurity.SpringSecurity.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
// este servicio de usuario realizará operacion para guardar a un usuario que no le corresponden a un
// authentication service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserRepository iUserRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RoleService roleService;
    @Override
    public User createOneCustomer(SaveUserDTO newUserDTO) {

        // validamos contraseña
        validatePassword(newUserDTO);
        User user = new User();
        user.setName(newUserDTO.getName());
        user.setPassword(passwordEncoder.encode(newUserDTO.getPassword()));
        user.setUsername(newUserDTO.getUsername());

        Role deFaultRole = roleService.findDefaultRole().orElseThrow(() -> new ObjectNotFoundException("Role not found. Default Role"));
        user.setRole(deFaultRole);


        return this.iUserRepository.save(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return this.iUserRepository.findByUsername(username);
    }

    private void validatePassword(SaveUserDTO newUserDTO) {
        if (newUserDTO.getPassword().toString() == null || newUserDTO.getRepeatedPassword().toString() == null) {
            throw new InvalidPasswordException("Password don't match");
        }
        if (!newUserDTO.getPassword().toString().equals(newUserDTO.getRepeatedPassword().toString())) {
            throw new InvalidPasswordException("Password don't match");
        }
    }

}
