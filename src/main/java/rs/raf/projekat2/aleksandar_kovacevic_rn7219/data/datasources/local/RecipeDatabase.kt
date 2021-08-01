package rs.raf.projekat2.aleksandar_kovacevic_rn7219.data.datasources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import rs.raf.projekat2.aleksandar_kovacevic_rn7219.data.models.RecipeEntity

@Database(
    entities = [RecipeEntity::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class RecipeDatabase : RoomDatabase() {

    abstract fun getRecipeDAO(): RecipeDAO
}
