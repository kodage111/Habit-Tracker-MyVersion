package com.kod.habittracker.ui.fragments.habitlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.kod.habittracker.R
import com.kod.habittracker.data.model.Habit
import com.kod.habittracker.utilities.Calculations

class HabitListAdapter(private val habit: List<Habit>) : RecyclerView.Adapter<HabitListAdapter.MyViewHolder>() {

    class MyViewHolder(view: View): RecyclerView.ViewHolder(view){
        private var title2Print: TextView = itemView.findViewById(R.id.tv_item_title)
        private var timeElapsed: TextView = itemView.findViewById(R.id.tv_timeElapsed)
        private var description2Print: TextView = itemView.findViewById(R.id.tv_item_description)
        private var timeStamp: TextView = itemView.findViewById(R.id.tv_item_createdTimeStamp)
        private var image2Print: ImageView = itemView.findViewById(R.id.iv_habit_icon)

        fun bindItems(myHabit: Habit){
            val a = Calculations.calculateTimeBetweenDates(myHabit.startTime)
            title2Print.text =  myHabit.title
            timeElapsed.text = a
            description2Print.text = myHabit.description
            timeStamp.text = "Since: ${myHabit.startTime}"
            image2Print.setImageResource(myHabit.imageID)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder
    = MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.habit_item, parent,false))

    override fun getItemCount(): Int = habit.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentHabit = habit[position]

        holder.bindItems(currentHabit)

        holder.itemView.findViewById<CardView>(R.id.cv_cardView).setOnClickListener{
            val action = HabitListDirections.actionHabitListToUpdateHabitItem(currentHabit)
            holder.itemView.findNavController().navigate(action)
        }
    }
}