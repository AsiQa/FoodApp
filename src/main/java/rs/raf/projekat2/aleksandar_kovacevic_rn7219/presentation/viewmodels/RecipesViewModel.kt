package rs.raf.projekat2.aleksandar_kovacevic_rn7219.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import rs.raf.projekat2.aleksandar_kovacevic_rn7219.data.models.Category
import rs.raf.projekat2.aleksandar_kovacevic_rn7219.data.models.Recipe
import rs.raf.projekat2.aleksandar_kovacevic_rn7219.data.repositories.RecipesRepository
import rs.raf.projekat2.aleksandar_kovacevic_rn7219.utils.Resource
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    private val recipesRepository: RecipesRepository
) : ViewModel() {

    private val _categoriesResult = MutableLiveData<Resource<List<Category>>>()
    val categoriesResult: LiveData<Resource<List<Category>>> = _categoriesResult

    private val _recipesResult = MutableLiveData<Resource<List<Recipe>>>()
    val recipesResult: LiveData<Resource<List<Recipe>>> = _recipesResult

    fun getAllCategories() = viewModelScope.launch {
        _categoriesResult.value = Resource.Loading()

        when (val response = recipesRepository.getAllCategories()) {
            is Resource.Success -> {
                _categoriesResult.value = Resource.Success(response.data.categories)
            }

            is Resource.Error -> {
                println(response.error)
            }
        }
    }

    fun searchRecipes(query: String) = viewModelScope.launch {
        _recipesResult.value = Resource.Loading()

        when (val response = recipesRepository.searchRecipes(query)) {
            is Resource.Success -> {
                _recipesResult.value = Resource.Success(response.data.recipes)
            }

            is Resource.Error -> {
                println(response.error)
            }
        }
    }
}