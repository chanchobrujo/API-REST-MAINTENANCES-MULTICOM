package com.proyectoagendador.multicom.service.maintenences;

import com.proyectoagendador.multicom.entity.Role;
import com.proyectoagendador.multicom.exception.BusinessException;
import com.proyectoagendador.multicom.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.proyectoagendador.multicom.common.constants.GeneralConstants.GENERIC_CODE;

@Service
@RequiredArgsConstructor
public
class RoleService {
    private final RoleRepository repository;

    public
    Role findByName(String name) {
        return this.repository.findByName(name).orElseThrow(() -> new BusinessException(GENERIC_CODE));
    }
}
