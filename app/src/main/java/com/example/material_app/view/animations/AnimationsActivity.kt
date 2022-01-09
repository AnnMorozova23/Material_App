package com.example.material_app.view.animations

import android.graphics.Rect
import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.*
import com.example.material_app.R
import com.example.material_app.databinding.ActivityAnimationsBinding

class AnimationsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAnimationsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.MyTheme)
        binding = ActivityAnimationsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.adapter = Adapter()
    }

    inner class Adapter : RecyclerView.Adapter<ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.activity_animations_recycler_item, parent, false)
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.itemView.setOnClickListener {
                val explodeTransition = Explode()
                val button = it as Button
                val fadeTransition = Fade()
                val setTransition = TransitionSet()
                explodeTransition.duration = 5000
                fadeTransition.duration = 5000
                explodeTransition.excludeTarget(button, true)
                explodeTransition.epicenterCallback = object : Transition.EpicenterCallback() {
                    override fun onGetEpicenter(transition: Transition): Rect {
                        val rect = Rect()
                        button.getGlobalVisibleRect(rect)
                        return rect
                    }
                }

                setTransition.addTransition(fadeTransition)
                setTransition.addTransition(explodeTransition)
                TransitionManager.beginDelayedTransition(binding.recyclerView, setTransition)
                binding.recyclerView.adapter = null
            }
        }


        override fun getItemCount(): Int {
            return 32
        }

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}