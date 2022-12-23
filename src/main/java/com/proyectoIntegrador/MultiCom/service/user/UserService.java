package com.proyectoIntegrador.MultiCom.service.user;

import com.proyectoIntegrador.MultiCom.entity.User;
import com.proyectoIntegrador.MultiCom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    UserRepository usuarioRepository;

    public List<User> list(){
        return usuarioRepository.findAll();
    } 

    public Optional<User> getById(int id){
        return usuarioRepository.findById(id);
    }

    public Optional<User> getByEmail(String email){
        return usuarioRepository.findByEmail(email);
    } 
    
    public int getUserSize() {
    	return usuarioRepository.findAll().size();
    }

    public boolean existsByEmail(String email){
        return usuarioRepository.existsByEmail(email);
    }

    public boolean existsByNumero(String numero){
        return usuarioRepository.existsByNumberPhone(numero);
    }

    public boolean existsById(int id){
        return usuarioRepository.existsById(id);
    }

    public void save(User user){
        usuarioRepository.save(user);
    }
}

