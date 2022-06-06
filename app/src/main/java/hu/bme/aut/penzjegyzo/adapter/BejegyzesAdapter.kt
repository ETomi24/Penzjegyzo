package hu.bme.aut.penzjegyzo.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.penzjegyzo.data.Bejegyzes
import hu.bme.aut.penzjegyzo.databinding.ItemBejegyzesListaBinding
import java.util.*

class BejegyzesAdapter(private val listener:BejegyzesekClickListener):
    RecyclerView.Adapter<BejegyzesAdapter.BejegyzesViewHolder>() {
    private val items = mutableListOf<Bejegyzes>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BejegyzesViewHolder(
        ItemBejegyzesListaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: BejegyzesViewHolder, position: Int) {
        val bejegyzes = items[position]

        holder.binding.tvName.text = bejegyzes.name

        holder.binding.tvCategory.text = bejegyzes.category.name
        if(bejegyzes.category.name == "KIADÁS"){
            holder.binding.tvCategory.setTextColor(Color.RED)
        }
        else{
            holder.binding.tvCategory.setTextColor(Color.GREEN)
        }
        holder.binding.tvMethod.text = bejegyzes.pay_method.name
        holder.binding.tvValue.text = "${bejegyzes.value} Ft"
        holder.binding.tvDescription.text = bejegyzes.description
        holder.binding.tvDate.text = bejegyzes.date
        holder.binding.ibRemove.setOnClickListener(){
            listener.onItemDeleted(bejegyzes)
        }

    }


    fun addItem(item: Bejegyzes) {
        items.add(item)
        notifyItemInserted(items.size - 1)

    }

    fun update(BejegyzesItems: List<Bejegyzes>) {
        items.clear()
        items.addAll(BejegyzesItems)

        notifyDataSetChanged()
    }

    fun deleteItem(item: Bejegyzes){
        notifyItemRemoved(items.indexOf(item));
        items.remove(item);
    }


    override fun getItemCount(): Int = items.size

    interface BejegyzesekClickListener {
        fun onItemDeleted(item : Bejegyzes)
    }

    inner class BejegyzesViewHolder(val binding: ItemBejegyzesListaBinding) : RecyclerView.ViewHolder(binding.root)
}