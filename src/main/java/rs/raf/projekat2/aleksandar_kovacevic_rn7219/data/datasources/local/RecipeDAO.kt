package rs.raf.projekat2.aleksandar_kovacevic_rn7219.data.datasources.local

import androidx.lifecycle.LiveData
import androidx.room.*
import rs.raf.projekat2.aleksandar_kovacevic_rn7219.data.models.RecipeEntity

@Dao
interface RecipeDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: RecipeEntity)

    @Delete
    suspend fun deleteRecipe(recipe: RecipeEntity)

    @Query("SELECT * FROM recipe_table")
    suspend fun getAllRecipes(): List<RecipeEntity>
}
