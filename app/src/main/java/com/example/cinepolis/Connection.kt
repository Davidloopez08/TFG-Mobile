import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import org.bson.Document

class Connection {
    private val mongoClient = MongoClients.create("mongodb://10.0.2.2:27017")
    private val database: MongoDatabase = mongoClient.getDatabase("CINEPOLIS")

    fun getPeliculasCollection(): MongoCollection<Document> {
        return database.getCollection("peliculas")
    }

}
