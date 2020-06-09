package br.com.zup.api.v1.mapper;

import br.com.zup.api.v1.dto.ProductDTO;
import br.com.zup.entity.Product;

public abstract class ProductDTOMapper {

    public static Product toProduct(ProductDTO productDTO) {
        return Product.builder()
                .identifier(productDTO.getIdentifier())
                .name(productDTO.getName())
                .description(productDTO.getDescription())
                .manufacturer(productDTO.getManufacturer())
                .price(productDTO.getPrice())
                .sku(productDTO.getSku())
                .depth(productDTO.getDepth())
                .weight(productDTO.getWeight())
                .width(productDTO.getWidth())
                .height(productDTO.getHeight())
                .build();
    }

    public static ProductDTO fromProduct(Product product) {
        return ProductDTO.builder()
            .identifier(product.getIdentifier())
            .name(product.getName())
            .description(product.getDescription())
            .manufacturer(product.getManufacturer())
            .price(product.getPrice())
            .sku(product.getSku())
            .depth(product.getDepth())
            .weight(product.getWeight())
            .width(product.getWidth())
            .height(product.getHeight())
            .build();
    }
}
