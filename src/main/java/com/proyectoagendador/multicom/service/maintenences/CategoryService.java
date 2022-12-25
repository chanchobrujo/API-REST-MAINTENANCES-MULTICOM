package com.proyectoagendador.multicom.service.maintenences;

import java.util.List;
import java.util.Objects;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import com.proyectoagendador.multicom.entity.Category;
import com.proyectoagendador.multicom.common.constants.GeneralConstants;
import com.proyectoagendador.multicom.mapper.CategoryMapper;
import com.proyectoagendador.multicom.exception.BusinessException;
import com.proyectoagendador.multicom.common.enums.ParamTypeEnum;
import com.proyectoagendador.multicom.model.request.CategoryRequest;
import com.proyectoagendador.multicom.repository.CategoryRepository;
import com.proyectoagendador.multicom.model.response.MessageResponse;
import com.proyectoagendador.multicom.model.response.CategoryResponse;

import static java.lang.Integer.parseInt;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public
class CategoryService {

    private final CategoryRepository repository;

    public
    MessageResponse register(CategoryRequest request) {
        if (this.repository.existsByName(request.getName())) {
            throw new BusinessException(GeneralConstants.DATA_REPEATED);
        }
        return this.save(new Category(request.getName(), request.getDescription()));
    }

    public
    MessageResponse update(String id, CategoryRequest request) {
        Category category = this.repository.findById(parseInt(id)).orElseThrow(() -> new BusinessException(GeneralConstants.DATA_NOT_FOUND));
        Category verify = this.repository.findByName(request.getName()).orElse(null);
        if (Objects.nonNull(verify) && !verify.getId().equals(category.getId())) {
            throw new BusinessException(GeneralConstants.DATA_REPEATED);
        }
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        return this.save(category);
    }

    public List<CategoryResponse> retrieveAll(Pageable page) {
        return this.repository.retrieveAllCategories(page).stream().map(CategoryMapper::mapperResponse).collect(toList());
    }

    public CategoryResponse retrieveOne(String paramType, String value) {
        paramType = ParamTypeEnum.findByValue(paramType).getValue();
        boolean verify = paramType.equals(ParamTypeEnum.ID.getValue());
        return verify ? this.findById(value) : this.findByName(value);
    }

    private MessageResponse save(Category category) {
        this.repository.save(category);
        return new MessageResponse(GeneralConstants.DATA_SUCCESSFUL);
    }

    private CategoryResponse findByName(String name) {
        return this.repository.findByName(name).map(CategoryMapper::mapperResponse).orElseThrow(() -> new BusinessException(GeneralConstants.DATA_NOT_FOUND));
    }

    private CategoryResponse findById(String id) {
        return this.repository.findById(parseInt(id)).map(CategoryMapper::mapperResponse).orElseThrow(() -> new BusinessException(GeneralConstants.DATA_NOT_FOUND));
    }
}
