package com.proyectoagendador.multicom.service.maintenences;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import com.proyectoagendador.multicom.mapper.UserMapper;
import com.proyectoagendador.multicom.repository.UserRepository;
import com.proyectoagendador.multicom.model.response.UserResponse;
import com.proyectoagendador.multicom.model.request.SingUpRequest;
import com.proyectoagendador.multicom.model.response.MessageResponse;
import com.proyectoagendador.multicom.service.authentication.AuthService;

import static java.util.stream.Collectors.toList;
import static org.springframework.data.domain.Sort.by;
import static org.springframework.data.domain.PageRequest.of;
import static com.proyectoagendador.multicom.common.enums.RoleNameEnum.ROLE_CUSTOMER;

@Service
@RequiredArgsConstructor
public
class CustomerService {
    private final AuthService service;
    private final UserRepository repository;

    public
    MessageResponse save(SingUpRequest request) {
        request.setPassword(null);
        return this.service.registerForCustomer(request);
    }

    public
    List<UserResponse> findAll(Pageable page) {
        Sort sort = by("createdDate").descending();
        page = of(page.getPageNumber(), page.getPageSize(), sort);
        return this.repository.findByRole_Name(ROLE_CUSTOMER.name(), page).getContent().stream().map(UserMapper::buildResponse).collect(toList());
    }
}
