package hu.bme.aut.penzjegyzo.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.util.*

@Entity(tableName = "bejegyzes")
data class Bejegyzes(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id: Long? = null,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "category") var category: Category,
    @ColumnInfo(name = "pay_method") var pay_method: Method,
    @ColumnInfo(name = "value") var value: Int,
    @ColumnInfo(name = "date") var date : String
) {
    enum class Category {
        BEVÉTEL,KIADÁS;
        companion object {
            @JvmStatic
            @TypeConverter
            fun getByOrdinal(ordinal: Int): Category? {
                var ret: Category? = null
                for (cat in values()) {
                    if (cat.ordinal == ordinal) {
                        ret = cat
                        break
                    }
                }
                return ret
            }

            @JvmStatic
            @TypeConverter
            fun toInt(category: Category): Int {
                return category.ordinal
            }
        }
    }
    enum class Method {
        KÁRTYA,KÉSZPÉNZ;
        companion object {
            @JvmStatic
            @TypeConverter
            fun getByOrdinal(ordinal: Int): Method? {
                var ret: Method? = null
                for (cat in values()) {
                    if (cat.ordinal == ordinal) {
                        ret = cat
                        break
                    }
                }
                return ret
            }

            @JvmStatic
            @TypeConverter
            fun toInt(method: Method): Int {
                return method.ordinal
            }
        }
    }
}
