package com.example.nasamaterial

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.nasamaterial.viewModel.SomeInterface

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    SomeInterface.ItemTouchHelperViewHolder {
    abstract fun bind(data: DataNote)

    override fun onItemSelected() {
        itemView.setBackgroundResource(R.color.fog);
    }

    override fun onItemClear() {
        itemView.setBackgroundColor(0)
    }
}