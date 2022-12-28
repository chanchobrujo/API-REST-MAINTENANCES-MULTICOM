package com.proyectoagendador.multicom.controller.maintenences;

import javax.validation.Valid;

import com.proyectoagendador.multicom.model.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import com.proyectoagendador.multicom.service.maintenences.CustomerService;

import com.proyectoagendador.multicom.model.request.SingUpRequest;
import com.proyectoagendador.multicom.model.response.MessageResponse;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
public
class CustomerController {

    private final CustomerService service;

    @PostMapping("/save")
    public
    ResponseEntity<MessageResponse> save(@Valid @RequestBody SingUpRequest request) {
        return ok(this.service.save(request));
    }

    @GetMapping("/findAll")
    public
    ResponseEntity<List<UserResponse>> findAll(Pageable pageable) {
        return ok(this.service.findAll(pageable));
    }
}
