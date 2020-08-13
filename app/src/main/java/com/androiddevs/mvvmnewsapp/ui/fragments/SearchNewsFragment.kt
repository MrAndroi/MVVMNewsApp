package com.androiddevs.mvvmnewsapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.ui.Resuorce
import com.androiddevs.mvvmnewsapp.ui.adapters.NewsAdapter
import com.androiddevs.mvvmnewsapp.ui.viewModels.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_search_news.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchNewsFragment:Fragment(R.layout.fragment_search_news) {

    val viewModel:NewsViewModel by viewModels()
    lateinit var adapter: NewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        var job:Job? =null
        etSearch.addTextChangedListener {
            job?.cancel()
            job = MainScope().launch {
                delay(500L)
                viewModel.searchForNews(it.toString())
            }

        }

        adapter = NewsAdapter()


        adapter.onItemClick {
            val bundle = Bundle().apply {
                putSerializable("article",it)
            }
            findNavController().navigate(R.id.action_searchNewsFragment_to_articleFragment,bundle)
        }

        viewModel.newsAfterSearch.observe(viewLifecycleOwner, Observer {

            when(it){

                is Resuorce.Loading ->{
                    paginationProgressBar.visibility = View.VISIBLE
                    rvSearchNews.visibility = View.INVISIBLE
                }
                is Resuorce.Success ->{
                    paginationProgressBar.visibility=View.INVISIBLE
                    rvSearchNews.visibility = View.VISIBLE
                    it.data?.let {
                        adapter.differ.submitList(it.articles)
                        rvSearchNews.adapter = adapter
                    }
                }
                is Resuorce.Error ->{
                    paginationProgressBar.visibility = View.INVISIBLE
                    rvSearchNews.visibility = View.INVISIBLE
                }
            }
        })


    }
}