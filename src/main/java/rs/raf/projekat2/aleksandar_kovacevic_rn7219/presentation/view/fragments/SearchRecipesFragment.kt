package rs.raf.projekat2.aleksandar_kovacevic_rn7219.presentation.view.fragments

import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import rs.raf.projekat2.aleksandar_kovacevic_rn7219.R
import rs.raf.projekat2.aleksandar_kovacevic_rn7219.databinding.SearchRecipesFragmentBinding
import rs.raf.projekat2.aleksandar_kovacevic_rn7219.presentation.view.recycler.CategoriesRecyclerViewAdapter
import rs.raf.projekat2.aleksandar_kovacevic_rn7219.presentation.view.recycler.RecipesRecyclerViewAdapter
import rs.raf.projekat2.aleksandar_kovacevic_rn7219.presentation.viewmodels.RecipesViewModel
import rs.raf.projekat2.aleksandar_kovacevic_rn7219.utils.Resource
import rs.raf.projekat2.aleksandar_kovacevic_rn7219.utils.viewbinding.ViewBindingFragment

@AndroidEntryPoint
class SearchRecipesFragment : ViewBindingFragment<SearchRecipesFragmentBinding>({
    SearchRecipesFragmentBinding.inflate(it)
}) {

    private val recipesViewModel: RecipesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recipesViewModel.getAllCategories()

        with(viewBinding) {
            val categoriesRecyclerViewAdapter = CategoriesRecyclerViewAdapter {
                recipesViewModel.searchRecipes(it)
            }
            rvCategories.adapter = categoriesRecyclerViewAdapter

            val recipesRecyclerViewAdapter =
                RecipesRecyclerViewAdapter { id ->
                    findNavController().navigate(
                        SearchRecipesFragmentDirections.actionSearchRecipesFragmentToRecipeDetailsFragment(
                            id,
                            null
                        )
                    )
                }
            rvRecipes.adapter = recipesRecyclerViewAdapter

            etSearch.setOnEditorActionListener { _, _, _ ->
                recipesViewModel.searchRecipes(etSearch.text.toString())
                false
            }

            ivMenu.setOnClickListener {
                PopupMenu(requireContext(), it).apply {
                    setOnMenuItemClickListener { item ->
                        when (item?.itemId) {
                            R.id.saved_menus -> {
                                findNavController().navigate(SearchRecipesFragmentDirections.actionSearchRecipesFragmentToSavedRecipesFragment())
                                true
                            }
                            else -> false
                        }
                    }
                    inflate(R.menu.recipes_menu)
                    show()
                }
                requireActivity().openOptionsMenu()
            }

            recipesViewModel.categoriesResult.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Loading -> {
                        showLoadingState(loading = true, showRecipes = false)
                    }

                    is Resource.Success -> {
                        showLoadingState(loading = false, showRecipes = false)
                        categoriesRecyclerViewAdapter.submitList(it.data)
                    }
                }
            }

            recipesViewModel.recipesResult.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Loading -> {
                        showLoadingState(loading = true, showRecipes = true)
                    }

                    is Resource.Success -> {
                        showLoadingState(loading = false, showRecipes = true)
                        recipesRecyclerViewAdapter.submitList(it.data)
                    }
                }
            }
        }
    }

    private fun showLoadingState(loading: Boolean, showRecipes: Boolean) {
        viewBinding.rvCategories.isVisible = !loading && !showRecipes
        viewBinding.pbLoadingBar.isVisible = loading
        viewBinding.rvRecipes.isVisible = !loading && showRecipes
    }
}
