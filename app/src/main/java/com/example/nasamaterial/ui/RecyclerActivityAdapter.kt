package com.example.nasamaterial.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nasamaterial.BaseViewHolder
import com.example.nasamaterial.DataNote
import com.example.nasamaterial.databinding.RecyclerItemEditBinding
import com.example.nasamaterial.databinding.RecyclerItemHeaderBinding
import com.example.nasamaterial.databinding.RecyclerItemViewBinding

class RecyclerActivityAdapter(
    private var onListItemClickListener: OnListItemClickListener,
    private var data: MutableList<DataNote>
) :
    RecyclerView.Adapter<BaseViewHolder>() {

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
                binding.searchItem.setOnClickListener {
                    onListItemClickListener.onItemClick(
                        data
                    )
                }
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

    interface OnListItemClickListener {
        fun onItemClick(data: DataNote)
    }

    companion object {
        private const val TYPE_VIEW = 0
        private const val TYPE_EDIT = 1
        private const val TYPE_HEADER = 2
    }
}