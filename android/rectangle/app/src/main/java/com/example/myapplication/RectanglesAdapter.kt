package com.example.myapplication

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RectanglesAdapter(
    private val viewModel: RectanglesViewModel
) : RecyclerView.Adapter<RectanglesAdapter.ViewHolder>() {

    private val items = mutableListOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_rectangle, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.fillView("Item $item", if (position % 2 == 0) Color.RED else Color.BLUE)
        holder.itemView.setOnClickListener {
            items.removeAt(position)
            viewModel.delete(item)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, items.size)
        }
    }

    override fun getItemCount(): Int = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(newItems: List<Int>) {
        items.clear()
        items += newItems
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textView: TextView = view.findViewById(R.id.itemText)

        fun fillView(nameItem: String, color: Int): Unit = with(textView) {
            text = nameItem
            setBackgroundColor(color)
        }
    }
}
