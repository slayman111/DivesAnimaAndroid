package com.example.divesanimaandroid.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.divesanimaandroid.R
import com.example.divesanimaandroid.utils.bitmap.BitmapConverter
import com.example.divesanimaandroid.utils.http.DivesAnimaClient
import com.example.divesanimaandroid.utils.log.Logger
import kotlinx.android.synthetic.main.fragment_article_info.imageViewArticle
import kotlinx.android.synthetic.main.fragment_article_info.textViewDate
import kotlinx.android.synthetic.main.fragment_article_info.textViewText
import kotlinx.android.synthetic.main.fragment_article_info.textViewTitle
import kotlinx.coroutines.launch

class ArticleInfoFragment : Fragment() {

    private val log = Logger.getLogger(javaClass)

    private lateinit var divesAnimaClient: DivesAnimaClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        divesAnimaClient = DivesAnimaClient(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_article_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            arguments?.getInt(getString(R.string.article_id))?.let { articleId ->
                divesAnimaClient.getArticleById(articleId)?.let { article ->
                    log.info("article: $article")

                    textViewTitle.text = article.titleText
                    textViewDate.text = article.date
                    article.titleImage?.let { titleImage ->
                        imageViewArticle.setImageBitmap(
                            BitmapConverter.bitmapFromStringByteArray(
                                titleImage
                            )
                        )
                    }
                    textViewText.text = article.text
                }
            }
        }
    }

}