package com.example.dubizzletask.features.products.presentation.productDetails;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.dubizzletask.R;
import com.example.dubizzletask.core.BaseFragment;
import com.example.dubizzletask.databinding.FragmentProductDetailsBinding;
import com.example.dubizzletask.features.products.domain.models.Product;

import dagger.hilt.android.AndroidEntryPoint;
import kotlin.collections.CollectionsKt;

@AndroidEntryPoint
public class ProductDetailsFragment extends BaseFragment<FragmentProductDetailsBinding, ProductDetailsViewModel> {

    @Override
    protected void onReady(@Nullable Bundle savedInstanceState) {
        observeViewModel();

        ProductDetailsFragmentArgs args = ProductDetailsFragmentArgs.fromBundle(getArguments());
        getViewModel().setProduct(args.getProduct());
    }

    private void observeViewModel() {
        getViewModel().getViewState().observe(getViewLifecycleOwner(), state -> {
            if (state instanceof ProductDetailsViewState.ProductState) {
                renderProductView(((ProductDetailsViewState.ProductState) state).getProduct());
            }
        });
    }

    private void renderProductView(Product product) {
        String firstImageUrl = CollectionsKt.firstOrNull(product.getImageUrls());
        if (firstImageUrl != null && !firstImageUrl.isEmpty()) {
            Glide.with(requireContext())
                    .load(firstImageUrl)
                    .fitCenter()
                    .placeholder(R.drawable.default_image)
                    .error(R.drawable.default_image)
                    .into(binding.ivProductImage);
        } else {
            binding.ivProductImage.setImageResource(R.drawable.default_image);
        }

        binding.tvProductName.setText(product.getName());
        binding.tvProductPrice.setText(product.getPrice());
    }

    @NonNull
    @Override
    protected ProductDetailsViewModel getViewModel() {
        return new ViewModelProvider(this).get(ProductDetailsViewModel.class);
    }

    @NonNull
    @Override
    protected FragmentProductDetailsBinding createViewBinding() {
        return FragmentProductDetailsBinding.inflate(
                LayoutInflater.from(requireContext())
        );
    }
}
