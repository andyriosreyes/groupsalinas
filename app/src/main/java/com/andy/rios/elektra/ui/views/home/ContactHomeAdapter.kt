package com.andy.rios.elektra.ui.views.home

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.andy.rios.elektra.R
import com.andy.rios.elektra.databinding.ItemContactBinding
import com.andy.rios.elektra.ui.model.Contact
import com.andy.rios.elektra.ui.util.DiffCallback
import com.bumptech.glide.Glide

class ContactHomeAdapter :
    ListAdapter<Contact, ContactHomeAdapter.RecipeHolder>(
        DiffCallback<Contact>(
            { old, new -> old == new },
            { old, new -> old == new },
        )
    ) {

    var  onItemClick: (Contact) -> Unit = {}
    var  onItemDelete: (Contact) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeHolder {
        return RecipeHolder(ItemContactBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: RecipeHolder, position: Int) {
        holder.onBind(getItem(position),onItemClick,onItemDelete)
    }

    class RecipeHolder(private val binding: ItemContactBinding) : RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun onBind(item : Contact, onItemClick: (Contact) -> Unit,onItemDelete: (Contact) -> Unit) {
            binding.tvRecipeName.text = "${item.name ?: ""} ${item.ape_pat ?: ""} ${item.ape_mat ?: ""}"
            if(!item.img.isNullOrEmpty()){
                val bitmap: Bitmap = BitmapFactory.decodeFile(item.img)
                binding.imgContact.setImageBitmap(bitmap)
            }else{
                binding.imgContact.setImageDrawable(ContextCompat.getDrawable(binding.root.context, R.drawable.ic_launcher_background))
            }

            binding.lyCardRecipe.setOnClickListener {
                onItemClick.invoke(item)
            }
            binding.ivDelete.setOnClickListener {
                onItemDelete.invoke(item)
            }
        }
    }

}