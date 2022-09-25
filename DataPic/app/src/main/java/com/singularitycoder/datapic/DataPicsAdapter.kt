package com.singularitycoder.datapic

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.singularitycoder.datapic.databinding.ListItemDataPicBinding

class DataPicsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var dataPicsList = mutableListOf<DataPic>()
    private var itemClickListener: (dataPic: DataPic, isExpanded: Boolean) -> Unit = { dataPic, isExpanded -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemBinding = ListItemDataPicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataPicViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as DataPicViewHolder).setData(dataPicsList[position])
    }

    override fun getItemCount(): Int = dataPicsList.size

    override fun getItemViewType(position: Int): Int = position

    fun setItemClickListener(listener: (contact: DataPic, isExpanded: Boolean) -> Unit) {
        itemClickListener = listener
    }

    inner class DataPicViewHolder(
        private val itemBinding: ListItemDataPicBinding,
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        @SuppressLint("SetTextI18n")
        fun setData(contact: DataPic) {
            itemBinding.apply {

            }
        }
    }
}
