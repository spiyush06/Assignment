package com.vegasega.myapplication.presentation.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.vegasega.myapplication.R
import com.vegasega.myapplication.data.model.Data
import com.vegasega.myapplication.presentation.ui.viewmodel.LiveSchemeViewModel
import com.vegasega.myapplication.util.Utils.Companion.dateformat

class LiveSchemeAdapter(private val exchangeRateItemList: MutableList<Data>, private val listener: OnItemClickListener, private val viewModel: LiveSchemeViewModel) :  RecyclerView.Adapter<LiveSchemeAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_list_live_scheme, parent, false)

        return MyViewHolder(view)
    }

    fun setData(data: MutableList<Data>) {
        data.clear()
        data.addAll(data)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = exchangeRateItemList[position]

        holder.itemView.setOnClickListener {
            listener.onItemClick(position)
        }
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return exchangeRateItemList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val txvSchemeName: TextView = itemView.findViewById(R.id.txv_name)
        private val linearLayout: LinearLayout = itemView.findViewById(R.id.linearlayout_status)
        private val txvSchemeDescription: TextView = itemView.findViewById(R.id.txv_description)
        private val txvSchemeEndDate: TextView = itemView.findViewById(R.id.txv_end_at)
        private val txvUserSchemeStatus: TextView = itemView.findViewById(R.id.txv_user_scheme_status)
        private val txvStatus: TextView = itemView.findViewById(R.id.txv_status)
        private val imageView: ImageView = itemView.findViewById(R.id.img_scheme_image)

        fun bind(exchangeRateItem: Data) {

            txvSchemeName.text = exchangeRateItem.name
            txvSchemeDescription.text = exchangeRateItem.description
            txvSchemeEndDate.text = "Valid Date " + dateformat(exchangeRateItem.end_at)
            txvUserSchemeStatus.text = "Status "+exchangeRateItem.user_scheme_status
            txvStatus.text = exchangeRateItem.status

            if(exchangeRateItem.status.equals("Active")) {
                linearLayout.setBackgroundColor(Color.parseColor("#008000"))
            } else {
                linearLayout.setBackgroundColor(Color.parseColor("#ff0000"))
            }

            Glide.with(imageView.context)
                .setDefaultRequestOptions(
                    RequestOptions()
                        .circleCrop().error(R.drawable.scheme)
                )
                .load(exchangeRateItem.scheme_image.url)
                .into(imageView)

            //viewModel.translateText(exchangeRateItem.name)
        }
    }
}