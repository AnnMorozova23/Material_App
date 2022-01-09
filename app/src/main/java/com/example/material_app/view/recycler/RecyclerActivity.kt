package com.example.material_app.view.recycler

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.material_app.R
import com.example.material_app.databinding.ActivityRecyclerBinding

class RecyclerActivity: AppCompatActivity() {

    private lateinit var binding: ActivityRecyclerBinding
    lateinit var itemTouchHelper:ItemTouchHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.MyTheme)
        binding = ActivityRecyclerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = arrayListOf(
            Data("Earth",type= TYPE_EARTH) to false,
            Data("Mars", "",type= TYPE_MARS) to false,
        )
        data.add(0,Data("Заголовок",type= TYPE_HEADER) to false)


        val adapter = RecyclerActivityAdapter(data,
            object : MyCallback{
                override fun onClick(position: Int) {
                    Toast.makeText(this@RecyclerActivity,"РАБОТАЕТ ${data[position].first.someText} ${data[position].first.someDescription}",
                        Toast.LENGTH_SHORT).show()
                }

            }, object : RecyclerActivityAdapter.OnStartDragListener{
                override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
                    itemTouchHelper.startDrag(viewHolder)
                }
            }

        )

        binding.recyclerView.adapter = adapter
        binding.recyclerActivityFAB.setOnClickListener {
            adapter.appendItem()
        }
        itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(adapter))
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)

    }
}