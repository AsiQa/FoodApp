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
class RecipeDetailsViewModel @Inject constructor(
    private val recipesRepository: RecipesRepository
) : ViewModel() {

    private val _recipeDetailsResult = MutableLiveData<Resource<RecipeDetails>>()
    val recipeDetailsResult: LiveData<Resource<RecipeDetails>> = _recipeDetailsResult

    fun getRecipeDetails(recipeId: String) = viewModelScope.launch {
        _recipeDetailsResult.value = Resource.Loading()

        when (val response = recipesRepository.getRecipeDetails(recipeId)) {
            is Resource.Success -> {
                _recipeDetailsResult.value = Resource.Success(response.data.recipe)
            }

            is Resource.Error -> {
                println(response.error)
            }
        }
    }

    fun setRecipeDetails(recipeEntity: RecipeEntity) {
        _recipeDetailsResult.value = Resource.Success(
            RecipeDetails(
                recipeEntity.ingredients,
                recipeEntity.imageUrl,
                null,
                null,
                recipeEntity.category,
                null,
                recipeEntity.id,
                null,
                recipeEntity.title
            )
        )
    }
}