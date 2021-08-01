package rs.raf.projekat2.aleksandar_kovacevic_rn7219.presentation.view.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import rs.raf.projekat2.aleksandar_kovacevic_rn7219.data.models.Recipe
import rs.raf.projekat2.aleksandar_kovacevic_rn7219.databinding.RecipeItemLayoutBinding
import rs.raf.projekat2.aleksandar_kovacevic_rn7219.utils.viewbinding.RecyclerViewHolderBinding

class RecipesRecyclerViewAdapter(private val callback: (String) -> Unit) :
    ListAdapter<Recipe, RecyclerViewHolderBinding<RecipeItemLayoutBinding>>(
        object : DiffUtil.ItemCallback<Recipe>() {
            override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
                return oldItem == newItem
            }

        }) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = RecyclerViewHolderBinding(
        RecipeItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(
        holder: RecyclerViewHolderBinding<RecipeItemLayoutBinding>,
        position: Int
    ) {
        val recipe = currentList[position]

        with(holder.viewBinding) {
            tvRecipeName.text = recipe.title
            tvRecipePublisher.text = recipe.publisher
            tvRecipeSocialRank.text = recipe.socialUrl
            Glide.with(ivRecipeIcon).load(recipe.imageUrl).into(ivRecipeIcon)

            clRecipeItemLayout.setOnClickListener {
                callback.invoke(recipe.id)
            }
        }
    }
}
