package com.proyectoagendador.multicom.controller.maintenances;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import com.proyectoagendador.multicom.model.request.CategoryRequest;
import com.proyectoagendador.multicom.model.response.MessageResponse;
import com.proyectoagendador.multicom.model.response.CategoryResponse;
import com.proyectoagendador.multicom.service.maintenances.CategoryService;

import javax.validation.Valid;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public
class CategoryController {

    private final CategoryService service;

    @PostMapping("/register")
    public
    ResponseEntity<MessageResponse> register(@Valid @RequestBody CategoryRequest request) {
        return ok(this.service.register(request));
    }

    @PutMapping("/update")
    public
    ResponseEntity<MessageResponse> update(@RequestParam("id") String id, @Valid @RequestBody CategoryRequest request) {
        return ok(this.service.update(id, request));
    }

    @GetMapping("/findAll")
    public
    ResponseEntity<List<CategoryResponse>> retrieveAll(Pageable request) {
        return ok(this.service.retrieveAll(request));
    }

    @GetMapping("/findBy/{paramType}")
    public
    ResponseEntity<CategoryResponse> findByParam(@PathVariable("paramType") String paramType, @RequestParam("value") String value) {
        return ok(this.service.retrieveOne(paramType, value));
    }
}
