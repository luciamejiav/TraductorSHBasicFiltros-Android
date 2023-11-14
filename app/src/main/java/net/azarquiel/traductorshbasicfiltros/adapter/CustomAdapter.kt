package net.azarquiel.traductorshbasicfiltros.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import net.azarquiel.traductorshbasicfiltros.model.Word
import net.azarquiel.traductorshbasicfiltros.R

class CustomAdapter(val context: Context,
                    val layout: Int
                    ) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    private var dataList: List<Word> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewlayout = layoutInflater.inflate(layout, parent, false)
        return ViewHolder(viewlayout, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    internal fun setWords(words: List<Word>) {
        this.dataList = words
        notifyDataSetChanged()
    }


    class ViewHolder(viewlayout: View, val context: Context) : RecyclerView.ViewHolder(viewlayout) {
        fun bind(dataItem: Word){
            // itemview es el item de diseño
            // al que hay que poner los datos del objeto dataItem
            val tvespanol = itemView.findViewById(R.id.tvespanol) as TextView
            val tvingles = itemView.findViewById(R.id.tvingles) as TextView

            tvingles.text = dataItem.enWord
            tvespanol.text = dataItem.spWord

            itemView.tag = dataItem

        }

    }
}