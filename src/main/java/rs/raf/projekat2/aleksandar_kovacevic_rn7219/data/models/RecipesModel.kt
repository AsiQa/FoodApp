package rs.raf.projekat2.aleksandar_kovacevic_rn7219.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import rs.raf.projekat2.aleksandar_kovacevic_rn7219.utils.RECIPE_TABLE
import java.io.Serializable

data class RecipesResponse(
    val count: Int,
    val recipes: List<Recipe>
)

data class Recipe(
    val imageUrl: String,
    val socialUrl: String,
    val publisher: String,
    val sourceUrl: String,
    val id: String,
    val publishedId: String,
    val title: String
)

@Entity(tableName = RECIPE_TABLE)
data class RecipeEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val imageUrl: String?,
    val ingredients: List<String>,
    val date: String,
    val category: String,
    val path: String?,
    val fileName: String?
) : Serializable
