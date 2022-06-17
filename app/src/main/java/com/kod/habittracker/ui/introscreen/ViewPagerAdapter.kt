package com.kod.habittracker.ui.introscreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kod.habittracker.R
import com.kod.habittracker.data.model.IntroView

class ViewPagerAdapter(private val introList: List<IntroView>): RecyclerView.Adapter<ViewPagerAdapter.IntroViewHolder>() {
    class IntroViewHolder(itemview: View): RecyclerView.ViewHolder(itemview){
        private var description2Print: TextView = itemView.findViewById(R.id.tv_description_intro)
        private var image2Print: ImageView = itemView.findViewById(R.id.iv_image_intro)

        fun bindItems(introView: IntroView){
            description2Print.text = introView.description
            image2Print.setImageResource(introView.imageId)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerAdapter.IntroViewHolder
    = IntroViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.intro_item_page,parent,false))

    override fun onBindViewHolder(holder: ViewPagerAdapter.IntroViewHolder, position: Int) {
        val currentIntroView = introList[position]

        holder.bindItems(currentIntroView)
    }

    override fun getItemCount(): Int = introList.size
}