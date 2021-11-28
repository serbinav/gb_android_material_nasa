package com.example.nasamaterial.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.nasamaterial.DataNote
import com.example.nasamaterial.databinding.ActivityRecyclerBinding
import com.example.nasamaterial.viewModel.SomeInterface

class RecyclerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecyclerBinding
    private lateinit var itemTouchHelper: ItemTouchHelper
    private lateinit var adapter: RecyclerActivityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val data = arrayListOf(
            DataNote("Animated Vector Drawable", ""),
            DataNote("Collapsing Toolbar Layout", "some text")
        )
        data.add(0, DataNote("Header"))
        adapter = RecyclerActivityAdapter(
            object : SomeInterface.OnListItemClickListener {
                override fun onItemClick(data: DataNote) {
                    Toast.makeText(this@RecyclerActivity, data.someText, Toast.LENGTH_SHORT).show()
                }
            },
            data
        )

        binding.recyclerView.adapter = adapter
        itemTouchHelper = ItemTouchHelper(RecyclerActivityAdapter.ItemTouchHelperCallback(adapter))
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }
}