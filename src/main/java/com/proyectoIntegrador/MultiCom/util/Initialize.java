package com.proyectoIntegrador.MultiCom.util;

import com.proyectoIntegrador.MultiCom.model.*;
import com.proyectoIntegrador.MultiCom.enums.*;
import com.proyectoIntegrador.MultiCom.service.*;

import java.util.*;

import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class Initialize implements CommandLineRunner {

	@Autowired
	RoleService rolService;

	@Autowired
	UserService userservice;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) throws Exception {
		if (rolService.getRolesSize() == 0) {
			Rol rolAdmin = new Rol(RoleName.ROLE_ADMIN);
			Rol rolUser = new Rol(RoleName.ROLE_MOD);
			Rol client = new Rol(RoleName.ROLE_CLIENTE);
			rolService.save(rolAdmin);
			rolService.save(rolUser);
			rolService.save(client);
		}

		if (userservice.getUserSize() == 0) {
			Usuario useradmin = new Usuario("Kevin Anderson", "Palma LLuén", "947275237", "umb.kevsidorov@gmail.com",
					passwordEncoder.encode("123456"), true);
			Set<Rol> roles = new HashSet<>();

			roles.add(rolService.getByRolNombre(RoleName.ROLE_ADMIN).get());
			useradmin.setRoles(roles);
			userservice.save(useradmin);
		}

		System.out.println("\n----               ----");
		System.out.println("-ENABLED ADMINISTRATOR-");
		System.out.println("----               ----\n");
	}
}