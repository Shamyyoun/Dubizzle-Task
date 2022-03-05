package com.example.dubizzletask.features.products.presentation.productsList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dubizzletask.R
import com.example.dubizzletask.databinding.ItemProductBinding
import com.example.dubizzletask.features.products.domain.models.Product

class ProductsAdapter(var products: List<Product>) :
    RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {
    var onItemClickListener: ((Product) -> Unit)? = null

    inner class ViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) = with(binding) {
            tvItemTitle.text = product.name
            tvItemPrice.text = product.price

            val firstImageThumbUrl = product.imageUrlsThumbnails.firstOrNull()
            if (firstImageThumbUrl != null) {
                Glide.with(root.context)
                    .load(firstImageThumbUrl)
                    .placeholder(R.drawable.default_image)
                    .error(R.drawable.default_image)
                    .into(ivItemImage)
            } else {
                ivItemImage.setImageResource(R.drawable.default_image)
            }

            binding.root.setOnClickListener {
                onItemClickListener?.invoke(product)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount(): Int {
        return products.size
    }
}