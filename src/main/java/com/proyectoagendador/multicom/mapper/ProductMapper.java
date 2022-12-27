package com.proyectoagendador.multicom.mapper;

import com.proyectoagendador.multicom.entity.Product;
import com.proyectoagendador.multicom.model.response.ProductResponse;

import static com.proyectoagendador.multicom.utils.GeneralUtil.mapper;

public
class ProductMapper {

    public static
    ProductResponse mapperResponse(Product product) {
        ProductResponse response = mapper(ProductResponse.class, product);
        response.setCategoryName(product.getCategory().getName());
        response.setStateName(product.getState() ? "Activo" : "Inactivo");
        return response;
    }

}
