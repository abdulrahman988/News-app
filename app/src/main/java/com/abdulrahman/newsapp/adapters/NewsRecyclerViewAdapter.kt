package com.abdulrahman.newsapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.abdulrahman.databinding.ItemArticlePreviewBinding
import com.abdulrahman.newsapp.models.Article
import com.bumptech.glide.Glide

class NewsRecyclerViewAdapter(private val onClickListener: OnClickListener):
    RecyclerView.Adapter<NewsRecyclerViewAdapter.ArticleViewHolder>() {

    private lateinit var binding: ItemArticlePreviewBinding
    var items: List<Article> = ArrayList()




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding= ItemArticlePreviewBinding.inflate(inflater,parent,false)
        return ArticleViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(items[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
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


    fun submitList(articleList: List<Article>){
        val oldList = items
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
            ArticleItemDiffCallback(
                oldList
                ,articleList
            )
        )
        items = articleList
        diffResult.dispatchUpdatesTo(this)

    }

    class ArticleItemDiffCallback(
        var oldArticleList: List<Article>,
        var newArticleList: List<Article>
        ):DiffUtil.Callback(){
        override fun getOldListSize(): Int {
            return oldArticleList.size
        }

        override fun getNewListSize(): Int {
            return newArticleList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldArticleList[oldItemPosition].url == newArticleList[newItemPosition].url
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldArticleList[oldItemPosition].equals(newArticleList[newItemPosition])
        }
    }

    class OnClickListener(val clickListener: (article: Article) -> Unit) {
        fun onClick(article: Article) = clickListener(article)
    }
}