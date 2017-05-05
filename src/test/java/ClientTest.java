import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

//class client test
public class ClientTest {

//connect with the database
  @Before
  public void setUp() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/saloon_test", "alex", "password");
  }

  @After
 public void tearDown() {
   try(Connection con = DB.sql2o.open()) {
     String sql = "DELETE FROM clients *;";
     con.createQuery(sql).executeUpdate();
   }
}
// end connectivity

//  to show whether the descriptions which are the same are returned in  true
  @Test
  public void equals_returnsTrueIfDescriptionsAretheSame() {
    Client firstClient = new Client("Judy");
    Client secondClient = new Client("Judy");
    assertTrue(firstClient.equals(secondClient));
  }

//  to save returns if the are true
  @Test
  public void save_returnsTrueIfDescriptionsAretheSame() {
    Client myClient = new Client("Judy");
    myClient.save();
    assertTrue(Client.all().get(0).equals(myClient));
  }

//  to return all instances as true
  @Test
  public void all_returnsAllInstancesOfClient_true() {
    Client firstClient = new Client("Judy");
    firstClient.save();
    Client secondClient = new Client("Mercy");
    secondClient.save();
    assertEquals(true, Client.all().get(0).equals(firstClient));
    assertEquals(true, Client.all().get(1).equals(secondClient));
  }

//  to assign and modify unique ids
  @Test
  public void save_assignsIdToObject() {
    Client myClient = new Client("Judy");
    myClient.save();
    Client savedClient = Client.all().get(0);
    assertEquals(myClient.getId(), savedClient.getId());
  }

  @Test
  public void getId_clientInstantiateWithAnID() {
    Client myClient = new Client("Judy");
    myClient.save();
    assertTrue(myClient.getId() > 0);
  }
  // end of assigning and modifying unique ids

// to find and check that the client has the correct id
  @Test
  public void find_returnsClientWithSameId_secondClient() {
    Client firstClient = new Client("Judy");
    firstClient.save();
    Client secondClient = new Client("Mercy");
    secondClient.save();
    assertEquals(Client.find(secondClient.getId()), secondClient);
  }
  // end
 }
