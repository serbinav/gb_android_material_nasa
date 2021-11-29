package com.example.nasamaterial.viewModel

import androidx.recyclerview.widget.RecyclerView
import com.example.nasamaterial.DataNote

class SomeInterface {

    interface OnListItemClickListener {
        fun onItemClick(data: DataNote)
    }

    interface ItemTouchHelperAdapter {
        fun onItemMove(fromPosition: Int, toPosition: Int)
        fun onItemDismiss(position: Int)
    }

    interface ItemTouchHelperViewHolder {
        fun onItemSelected()
        fun onItemClear()
    }

    interface OnStartDragListener {
        fun onStartDrag(viewHolder: RecyclerView.ViewHolder)
    }
}