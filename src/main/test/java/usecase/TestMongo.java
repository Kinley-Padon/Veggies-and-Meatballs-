package java.usecase;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;

public class TestMongo {
    public static void main(String[] args) {
        String uri = "mongodb+srv://dechenz606:<password>@csc207-project.zfsuk.mongodb.net/myDatabase?retryWrites=true&w=majority";
        try (MongoClient client = MongoClients.create(uri)) {
            MongoDatabase database = client.getDatabase("myDatabase");
            System.out.println("Connected to database: " + database.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

