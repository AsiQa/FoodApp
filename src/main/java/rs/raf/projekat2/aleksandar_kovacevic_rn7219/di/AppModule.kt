package rs.raf.projekat2.aleksandar_kovacevic_rn7219.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import rs.raf.projekat2.aleksandar_kovacevic_rn7219.data.datasources.local.RecipeDAO
import rs.raf.projekat2.aleksandar_kovacevic_rn7219.data.datasources.local.RecipeDatabase
import rs.raf.projekat2.aleksandar_kovacevic_rn7219.data.datasources.remote.RecipesAPI
import rs.raf.projekat2.aleksandar_kovacevic_rn7219.utils.BASE_URL
import rs.raf.projekat2.aleksandar_kovacevic_rn7219.utils.RECIPE_DATABASE_NAME
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(): RecipesAPI =
        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient())
            .build()
            .create(RecipesAPI::class.java)

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

    @Singleton
    @Provides
    fun provideRecipeDatabase(
        @ApplicationContext context: Context
    ): RecipeDatabase =
        Room.databaseBuilder(context, RecipeDatabase::class.java, RECIPE_DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun providePlayerDao(db: RecipeDatabase): RecipeDAO = db.getRecipeDAO()
}
