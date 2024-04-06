package com.vegasega.myapplication.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.common.model.RemoteModelManager
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.TranslateRemoteModel
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

        adapter = SchemePagingAdapter(false)

        mainBinding.schemeRecyclerview.layoutManager = LinearLayoutManager(this)
        mainBinding.schemeRecyclerview.setHasFixedSize(true)
        mainBinding.schemeRecyclerview.adapter = adapter.withLoadStateHeaderAndFooter(
            header = LoaderAdapter(),
            footer = LoaderAdapter()
        )

        viewModel.list.observe(this, Observer {
            adapter.submitData(lifecycle, it)
        })

        val modelManager = RemoteModelManager.getInstance()

        val hindiModel = TranslateRemoteModel.Builder(TranslateLanguage.HINDI).build()

        val conditions = DownloadConditions.Builder()
            .build()

        var isDownload : Boolean = false

        modelManager.getDownloadedModels(TranslateRemoteModel::class.java)
            .addOnSuccessListener { models ->
                models.contains(hindiModel)
                isDownload=true
            }
            .addOnFailureListener {
                // Error.
                isDownload=false
            }


        if(!isDownload) {
            mainBinding.progressBar.visibility=View.VISIBLE
            mainBinding.schemeRecyclerview.visibility=View.GONE
            modelManager.download(hindiModel, conditions)
                .addOnSuccessListener {
                    // Model downloaded.
                    mainBinding.progressBar.visibility=View.GONE
                    mainBinding.schemeRecyclerview.visibility=View.VISIBLE
                }
                .addOnFailureListener {
                    // Error.
                    mainBinding.progressBar.visibility=View.GONE
                    mainBinding.schemeRecyclerview.visibility=View.VISIBLE
                }
        }

        mainBinding.switchLanguage.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mainBinding.switchLanguage.text = "Hindi"
                adapter.fetchData(true)
            } else {
                mainBinding.switchLanguage.text = "English"
                adapter.fetchData(false)
            }
        }

    }
}