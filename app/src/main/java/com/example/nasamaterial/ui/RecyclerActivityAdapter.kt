package com.example.nasamaterial.ui

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.nasamaterial.BaseViewHolder
import com.example.nasamaterial.DataNote
import com.example.nasamaterial.databinding.RecyclerItemEditBinding
import com.example.nasamaterial.databinding.RecyclerItemHeaderBinding
import com.example.nasamaterial.databinding.RecyclerItemViewBinding
import com.example.nasamaterial.viewModel.SomeInterface

class RecyclerActivityAdapter(
    private var onListItemClickListener: SomeInterface.OnListItemClickListener,
    private var data: MutableList<DataNote>,
    private val dragListener: SomeInterface.OnStartDragListener
) :
    RecyclerView.Adapter<BaseViewHolder>(), SomeInterface.ItemTouchHelperAdapter {

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_VIEW -> WatchViewHolder(
                RecyclerItemViewBinding.inflate(inflater, parent, false)
            )
            TYPE_EDIT -> EditViewHolder(
                RecyclerItemEditBinding.inflate(inflater, parent, false)
            )
            else -> HeaderViewHolder(
                RecyclerItemHeaderBinding.inflate(inflater, parent, false)
            )
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            position == 0 -> TYPE_HEADER
            data[position].someDescription.isNullOrBlank() -> TYPE_EDIT
            else -> TYPE_VIEW
        }
    }

    inner class WatchViewHolder(private val binding: RecyclerItemViewBinding) :
        BaseViewHolder(binding.root) {
        override fun bind(data: DataNote) {
            binding.addItem.setOnClickListener { addItem() }
            binding.deleteItem.setOnClickListener { removeItem() }
            binding.downItem.setOnClickListener { moveDown() }
            binding.upItem.setOnClickListener { moveUp() }
            binding.header.setOnClickListener { toggleText() }
            binding.descriptionTextView.setOnClickListener { toggleText() }
            if (layoutPosition != RecyclerView.NO_POSITION) {
                binding.header.text = data.someText
                binding.descriptionTextView.text = data.someDescription
                binding.saveItem.setOnClickListener {
                    onListItemClickListener.onItemClick(
                        data
                    )
                }
            }
            binding.dragHandleImageView.setOnTouchListener { _, event ->
                if (event.actionMasked == MotionEvent.ACTION_DOWN) {
                    dragListener.onStartDrag(this)
                }
                false
            }
        }

        private fun addItem() {
            data.add(layoutPosition, DataNote("Inject", "some text"))
            notifyItemInserted(layoutPosition)
        }

        private fun removeItem() {
            data.removeAt(layoutPosition)
            notifyItemRemoved(layoutPosition)
        }

        private fun moveUp() {
            layoutPosition.takeIf { it > 1 }?.also { currentPosition ->
                data.removeAt(currentPosition).apply {
                    data.add(currentPosition - 1, this)
                }
                notifyItemMoved(currentPosition, currentPosition - 1)
            }
        }

        private fun moveDown() {
            layoutPosition.takeIf { it < data.size - 1 }?.also { currentPosition ->
                data.removeAt(currentPosition).apply {
                    data.add(currentPosition + 1, this)
                }
                notifyItemMoved(currentPosition, currentPosition + 1)
            }
        }

        private fun toggleText() {
            data.removeAt(layoutPosition)
            data.add(
                layoutPosition,
                DataNote(
                    binding.descriptionTextView.text.toString(),
                    binding.header.text.toString()
                )
            )
            notifyItemChanged(layoutPosition)
        }
    }

    inner class EditViewHolder(private val binding: RecyclerItemEditBinding) :
        BaseViewHolder(binding.root) {
        override fun bind(data: DataNote) {
            binding.editImageView.setOnClickListener { onListItemClickListener.onItemClick(data) }
            binding.addItem.setOnClickListener { addItem() }
            binding.deleteItem.setOnClickListener { removeItem() }
            binding.downItem.setOnClickListener { moveDown() }
            binding.upItem.setOnClickListener { moveUp() }
            binding.dragHandleImageView.setOnTouchListener { _, event ->
                if (event.actionMasked == MotionEvent.ACTION_DOWN) {
                    dragListener.onStartDrag(this)
                }
                false
            }
        }

        private fun addItem() {
            data.add(layoutPosition, DataNote("Autowired", ""))
            notifyItemInserted(layoutPosition)
        }

        private fun removeItem() {
            data.removeAt(layoutPosition)
            notifyItemRemoved(layoutPosition)
        }

        private fun moveUp() {
            layoutPosition.takeIf { it > 1 }?.also { currentPosition ->
                data.removeAt(currentPosition).apply {
                    data.add(currentPosition - 1, this)
                }
                notifyItemMoved(currentPosition, currentPosition - 1)
            }
        }

        private fun moveDown() {
            layoutPosition.takeIf { it < data.size - 1 }?.also { currentPosition ->
                data.removeAt(currentPosition).apply {
                    data.add(currentPosition + 1, this)
                }
                notifyItemMoved(currentPosition, currentPosition + 1)
            }
        }
    }

    inner class HeaderViewHolder(private val binding: RecyclerItemHeaderBinding) :
        BaseViewHolder(binding.root) {
        override fun bind(data: DataNote) {
            binding.header.setOnClickListener { onListItemClickListener.onItemClick(data) }
        }
    }

    class ItemTouchHelperCallback(private val adapter: RecyclerActivityAdapter) :
        ItemTouchHelper.Callback() {

        override fun isLongPressDragEnabled(): Boolean {
            return true
        }

        override fun isItemViewSwipeEnabled(): Boolean {
            return true
        }

        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int {
            val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
            val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
            return makeMovementFlags(
                dragFlags,
                swipeFlags
            )
        }

        override fun onMove(
            recyclerView: RecyclerView,
            source: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            adapter.onItemMove(source.adapterPosition, target.adapterPosition)
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, i: Int) {
            adapter.onItemDismiss(viewHolder.adapterPosition)
        }

        override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
            if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                val itemViewHolder = viewHolder as SomeInterface.ItemTouchHelperViewHolder
                itemViewHolder.onItemSelected()
            }
            super.onSelectedChanged(viewHolder, actionState)
        }

        override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
            super.clearView(recyclerView, viewHolder)
            val itemViewHolder = viewHolder as SomeInterface.ItemTouchHelperViewHolder
            itemViewHolder.onItemClear()
        }
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        data.removeAt(fromPosition).apply {
            data.add(if (toPosition > fromPosition) toPosition - 1 else toPosition, this)
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
    }

    companion object {
        private const val TYPE_VIEW = 0
        private const val TYPE_EDIT = 1
        private const val TYPE_HEADER = 2
    }
}