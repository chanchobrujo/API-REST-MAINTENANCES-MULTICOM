package com.proyectoagendador.multicom.service.maintenances;

import java.util.List;
import java.util.Objects;
import java.math.BigDecimal;

import com.proyectoagendador.multicom.mapper.ProductMapper;
import com.proyectoagendador.multicom.model.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.proyectoagendador.multicom.entity.Product;
import com.proyectoagendador.multicom.entity.Category;
import com.proyectoagendador.multicom.exception.BusinessException;
import com.proyectoagendador.multicom.model.request.ProductRequest;
import com.proyectoagendador.multicom.repository.ProductRepository;
import com.proyectoagendador.multicom.model.response.MessageResponse;
import com.proyectoagendador.multicom.common.constants.GeneralConstants;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public
class ProductService {

    private final CategoryService service;
    private final ProductRepository repository;

    public MessageResponse register(ProductRequest request) {
        boolean verify = this.repository.existsByName(request.getName());
        if (verify) {
            throw new BusinessException(GeneralConstants.DATA_REPEATED);
        }
        Category category = this.service.findById(request.getCategoryId());
        return this.save(new Product(request.getName(), request.getDescription(), request.getCost(), category));
    }

    public MessageResponse update(String id, ProductRequest request) {
        Product product = this.findByIdOriginal(id);
        Product verify = this.repository.findByName(request.getName()).orElse(null);
        if (Objects.nonNull(verify) && !verify.getId().equals(product.getId())) {
            throw new BusinessException(GeneralConstants.DATA_REPEATED);
        }
        if (!product.getCategory().getId().equals(request.getCategoryId())) {
            product.setCategory(this.service.findById(request.getCategoryId()));
        }
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(BigDecimal.valueOf(request.getCost()));
        return this.save(product);
    }

    public List<ProductResponse> retrieveAll(Pageable page) {
        return this.repository.retrieveAllProducts(page).stream().map(ProductMapper::mapperResponse).collect(toList());
    }

    public ProductResponse findById(String id) {
        return ProductMapper.mapperResponse(this.findByIdOriginal(id));
    }

    private Product findByIdOriginal(String id) {
        return this.repository.findById(id).orElseThrow(() -> new BusinessException(GeneralConstants.DATA_NOT_FOUND));
    }

    private MessageResponse save(Product product) {
        this.repository.save(product);
        return new MessageResponse(GeneralConstants.DATA_SUCCESSFUL);
    }
}
