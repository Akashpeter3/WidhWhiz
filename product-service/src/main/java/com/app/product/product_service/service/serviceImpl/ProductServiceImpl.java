package com.app.product.product_service.service.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.app.product.product_service.dto.ProductRequestDTO;
import com.app.product.product_service.dto.ProductResponseDTO;
import com.app.product.product_service.model.Inventory;
import com.app.product.product_service.model.Product;
import com.app.product.product_service.model.ProductCategory;
import com.app.product.product_service.repository.CategoryRepository;
import com.app.product.product_service.repository.InventoryRepository;
import com.app.product.product_service.repository.ProductRepository;
import com.app.product.product_service.service.ProductService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {
        ProductCategory productCategory;

        if (productRequestDTO.getCategoryId() != null) {
            productCategory = categoryRepository.findById(productRequestDTO.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        } else if (productRequestDTO.getCategoryName() != null) {
            productCategory = categoryRepository.findByName(productRequestDTO.getCategoryName());
            if (productCategory == null) {
                productCategory = ProductCategory.builder()
                    .name(productRequestDTO.getCategoryName())
                    .description(productRequestDTO.getCategoryDescription())
                    .build();
                categoryRepository.save(productCategory);
            }
        } else {
            throw new IllegalArgumentException("Either category id or name is required");
        }

        Product product = Product.builder()
            .category(productCategory)
            .description(productRequestDTO.getDescription())
            .name(productRequestDTO.getName())
            .price(productRequestDTO.getPrice())
            .build();

        productRepository.save(product);

        Inventory inventory = Inventory.builder()
            .product(product)
            .stock(productRequestDTO.getStock())
            .build();

        inventoryRepository.save(inventory);

        return mapToDTO(product, inventory);
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
            .<ProductResponseDTO>map(product -> mapToDTO(product, inventoryRepository.findByProduct_ProductId(product.getProductId())))
            .collect(Collectors.toList());
    }

    private ProductResponseDTO mapToDTO(Product product, Inventory inventory) {
        return ProductResponseDTO.builder()
            .productId(product.getProductId())
            .categoryName(product.getCategory().getName())
            .description(product.getDescription())
            .name(product.getName())
            .price(product.getPrice())
            .stock(inventory != null ? inventory.getStock() : 0)
            .build();
    }

    @Override
    public ProductResponseDTO getProductById(Long productId) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        Inventory inventory = inventoryRepository.findByProduct_ProductId(productId);
        return mapToDTO(product, inventory);
    }

    @Override
    public ProductResponseDTO updateProduct(Long productId, ProductRequestDTO productRequestDTO) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        product.setDescription(productRequestDTO.getDescription());
        product.setName(productRequestDTO.getName());
        product.setPrice(productRequestDTO.getPrice());

        if (productRequestDTO.getCategoryId() != null) {
            ProductCategory productCategory = categoryRepository.findById(productRequestDTO.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
            product.setCategory(productCategory);
        }

        Product updatedProduct = productRepository.save(product);

        Inventory inventory = inventoryRepository.findByProduct_ProductId(productId);
        if (inventory != null) {
            inventory.setStock(productRequestDTO.getStock());
            inventoryRepository.save(inventory);
        }
        return mapToDTO(updatedProduct, inventory);
    }

@Override
@Transactional
public void deleteProduct(Long productId) {
    Product product = productRepository.findById(productId)
        .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        System.out.println("product name ---->"+product.getName());
    // Delete inventory explicitly to avoid cascading issues
   

    // Now delete product
       productRepository.delete(product);
}
}