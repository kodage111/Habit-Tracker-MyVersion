package com.kod.habittracker.ui.fragments.createhabit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.kod.habittracker.R
import com.kod.habittracker.utilities.ListOfDrawables

class DrawableAdapter : RecyclerView.Adapter<DrawableAdapter.DrawableHolder>(), ListOfDrawables {
    private var selectedItemPosition = 0
    class DrawableHolder(view: View): RecyclerView.ViewHolder(view){

        private val drawable2Print = itemView.findViewById<ImageView>(R.id.iv_habit_icon_item)
        fun bindItems(drawable: Int){
            drawable2Print.setImageResource(drawable)
        }
    }

    private var chosenDrawable: Int = 0
    fun getDrawable(): Int{
        return chosenDrawable
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DrawableHolder = DrawableHolder(LayoutInflater.from(parent.context)
        .inflate(R.layout.drawable_item,parent, false))

    override fun getItemCount(): Int = listOutlineOfDrawables.size

    override fun onBindViewHolder(holder: DrawableHolder, position: Int) {
        val currentDrawable = listOutlineOfDrawables[position]
        val currentColoredDrawable = listColoredDrawable[position]
        holder.bindItems(currentDrawable)

        holder.itemView. setOnClickListener {
            selectedItemPosition = holder.adapterPosition
            notifyDataSetChanged()
        }
        holder.itemView
        if(selectedItemPosition == holder.adapterPosition){
            holder.itemView.isSelected = !holder.itemView.isSelected
            chosenDrawable = when(holder.itemView.isSelected){
                true -> currentColoredDrawable
                else -> 0
            }
        }else {
            holder.itemView.isSelected = false
        }
    }
}