package org.d3if4127.jurnal11.ui.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_diary.view.*
import org.d3if4127.jurnal11.R
import org.d3if4127.jurnal11.database.Diary


class diaryAdapter(private val myDataset: List<Diary>, val onClickListener: OnClickListener) :
    RecyclerView.Adapter<diaryAdapter.MyViewHolder>() {

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val diary_item = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_diary, parent, false)
        return MyViewHolder(diary_item)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.view.tv_date.text = myDataset[position].lastUpdate.toString()
        holder.view.tv_paragraf.text = myDataset[position].content
        holder.view.setOnClickListener {
            onClickListener.onClick(myDataset[position])
        }
    }

    override fun getItemCount() = myDataset.size

    class OnClickListener(val clickListener: (title: Diary) -> Unit) {
        fun onClick(title: Diary) = clickListener(title)
    }
}