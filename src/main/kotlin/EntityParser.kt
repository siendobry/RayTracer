import java.io.BufferedReader
import java.io.FileReader

class EntityParser {

    fun parse(filename : String) : EntityList {
        val entities = EntityList()
        try {
            val reader = BufferedReader(FileReader(filename))
            val line = reader.readLine()
            while (line != null) {
                entities.add(Entity.parse(line))
            }
            return entities
        }
        catch (ex : Exception) {
            throw IllegalArgumentException(ex.message)
        }
    }

}