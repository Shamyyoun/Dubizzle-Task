package com.example.dubizzletask.features.products.presentation.productDetails;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.dubizzletask.core.BaseViewModel;
import com.example.dubizzletask.features.products.domain.models.Product;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ProductDetailsViewModel extends BaseViewModel {
    private Product product;
    private final MutableLiveData<ProductDetailsViewState> viewState = new MutableLiveData<>();

    @Inject
    public ProductDetailsViewModel() {
    }

    public void setProduct(Product product) {
        this.product = product;

        viewState.setValue(
                new ProductDetailsViewState.ProductState(product)
        );
    }

    public LiveData<ProductDetailsViewState> getViewState() {
        return viewState;
    }
}
