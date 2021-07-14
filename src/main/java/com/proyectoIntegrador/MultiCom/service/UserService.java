package com.proyectoIntegrador.MultiCom.service; 

import com.proyectoIntegrador.MultiCom.model.Usuario;
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

    public List<Usuario> list(){
        return usuarioRepository.findAll();
    } 

    public Optional<Usuario> getById(int id){
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> getByEmail(String email){
        return usuarioRepository.findByEmail(email);
    } 
    
    public int getUserSize() {
    	return usuarioRepository.findAll().size();
    }

    public boolean existsByEmail(String email){
        return usuarioRepository.existsByEmail(email);
    }

    public boolean existsByContrasena(String password){
        return usuarioRepository.existsByContrasena(password);
    }

    public boolean existsByNumero(String numero){
        return usuarioRepository.existsByNumero(numero);
    }

    public boolean existsById(int id){
        return usuarioRepository.existsById(id);
    }

    public void save(Usuario usuario){
        usuarioRepository.save(usuario);
    }
}

