package com.example.divesanimaandroid.adapter

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.divesanimaandroid.R
import com.example.divesanimaandroid.models.dto.response.ArticlePreviewResponse
import com.example.divesanimaandroid.utils.bitmap.BitmapConverter
import kotlinx.android.synthetic.main.article_recycler_item.view.imageViewArticleImage
import kotlinx.android.synthetic.main.article_recycler_item.view.linearLayoutArticle
import kotlinx.android.synthetic.main.article_recycler_item.view.textViewArticleTitle


class ArticlesAdapter(
    private val context: Context,
    private val view: View,
    private val articles: List<ArticlePreviewResponse>
) : RecyclerView.Adapter<ArticlesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val linearLayoutArticle: LinearLayout
        val image: ImageView
        val title: TextView

        init {
            linearLayoutArticle = view.linearLayoutArticle
            image = view.imageViewArticleImage
            title = view.textViewArticleTitle
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.article_recycler_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = articles[position].titleText

        articles[position].titleImage?.let { titleImage ->
            BitmapConverter.bitmapFromStringByteArray(titleImage)?.let { bmp ->
                holder.image.setImageBitmap(
                    Bitmap.createBitmap(bmp)
                )
            }
        }

        holder.linearLayoutArticle.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt(context.getString(R.string.article_id), articles[position].id)

            view.findNavController()
                .navigate(R.id.action_articlesFragment_to_articleInfoFragment, bundle)
        }
    }

    override fun getItemCount(): Int = articles.size

}