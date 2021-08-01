package rs.raf.projekat2.aleksandar_kovacevic_rn7219.presentation.view.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import rs.raf.projekat2.aleksandar_kovacevic_rn7219.data.models.Recipe
import rs.raf.projekat2.aleksandar_kovacevic_rn7219.data.models.RecipeEntity
import rs.raf.projekat2.aleksandar_kovacevic_rn7219.databinding.RecipeItemLayoutBinding
import rs.raf.projekat2.aleksandar_kovacevic_rn7219.databinding.SavedRecipeItemLayoutBinding
import rs.raf.projekat2.aleksandar_kovacevic_rn7219.utils.viewbinding.RecyclerViewHolderBinding

class SavedRecipesRecyclerViewAdapter(private val callback: (RecipeEntity) -> Unit) :
    ListAdapter<RecipeEntity, RecyclerViewHolderBinding<SavedRecipeItemLayoutBinding>>(
        object : DiffUtil.ItemCallback<RecipeEntity>() {
            override fun areItemsTheSame(oldItem: RecipeEntity, newItem: RecipeEntity): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: RecipeEntity, newItem: RecipeEntity): Boolean {
                return oldItem == newItem
            }

        }) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = RecyclerViewHolderBinding(
        SavedRecipeItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(
        holder: RecyclerViewHolderBinding<SavedRecipeItemLayoutBinding>,
        position: Int
    ) {
        val recipe = currentList[position]

        with(holder.viewBinding) {
            tvRecipeName.text = recipe.title
            tvRecipeCategory.text = recipe.category
            tvRecipeDate.text = recipe.date

            clRecipeItemLayout.setOnClickListener {
                callback.invoke(recipe)
            }
        }
    }
}
