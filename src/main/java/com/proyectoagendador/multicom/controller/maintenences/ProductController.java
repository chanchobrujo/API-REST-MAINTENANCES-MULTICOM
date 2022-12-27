package com.proyectoagendador.multicom.controller.maintenences;

import java.util.List;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.proyectoagendador.multicom.model.request.ProductRequest;
import com.proyectoagendador.multicom.model.response.MessageResponse;
import com.proyectoagendador.multicom.model.response.ProductResponse;
import com.proyectoagendador.multicom.service.maintenences.ProductService;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public
class ProductController {

    private final ProductService service;

    @PostMapping("/register")
    public
    ResponseEntity<MessageResponse> register(@Valid @RequestBody ProductRequest request) {
        return ok(this.service.register(request));
    }

    @PutMapping("/update")
    public
    ResponseEntity<MessageResponse> update(@RequestParam("id") String id, @Valid @RequestBody ProductRequest request) {
        return ok(this.service.update(id, request));
    }

    @GetMapping("/findAll")
    public
    ResponseEntity<List<ProductResponse>> retrieveAll(Pageable request) {
        return ok(this.service.retrieveAll(request));
    }

    @GetMapping("/findById")
    public
    ResponseEntity<ProductResponse> findByParam(@RequestParam("value") String value) {
        return ok(this.service.findById(value));
    }
}
