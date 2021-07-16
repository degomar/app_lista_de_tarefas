package com.example.mytodolist.UI

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mytodolist.R
import com.example.mytodolist.model.DataSource
import com.example.mytodolist.databinding.ItemRvBinding

class TaskAdapter: ListAdapter<DataSource, TaskAdapter.TaskViewHolder>(DiffCallBack()) {

    var listenerEdit : (DataSource) -> Unit = {}
    var listenerDelete : (DataSource) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        val layoutBind = ItemRvBinding.inflate(inflate, parent, false)
        return TaskViewHolder(layoutBind)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
       holder.bind(getItem(position))
    }

    inner class TaskViewHolder(val binding: ItemRvBinding): RecyclerView.ViewHolder(binding.root)
    {

        fun bind(item: DataSource) {
            binding.rvItemDate.setText("${item.date} ${item.hour}")
            binding.rvItemTitle.setText(item.title)
            binding.rvItemDescription.setText(item.desc)
            binding.icMoreOptions.setOnClickListener {
                showPopUp(item)
            }
        }

        private fun showPopUp(item: DataSource) {
            val icon = binding.icMoreOptions
            val popMenu = PopupMenu(icon.context, icon)
            popMenu.menuInflater.inflate(R.menu.pop_menu, popMenu.menu)
            popMenu.setOnMenuItemClickListener {
                when (it.itemId){
                    R.id.action_edit -> listenerEdit(item)
                    R.id.action_deletar -> listenerDelete(item)
                }
                    return@setOnMenuItemClickListener true
            }
            popMenu.show()
        }

    }
}

class DiffCallBack: DiffUtil.ItemCallback<DataSource>(){
    override fun areItemsTheSame(oldItem: DataSource, newItem: DataSource) = oldItem == newItem
    override fun areContentsTheSame(oldItem: DataSource, newItem: DataSource) = oldItem.id == newItem.id


}