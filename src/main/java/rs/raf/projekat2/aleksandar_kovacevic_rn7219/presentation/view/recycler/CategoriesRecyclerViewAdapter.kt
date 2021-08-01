package rs.raf.projekat2.aleksandar_kovacevic_rn7219.presentation.view.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import rs.raf.projekat2.aleksandar_kovacevic_rn7219.data.models.Category
import rs.raf.projekat2.aleksandar_kovacevic_rn7219.databinding.CategoryItemLayoutBinding
import rs.raf.projekat2.aleksandar_kovacevic_rn7219.utils.viewbinding.RecyclerViewHolderBinding

class CategoriesRecyclerViewAdapter(private val callback: (String) -> Unit) :
    ListAdapter<Category, RecyclerViewHolderBinding<CategoryItemLayoutBinding>>(
        object : DiffUtil.ItemCallback<Category>() {
            override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
                return oldItem == newItem
            }

        }) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = RecyclerViewHolderBinding(
        CategoryItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(
        holder: RecyclerViewHolderBinding<CategoryItemLayoutBinding>,
        position: Int
    ) {
        val category = currentList[position]

        with(holder.viewBinding) {
            tvCategoryName.text = category.title
            Glide.with(ivCategoryIcon).load(category.imageUrl).apply(RequestOptions.circleCropTransform()).into(ivCategoryIcon)

            clCategoryItemLayout.setOnClickListener {
                callback.invoke(category.title)
            }
        }
    }
}
