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
    Client firstClient = new Client("Judy", 1);
    Client secondClient = new Client("Judy", 1);
    assertTrue(firstClient.equals(secondClient));
  }

//  to save returns if the are true
  @Test
  public void save_returnsTrueIfDescriptionsAretheSame() {
    Client myClient = new Client("Judy" ,1);
    myClient.save();
    assertTrue(Client.all().get(0).equals(myClient));
  }

//  to return all instances as true
  @Test
  public void all_returnsAllInstancesOfClient_true() {
    Client firstClient = new Client("Judy", 1);
    firstClient.save();
    Client secondClient = new Client("Mercy", 1);
    secondClient.save();
    assertEquals(true, Client.all().get(0).equals(firstClient));
    assertEquals(true, Client.all().get(1).equals(secondClient));
  }

//  to assign and modify unique ids
  @Test
  public void save_assignsIdToObject() {
    Client myClient = new Client("Judy", 1);
    myClient.save();
    Client savedClient = Client.all().get(0);
    assertEquals(myClient.getId(), savedClient.getId());
  }

  @Test
  public void getId_clientInstantiateWithAnID() {
    Client myClient = new Client("Judy", 1);
    myClient.save();
    assertTrue(myClient.getId() > 0);
  }
  // end of assigning and modifying unique ids

// to find and check that the client has the correct id
  @Test
  public void find_returnsClientWithSameId_secondClient() {
    Client firstClient = new Client("Judy", 1);
    firstClient.save();
    Client secondClient = new Client("Mercy", 1);
    secondClient.save();
    assertEquals(Client.find(secondClient.getId()), secondClient);
  }
  // end
  // test used to enhance relationship between parent-class stylist and child-class client
@Test
    public void save_savesStylistIdIntoDB_true() {
      Stylist myStylist = new Stylist("Gal");
      myStylist.save();
      Client myClient = new Client("Judy", myStylist.getId());
      myClient.save();
      Client savedClient = Client.find(myClient.getId());
      assertEquals(savedClient.getStylistId(), myStylist.getId());
    }

// test used for updates
@Test
public void update_updatesClientDescription_true() {
  Client myClient = new Client("Judy", 1);
  myClient.save();
  myClient.update("Jackie");
  assertEquals("Jackie", Client.find(myClient.getId()).getDescription());
}

// test used to delete clients
@Test
public void delete_deletesClient_true() {
  Client myClient = new Client("Judy", 1);
  myClient.save();
  int myClientId = myClient.getId();
  myClient.delete();
  assertEquals(null, Client.find(myClientId));
}


 }
