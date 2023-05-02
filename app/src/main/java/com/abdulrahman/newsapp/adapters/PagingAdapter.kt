package com.abdulrahman.newsapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.abdulrahman.databinding.ItemArticlePreviewBinding
import com.abdulrahman.newsapp.models.Article
import com.bumptech.glide.Glide

class PagingAdapter (private val onClickListener: OnClickListener)
    : PagingDataAdapter<Article, PagingAdapter.ArticleViewHolder>(ArticleItemDiffCallback) {

    private lateinit var binding: ItemArticlePreviewBinding


    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bind(it) }
        holder.setIsRecyclable(false)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding= ItemArticlePreviewBinding.inflate(inflater,parent,false)
        return ArticleViewHolder(binding)
    }



    inner class ArticleViewHolder(itemView: ItemArticlePreviewBinding) : RecyclerView.ViewHolder(itemView.root) {
        fun bind(article: Article){
            binding.apply {
                Glide.with(itemView.context).load(article.urlToImage).centerCrop().into(ivArticleImage)
                tvTitle.text = article.title
                binding.root.setOnClickListener {
                    onClickListener.onClick(article)
                }
            }
        }
    }

    class OnClickListener(val clickListener: (article: Article) -> Unit) {
        fun onClick(article: Article) = clickListener(article)
    }


    object ArticleItemDiffCallback: DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
           return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.equals(newItem)
        }

    }



}