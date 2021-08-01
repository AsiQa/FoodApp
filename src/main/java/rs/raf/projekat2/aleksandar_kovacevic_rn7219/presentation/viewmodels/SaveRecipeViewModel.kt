package rs.raf.projekat2.aleksandar_kovacevic_rn7219.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import rs.raf.projekat2.aleksandar_kovacevic_rn7219.data.models.RecipeDetails
import rs.raf.projekat2.aleksandar_kovacevic_rn7219.data.models.RecipeEntity
import rs.raf.projekat2.aleksandar_kovacevic_rn7219.data.repositories.RecipesRepository
import rs.raf.projekat2.aleksandar_kovacevic_rn7219.utils.Resource
import javax.inject.Inject

@HiltViewModel
class SaveRecipeViewModel @Inject constructor(
    private val recipesRepository: RecipesRepository
) : ViewModel() {

    private val _insertRecipeResult = MutableLiveData<Resource<Unit>>()
    val insertRecipeResult: LiveData<Resource<Unit>> = _insertRecipeResult

    fun insertRecipe(selectedDate: String, selectedCategory: String, recipeDetails: RecipeDetails, path: String?, fileName: String?) =
        viewModelScope.launch {
            val response = recipesRepository.insertRecipe(
                RecipeEntity(
                    recipeDetails.recipe_id ?: "",
                    recipeDetails.title,
                    recipeDetails.image_url ?: "",
                    recipeDetails.ingredients,
                    selectedDate,
                    selectedCategory,
                    path,
                    fileName
                )
            )

            _insertRecipeResult.value = response
        }
}
