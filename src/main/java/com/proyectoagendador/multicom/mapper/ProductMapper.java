package com.proyectoagendador.multicom.mapper;

import com.proyectoagendador.multicom.entity.Category;
import com.proyectoagendador.multicom.entity.Product;
import com.proyectoagendador.multicom.model.response.ProductResponse;

import java.util.Optional;

import static com.proyectoagendador.multicom.utils.GeneralUtil.mapper;

public
class ProductMapper {

    public ProductMapper() {}

    public static
    ProductResponse mapperResponse(Product product) {
        ProductResponse response = mapper(ProductResponse.class, product);
        response.setCategoryName(Optional.ofNullable(product.getCategory()).map(Category::getName).orElse(""));
        response.setStateName(product.getState() ? "Activo" : "Inactivo");
        return response;
    }

}
