package rs.raf.projekat2.aleksandar_kovacevic_rn7219.presentation.view.fragments

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import rs.raf.projekat2.aleksandar_kovacevic_rn7219.data.models.RecipeDetails
import rs.raf.projekat2.aleksandar_kovacevic_rn7219.databinding.RecipeDetailsFragmentBinding
import rs.raf.projekat2.aleksandar_kovacevic_rn7219.presentation.view.recycler.IngredientsRecyclerViewAdapter
import rs.raf.projekat2.aleksandar_kovacevic_rn7219.presentation.viewmodels.RecipeDetailsViewModel
import rs.raf.projekat2.aleksandar_kovacevic_rn7219.utils.Resource
import rs.raf.projekat2.aleksandar_kovacevic_rn7219.utils.viewbinding.ViewBindingFragment
import java.io.File
import java.io.FileInputStream

@AndroidEntryPoint
class RecipeDetailsFragment : ViewBindingFragment<RecipeDetailsFragmentBinding>({
    RecipeDetailsFragmentBinding.inflate(it)
}) {

    private val recipeDetailsViewModel: RecipeDetailsViewModel by viewModels()
    private val recipeDetailsFragmentArgs by navArgs<RecipeDetailsFragmentArgs>()

    private lateinit var recipeDetails: RecipeDetails

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (recipeDetailsFragmentArgs.recipeId != null) {
            recipeDetailsViewModel.getRecipeDetails(recipeDetailsFragmentArgs.recipeId!!)
        } else {
            recipeDetailsViewModel.setRecipeDetails(recipeDetailsFragmentArgs.savedRecipe!!)
        }

        with(viewBinding) {
            val ingredientsRecyclerViewAdapter = IngredientsRecyclerViewAdapter()
            rvIngredients.adapter = ingredientsRecyclerViewAdapter

            btnSave.setOnClickListener {
                findNavController().navigate(
                    RecipeDetailsFragmentDirections.actionRecipeDetailsFragmentToSaveRecipeFragment(
                        recipeDetails
                    )
                )
            }

            recipeDetailsViewModel.recipeDetailsResult.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Loading -> {
                        showLoadingState(true)
                    }

                    is Resource.Success -> {
                        showLoadingState(false)
                        println(it.data)
                        recipeDetails = it.data
                        with(it.data) {
                            if (recipeDetailsFragmentArgs.recipeId != null) {
                                tvRecipeSocialRank.text = social_rank
                                Glide.with(ivRecipeIcon).load(image_url).into(ivRecipeIcon)
                                btnSave.visibility = View.VISIBLE
                            } else {
                                tvRecipeSocialRank.text =
                                    recipeDetailsFragmentArgs.savedRecipe!!.date
                                if (recipeDetailsFragmentArgs.savedRecipe!!.path != null) {
                                    val f = File(
                                        recipeDetailsFragmentArgs.savedRecipe!!.path,
                                        "${recipeDetailsFragmentArgs.savedRecipe!!.fileName}.jpg"
                                    )
                                    Glide.with(ivRecipeIcon)
                                        .load(BitmapFactory.decodeStream(FileInputStream(f)))
                                        .into(ivRecipeIcon)
                                } else {
                                    Glide.with(ivRecipeIcon)
                                        .load(recipeDetailsFragmentArgs.savedRecipe!!.imageUrl!!)
                                        .into(ivRecipeIcon)
                                }

                                btnSave.visibility = View.GONE
                            }
                            tvRecipeName.text = title
                            ingredientsRecyclerViewAdapter.submitList(ingredients)
                        }
                    }
                }
            }
        }
    }

    private fun showLoadingState(loading: Boolean) {
        viewBinding.clRecipeDetails.isVisible = !loading
        viewBinding.pbLoadingBar.isVisible = loading
    }
}

