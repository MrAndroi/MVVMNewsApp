package com.androiddevs.mvvmnewsapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.ui.models.Article
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_article_preview.view.*

class NewsAdapter:RecyclerView.Adapter<NewsAdapter.ViewHoler>() {


    inner class ViewHoler(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    private val diffUtil = object :DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this,diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.ViewHoler {
        return ViewHoler(LayoutInflater.from(parent.context).inflate(R.layout.item_article_preview,parent,false))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: NewsAdapter.ViewHoler, position: Int) {
        val article = differ.currentList[position]

        holder.itemView.apply {


            Glide.with(this).load(article.urlToImage).into(ivArticleImage)

            tvTitle.text = article.title

            tvDescription.text = article.description

            tvPublishedAt.text = article.publishedAt

            tvSource.text = article.source.name

            setOnClickListener {
               onItemClickListener?.let { it(article) }
            }
        }
    }

    private var onItemClickListener:( (Article) -> Unit )? = null

    fun onItemClick (listener:(Article)->Unit){
        onItemClickListener = listener

    }
}