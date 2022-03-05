package com.example.dubizzletask.features.products.presentation.productsList

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dubizzletask.R
import com.example.dubizzletask.core.BaseFragment
import com.example.dubizzletask.databinding.FragmentProductsListBinding
import com.example.dubizzletask.features.products.domain.models.Product
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsListFragment : BaseFragment<FragmentProductsListBinding, ProductsListViewModel>() {

    private lateinit var productsAdapter: ProductsAdapter

    override fun onReady(savedInstanceState: Bundle?) {
        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        productsAdapter = ProductsAdapter(emptyList())
        productsAdapter.onItemClickListener = {
            viewModel.onEvent(
                ProductsListEvent.ProductClicked(it)
            )
        }

        binding.rvProducts.apply {
            addItemDecoration(
                DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
            )

            adapter = productsAdapter
        }
    }

    private fun observeViewModel() {
        viewModel.viewState.observe(viewLifecycleOwner) {
            when (it) {
                is ProductsListViewState.Loading -> renderLoadingView()

                is ProductsListViewState.Error -> renderErrorView(it.message)

                is ProductsListViewState.Products -> renderProductsView(it.products)

                is ProductsListViewState.Empty -> renderEmptyView()
            }
        }
    }

    private fun renderProductsView(products: List<Product>) = with(binding) {
        productsAdapter.products = products
        productsAdapter.notifyDataSetChanged()

        showOneView(rvProducts, pbProducts, tvProductsEmptyMsg, tvProductsEmptyMsg)
    }

    private fun renderLoadingView() = with(binding) {
        showOneView(pbProducts, tvProductsErrorMsg, rvProducts, tvProductsEmptyMsg)
    }

    private fun renderErrorView(message: String? = null) = with(binding) {
        tvProductsErrorMsg.text = message ?: getString(R.string.something_went_wrong)
        showOneView(tvProductsErrorMsg, rvProducts, pbProducts, tvProductsEmptyMsg)
    }

    private fun renderEmptyView() = with(binding) {
        showOneView(tvProductsEmptyMsg, tvProductsErrorMsg, rvProducts, pbProducts)
    }

    override val viewModel: ProductsListViewModel by viewModels ()

    override fun createViewBinding(): FragmentProductsListBinding {
        return FragmentProductsListBinding.inflate(LayoutInflater.from(requireContext()))
    }
}