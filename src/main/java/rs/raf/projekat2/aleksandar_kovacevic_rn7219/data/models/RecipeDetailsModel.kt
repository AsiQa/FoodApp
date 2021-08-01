package rs.raf.projekat2.aleksandar_kovacevic_rn7219.data.models

import java.io.Serializable

data class RecipeDetailsResponse(
    val recipe: RecipeDetails
)

data class RecipeDetails(
    val ingredients: List<String>,
    val image_url: String?,
    val social_rank: String?,
    val _id: String?,
    val publisher: String?,
    val source_url: String?,
    val recipe_id: String?,
    val publisher_url: String?,
    val title: String
) : Serializable
