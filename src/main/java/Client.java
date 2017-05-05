// imports
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

// class client and its attributes and access modifiers
public class Client {
  private String description;
  private boolean completed;
  private LocalDateTime createdAt;
  private int id;

// constructor client
  public Client(String description) {
    this.description = description;
    completed = true;
    createdAt = LocalDateTime.now();
  }

//to get description of the client
  public String getDescription() {
      return description;
    }

//  to check if the client has been serviced
    public boolean isCompleted() {
      return completed;
    }

//  to show the exact time the service has been done to the client
    public LocalDateTime getCreatedAt() {
      return createdAt;
    }

//to get ids of the clients
    public int getId() {
      return id;
    }

// to enhance the id is found and used within the class
    public static Client find(int id) {
      try(Connection con = DB.sql2o.open()) {
        String sql = "SELECT * FROM clients where id=:id";
        Client client = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Client.class);
        return client;
      }
    }

// list to hold the clients details and be connected and stored to the database
    public static List<Client> all() {
      String sql = "SELECT id, description FROM clients";
      try(Connection con = DB.sql2o.open()) {
        return con.createQuery(sql).executeAndFetch(Client.class);
      }
    }

// to override the methods to prevent data redundancy
    @Override
    public boolean equals(Object otherClient) {
      if (!(otherClient instanceof Client)) {
        return false;
      } else {
        Client newClient = (Client) otherClient;
        return this.getDescription().equals(newClient.getDescription()) &&
             this.getId() == newClient.getId();
      }
    }

// save method for storing our client data
    public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO clients (description) VALUES (:description)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("description", this.description)
        .executeUpdate()
        .getKey();
    }
  }
}
