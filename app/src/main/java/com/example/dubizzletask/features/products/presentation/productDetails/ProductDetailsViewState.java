package com.example.dubizzletask.features.products.presentation.productDetails;

import com.example.dubizzletask.features.products.domain.models.Product;

public class ProductDetailsViewState {
    private ProductDetailsViewState() {
    }

    static class ProductState extends ProductDetailsViewState {
        private final Product product;

        public ProductState(Product product) {
            this.product = product;
        }

        public Product getProduct() {
            return product;
        }
    }
}
