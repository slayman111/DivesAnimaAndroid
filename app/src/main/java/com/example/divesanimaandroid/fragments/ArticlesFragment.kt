package com.example.divesanimaandroid.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.divesanimaandroid.R
import com.example.divesanimaandroid.adapter.ArticlesAdapter
import com.example.divesanimaandroid.utils.http.DivesAnimaClient
import com.example.divesanimaandroid.utils.log.Logger
import kotlinx.android.synthetic.main.fragment_articles.articlesRecycler
import kotlinx.coroutines.launch

class ArticlesFragment : Fragment() {

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
        return inflater.inflate(R.layout.fragment_articles, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            divesAnimaClient.getArticlesPreview()?.let { response ->
                log.info(response.toString())

                articlesRecycler.adapter =
                    ArticlesAdapter(requireContext(), view, response)

                articlesRecycler.layoutManager =
                    LinearLayoutManager(requireActivity().applicationContext)
            }
        }
    }

}