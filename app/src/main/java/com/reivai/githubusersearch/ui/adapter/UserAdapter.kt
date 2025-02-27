package com.reivai.githubusersearch.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.reivai.githubusersearch.network.model.User
import com.reivai.githubusersearch.databinding.ItemUserBinding

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private val list = ArrayList<User>()

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    fun setList(users: ArrayList<User>) {
        list.clear()
        list.addAll(users)
        notifyDataSetChanged()
    }
    inner class UserViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user : User) {
            binding.root.setOnClickListener{
                onItemClickCallback?.onItemClick(user)
            }

            Glide.with(itemView)
                .load(user.avatar_url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .centerCrop()
                .into(binding.ivUserPict)

            binding.tvUsername.text = user.login

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder((view))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickCallback{
        fun onItemClick(data: User)
    }
}