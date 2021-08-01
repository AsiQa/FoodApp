package rs.raf.projekat2.aleksandar_kovacevic_rn7219.data.repositories

import androidx.lifecycle.LiveData
import rs.raf.projekat2.aleksandar_kovacevic_rn7219.data.datasources.local.RecipeDAO
import rs.raf.projekat2.aleksandar_kovacevic_rn7219.data.datasources.remote.RecipesAPI
import rs.raf.projekat2.aleksandar_kovacevic_rn7219.data.models.CategoryResponse
import rs.raf.projekat2.aleksandar_kovacevic_rn7219.data.models.RecipeDetailsResponse
import rs.raf.projekat2.aleksandar_kovacevic_rn7219.data.models.RecipeEntity
import rs.raf.projekat2.aleksandar_kovacevic_rn7219.data.models.RecipesResponse
import rs.raf.projekat2.aleksandar_kovacevic_rn7219.utils.Resource
import javax.inject.Inject

class RecipesRepository @Inject constructor(
    private val recipesAPI: RecipesAPI,
    private val recipeDAO: RecipeDAO
) {

    suspend fun getAllCategories(): Resource<CategoryResponse> {
        val response = try {
            recipesAPI.getAllCategories()
        } catch (e: Exception) {
            return Resource.Error(e.toString())
        }
        return Resource.Success(response)
    }

    suspend fun searchRecipes(query: String): Resource<RecipesResponse> {
        val response = try {
            recipesAPI.searchRecipes(query)
        } catch (e: Exception) {
            return Resource.Error(e.toString())
        }
        return Resource.Success(response)
    }

    suspend fun getRecipeDetails(recipeId: String): Resource<RecipeDetailsResponse> {
        val response = try {
            recipesAPI.getRecipeDetails(recipeId)
        } catch (e: Exception) {
            return Resource.Error(e.toString())
        }
        return Resource.Success(response)
    }

    suspend fun insertRecipe(recipeEntity: RecipeEntity): Resource<Unit> {
        try {
            recipeDAO.insertRecipe(recipeEntity)
        } catch (e: Exception) {
            return Resource.Error(e.toString())
        }
        return Resource.Success(Unit)
    }

    suspend fun deleteRecipe(recipeEntity: RecipeEntity): Resource<Unit> {
        try {
            recipeDAO.deleteRecipe(recipeEntity)
        } catch (e: Exception) {
            return Resource.Error(e.toString())
        }
        return Resource.Success(Unit)
    }

    suspend fun getAllRecipes(): Resource<List<RecipeEntity>> {
        val response = try {
            recipeDAO.getAllRecipes()
        } catch (e: Exception) {
            return Resource.Error(e.toString())
        }
        return Resource.Success(response)
    }
}
