package rs.raf.projekat2.aleksandar_kovacevic_rn7219.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import rs.raf.projekat2.aleksandar_kovacevic_rn7219.data.models.RecipeEntity
import rs.raf.projekat2.aleksandar_kovacevic_rn7219.data.repositories.RecipesRepository
import rs.raf.projekat2.aleksandar_kovacevic_rn7219.utils.Resource
import javax.inject.Inject

@HiltViewModel
class SavedRecipesViewModel @Inject constructor(
    private val recipesRepository: RecipesRepository
) : ViewModel() {

    private val _recipesResult = MutableLiveData<Resource<List<RecipeEntity>>>()
    val recipesResult: LiveData<Resource<List<RecipeEntity>>> = _recipesResult

    fun getAllSavedRecipes() = viewModelScope.launch {
        when (val response = recipesRepository.getAllRecipes()) {
            is Resource.Success -> {
                _recipesResult.value = Resource.Success(response.data)
            }

            is Resource.Error -> {
                println(response.error)
            }
        }
    }
}