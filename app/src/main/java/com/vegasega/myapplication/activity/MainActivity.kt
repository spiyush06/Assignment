package com.vegasega.myapplication.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.vegasega.myapplication.paging.LoaderAdapter
import com.vegasega.myapplication.paging.SchemePagingAdapter
import com.vegasega.myapplication.R
import com.vegasega.myapplication.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var adapter: SchemePagingAdapter
    lateinit var mainBinding: ActivityMainBinding

    private val viewModel by viewModels<SchemeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        adapter = SchemePagingAdapter()

        mainBinding.schemeRecyclerview.layoutManager = LinearLayoutManager(this)
        mainBinding.schemeRecyclerview.setHasFixedSize(true)
        mainBinding.schemeRecyclerview.adapter = adapter.withLoadStateHeaderAndFooter(
            header = LoaderAdapter(),
            footer = LoaderAdapter()
        )

        viewModel.list.observe(this, Observer {
            adapter.submitData(lifecycle, it)
        })
    }
}