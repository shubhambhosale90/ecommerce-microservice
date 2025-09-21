package com.smasher.ecommerce.inventory_service.serrvice;

import com.smasher.ecommerce.inventory_service.dto.OrderRequestDto;
import com.smasher.ecommerce.inventory_service.dto.OrderRequestItemDto;
import com.smasher.ecommerce.inventory_service.dto.ProductDto;
import com.smasher.ecommerce.inventory_service.entity.Product;
import com.smasher.ecommerce.inventory_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public List<ProductDto> getAllInventory() {
        log.info("Fetching all inventory Items");
        List<Product> inventories = productRepository.findAll();
        return inventories.stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .toList();
    }

    public ProductDto getProductById(Long id) {
        log.info("Fetching Product with ID : {}", id);
        Optional<Product> inventory = productRepository.findById(id);
        return inventory.map(item -> modelMapper.map(item, ProductDto.class))
                .orElseThrow(() -> new RuntimeException("Inventory not found !"));
    }

    @Transactional
    public Double reduceStock(OrderRequestDto orderRequestDto) {
        log.info("Reducing the stocks");
        double totalPrice = 0.0;
        for (OrderRequestItemDto orderRequestItemDto : orderRequestDto.getItems()) {
            Long productId = orderRequestItemDto.getProductId();
            Integer quantity = orderRequestItemDto.getQuantity();

            Product product = productRepository.findById(productId).orElseThrow(() ->
                    new RuntimeException("Product not found with ID : "+productId));

            if (product.getStock() < quantity) {
                throw new RuntimeException("Product cannot be fulfilled for given quantity");
            }

            product.setStock(product.getStock()-quantity);
            productRepository.save(product);
            totalPrice += quantity*product.getPrice();
        }
        return totalPrice;
    }
}
