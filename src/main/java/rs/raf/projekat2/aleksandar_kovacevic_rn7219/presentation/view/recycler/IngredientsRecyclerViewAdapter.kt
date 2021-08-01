package rs.raf.projekat2.aleksandar_kovacevic_rn7219.presentation.view.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import rs.raf.projekat2.aleksandar_kovacevic_rn7219.databinding.IngredientItemLayoutBinding
import rs.raf.projekat2.aleksandar_kovacevic_rn7219.utils.viewbinding.RecyclerViewHolderBinding

class IngredientsRecyclerViewAdapter :
    ListAdapter<String, RecyclerViewHolderBinding<IngredientItemLayoutBinding>>(
        object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

        }) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = RecyclerViewHolderBinding(
        IngredientItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    //products binding with view
    override fun onBindViewHolder(
        holder: RecyclerViewHolderBinding<IngredientItemLayoutBinding>,
        position: Int
    ) {
        val ingredient = currentList[position]
        holder.viewBinding.tvIngredient.text = ingredient
    }
}