package com.androiddevs.mvvmnewsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.ui.Resuorce
import com.androiddevs.mvvmnewsapp.ui.adapters.NewsAdapter
import com.androiddevs.mvvmnewsapp.ui.viewModels.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_breaking_news.*

@AndroidEntryPoint
class BreakingFragment:Fragment(R.layout.fragment_breaking_news) {

    private val viewModel:NewsViewModel by viewModels()
    lateinit var newsAdapter: NewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newsAdapter = NewsAdapter()

        newsAdapter.onItemClick {
            val bundle = Bundle().apply {
                putSerializable("article",it)
            }
            findNavController().navigate(R.id.action_breakingFragment_to_articleFragment,bundle)
        }

        viewModel.breakingNewsList.observe(viewLifecycleOwner, Observer {
            when(it){

                is Resuorce.Success ->{
                    paginationProgressBar.visibility =View.INVISIBLE
                    it.data?.let {
                        newsAdapter.differ.submitList(it.articles)
                        rvBreakingNews.adapter = newsAdapter
                    }
                }

                is Resuorce.Loading ->{
                    paginationProgressBar.visibility = View.VISIBLE
                }

                is Resuorce.Error ->{
                    paginationProgressBar.visibility = View.INVISIBLE
                    it.massege?.let {
                        Log.e("error",it)
                    }
                }
            }

        })


    }
}