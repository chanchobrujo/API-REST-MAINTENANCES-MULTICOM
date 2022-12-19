package com.proyectoIntegrador.MultiCom.service;

import com.proyectoIntegrador.MultiCom.entity.User;
import com.proyectoIntegrador.MultiCom.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import com.proyectoIntegrador.MultiCom.entity.Role;
import com.proyectoIntegrador.MultiCom.repository.RoleRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;

import static com.proyectoIntegrador.MultiCom.constants.enums.RoleName.ROLE_MOD;
import static com.proyectoIntegrador.MultiCom.constants.enums.RoleName.ROLE_ADMIN;
import static com.proyectoIntegrador.MultiCom.constants.enums.RoleName.ROLE_CUSTOMER;

@Component
@RequiredArgsConstructor
public
class InitializeDataConfig implements CommandLineRunner {

    private final
    RoleRepository roleRepository;

    private final
    UserRepository userRepository;

    private final
    PasswordEncoder encoder;

    @Override
    public
    void run (String... args) {
        /**

         Role moderator = new Role(ROLE_MOD.name(), ROLE_MOD.getValue());
         Role administrator = new Role(ROLE_ADMIN.name(), ROLE_ADMIN.getValue());
         Role customer = new Role(ROLE_CUSTOMER.name(), ROLE_CUSTOMER.getValue());
         if (this.roleRepository.findAll().isEmpty()) {
         moderator = this.roleRepository.save(moderator);
         customer = this.roleRepository.save(customer);
         administrator = this.roleRepository.save(administrator);
         }
         if (this.userRepository.findAll().isEmpty()) {
         User admin = new User("Kevin Anderson", "Palma Lluén", "+51947275237",
         "umb.kevsidorov@gmail.com", administrator, this.encoder.encode("123456"));
         this.userRepository.save(admin);
         }
         */
    }
}
