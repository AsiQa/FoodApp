package rs.raf.projekat2.aleksandar_kovacevic_rn7219.data.models

data class CategoryResponse(
    val categories: List<Category>
)

data class Category(
    val imageUrl: String,
    val title: String
)
