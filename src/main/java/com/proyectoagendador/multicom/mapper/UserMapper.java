package com.proyectoagendador.multicom.mapper;

import java.util.Map;
import java.util.HashMap;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.proyectoagendador.multicom.entity.User;
import com.proyectoagendador.multicom.model.dto.UserPrincipalDto;
import com.proyectoagendador.multicom.model.response.UserResponse;

import static java.util.Collections.singleton;

public
class UserMapper {

    private UserMapper() {}

    public static UserResponse buildResponse(User user) {
        Map<String, String> document = new HashMap<>();
        document.put("document-type", user.getDocumentType());
        document.put("document-number", user.getDocumentNumber());

        Map<String, String> contact = new HashMap<>();
        contact.put("email", user.getEmail());
        contact.put("number-phone", user.getNumberPhone());

        String fullName = user.getFirstName().concat(" ").concat(user.getLastName());
        UserResponse response = new UserResponse(user.getId(), user.getCreatedDate(), user.getState(), fullName);
        response.setContact(contact);
        response.setDocument(document);
        return response;
    }

    public static
    UserDetails buildDetailAuth(User user) {
        return new UserPrincipalDto(user.getEmail(), user.getPassword(), singleton(new SimpleGrantedAuthority(user.getRole().getName())));
    }
}
