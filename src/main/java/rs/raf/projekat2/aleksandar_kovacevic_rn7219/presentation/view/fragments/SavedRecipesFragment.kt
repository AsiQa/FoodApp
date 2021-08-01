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
import rs.raf.projekat2.aleksandar_kovacevic_rn7219.databinding.SavedRecipesFragmentBinding
import rs.raf.projekat2.aleksandar_kovacevic_rn7219.presentation.view.recycler.SavedRecipesRecyclerViewAdapter
import rs.raf.projekat2.aleksandar_kovacevic_rn7219.presentation.viewmodels.SavedRecipesViewModel
import rs.raf.projekat2.aleksandar_kovacevic_rn7219.utils.Resource
import rs.raf.projekat2.aleksandar_kovacevic_rn7219.utils.viewbinding.ViewBindingFragment

@AndroidEntryPoint
class SavedRecipesFragment : ViewBindingFragment<SavedRecipesFragmentBinding>({
    SavedRecipesFragmentBinding.inflate(it)
}) {

    private val savedRecipesViewModel: SavedRecipesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewBinding) {
            savedRecipesViewModel.getAllSavedRecipes()
            val adapter = SavedRecipesRecyclerViewAdapter {
                findNavController().navigate(
                    SavedRecipesFragmentDirections.actionSavedRecipesFragmentToRecipeDetailsFragment(
                        null,
                        it
                    )
                )
            }
            rvRecipes.adapter = adapter

            ivMenu.setOnClickListener {
                PopupMenu(requireContext(), it).apply {
                    setOnMenuItemClickListener { item ->
                        when (item?.itemId) {
                            R.id.categories -> {
                                findNavController().navigateUp()
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

            savedRecipesViewModel.recipesResult.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Loading -> {
                        showLoadingState(true)
                    }

                    is Resource.Success -> {
                        showLoadingState(false)
                        adapter.submitList(it.data)
                    }
                }
            }
        }
    }

    private fun showLoadingState(loading: Boolean) {
        viewBinding.rvRecipes.isVisible = !loading
        viewBinding.pbLoadingBar.isVisible = loading
    }
}
