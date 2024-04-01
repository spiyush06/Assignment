package com.vegasega.myapplication.presentation.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vegasega.myapplication.R
import com.vegasega.myapplication.data.api.ApiService
import com.vegasega.myapplication.data.api.RetroClient
import com.vegasega.myapplication.data.model.Data
import com.vegasega.myapplication.data.repository.DataRepositoryImpl
import com.vegasega.myapplication.data.repository.TranslationDataRepository
import com.vegasega.myapplication.data.repository.TranslationDataRepositoryImpl
import com.vegasega.myapplication.databinding.ActivityMainBinding
import com.vegasega.myapplication.domain.usecase.GetDataUseCase
import com.vegasega.myapplication.domain.usecase.GetTranslationDataUseCase
import com.vegasega.myapplication.presentation.ui.adapter.LiveSchemeAdapter
import com.vegasega.myapplication.presentation.ui.adapter.OnItemClickListener
import com.vegasega.myapplication.util.ViewModelFactory
import com.vegasega.myapplication.presentation.ui.viewmodel.LiveSchemeViewModel
import com.vegasega.myapplication.util.Utils

class MainActivity : AppCompatActivity() {
    lateinit var mainBinding: ActivityMainBinding

    private var isLoading = false
    private var isLastPage = false
    private var currentPage = 0

    lateinit var adapter : LiveSchemeAdapter
    private var list = mutableListOf<Data>()

    private lateinit var viewModel: LiveSchemeViewModel

    private lateinit var getDataUseCase: GetDataUseCase
    private lateinit var dataRepository: DataRepositoryImpl

    private lateinit var getTranslationDataUseCase: GetTranslationDataUseCase
    private lateinit var translationDataRepository: TranslationDataRepositoryImpl
    private lateinit var strName : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        // Initialize ViewModel
        val apiService: ApiService = RetroClient.getClient().create(ApiService::class.java)
        dataRepository = DataRepositoryImpl(apiService)
        getDataUseCase = GetDataUseCase(dataRepository, currentPage)

        translationDataRepository = TranslationDataRepositoryImpl(apiService)
        getTranslationDataUseCase = GetTranslationDataUseCase(translationDataRepository, "", "", "", "", "")

        val viewModelFactory = ViewModelFactory { LiveSchemeViewModel(getDataUseCase) }
        viewModel = ViewModelProvider(this, viewModelFactory).get(LiveSchemeViewModel::class.java)

        adapter = LiveSchemeAdapter(list, object :
            OnItemClickListener {
            override fun onItemClick(position: Int) {
                // Handle item click here
                strName = list.get(position).name
            }
        }, viewModel)

        mainBinding.recyclerView.layoutManager = LinearLayoutManager(this)
        mainBinding.recyclerView.adapter = adapter

        viewModel.apiResponse.observe(this, Observer {
            list.addAll(it.data)
            adapter.setData(it.data as MutableList<Data>)
        })

        // Fetch data
        //loadMoreItems()
        viewModel.fetchData(currentPage)

        mainBinding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                if (!isLoading && !isLastPage) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                    ) {
                        loadMoreItems()
                    }
                }
            }
        })

        mainBinding.idBtnToggle.setOnClickListener {
            viewModel.translateText(list.get(0).name)
            adapter.notifyItemInserted(0)

            for(i in list.indices) {
                viewModel.translateText(list.get(i).name)
                adapter.notifyItemInserted(i)
            }
        }


        viewModel.translation.observe(this) { translation ->
            //translationTextView.text = translation
            Utils.showToastShort(this@MainActivity, translation)
        }

    }

    private fun loadMoreItems() {
        isLoading = true;
        mainBinding.mainProgress.visibility=View.VISIBLE
        currentPage++
        loadNextPageData(currentPage);
    }

    private fun loadNextPageData(page: Int) {
        getDataUseCase = GetDataUseCase(dataRepository, page)
        viewModel.fetchData(currentPage)
    }
}