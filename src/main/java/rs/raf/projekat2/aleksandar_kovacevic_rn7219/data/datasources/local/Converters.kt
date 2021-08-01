package rs.raf.projekat2.aleksandar_kovacevic_rn7219.data.datasources.local

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun toList(ids: String): List<String> {
        return ids.split(";")
    }

    @TypeConverter
    fun fromList(listIds: List<String>): String {
        var ids = ""
        listIds.forEach { ids += ";$it" }
        return ids
    }
}
