package com.example.dubizzletask.features.products.domain.models

import android.os.Parcel
import android.os.Parcelable

data class Product(
    val createdAt: String = "",
    val imageIds: List<String> = listOf(),
    val imageUrls: List<String> = listOf(),
    val imageUrlsThumbnails: List<String> = listOf(),
    val name: String = "",
    val price: String = "",
    val uid: String = ""
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.createStringArrayList() ?: emptyList(),
        parcel.createStringArrayList() ?: emptyList(),
        parcel.createStringArrayList() ?: emptyList(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(createdAt)
        parcel.writeStringList(imageIds)
        parcel.writeStringList(imageUrls)
        parcel.writeStringList(imageUrlsThumbnails)
        parcel.writeString(name)
        parcel.writeString(price)
        parcel.writeString(uid)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }
}
