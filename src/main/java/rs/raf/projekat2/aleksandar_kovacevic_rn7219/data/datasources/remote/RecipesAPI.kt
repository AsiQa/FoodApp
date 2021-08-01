package rs.raf.projekat2.aleksandar_kovacevic_rn7219.data.datasources.remote

import retrofit2.http.GET
import retrofit2.http.Query
import rs.raf.projekat2.aleksandar_kovacevic_rn7219.data.models.CategoryResponse
import rs.raf.projekat2.aleksandar_kovacevic_rn7219.data.models.RecipeDetailsResponse
import rs.raf.projekat2.aleksandar_kovacevic_rn7219.data.models.RecipesResponse

interface RecipesAPI {

    @GET("v2/categories")
    suspend fun getAllCategories(): CategoryResponse

    @GET("v2/recipes")
    suspend fun searchRecipes(
        @Query("q") query: String,
        @Query("page") page: Int = 1
    ): RecipesResponse

    @GET("get")
    suspend fun getRecipeDetails(
        @Query("rId") recipeId: String
    ): RecipeDetailsResponse
}
