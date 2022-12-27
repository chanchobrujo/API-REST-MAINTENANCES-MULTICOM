package com.proyectoagendador.multicom.mapper;

import com.proyectoagendador.multicom.entity.Category;
import com.proyectoagendador.multicom.model.response.CategoryResponse;
import com.proyectoagendador.multicom.utils.DateUtil;

import static com.proyectoagendador.multicom.utils.GeneralUtil.mapper;

public
class CategoryMapper {

    public static CategoryResponse mapperResponse(Category category) {
        CategoryResponse response = mapper(CategoryResponse.class, category);
        response.setDate(DateUtil.formatterDate(category.getCreatedDate()));
        return response;
    }
}
