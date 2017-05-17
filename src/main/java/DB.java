// import org.sql2o.*;
//
// public class DB {
//   // public static Sql2o sql2o = new Sql2o("jdbc:postgresql://ec2-54-227-237-223.compute-1.amazonaws.com:5432/dbqrqqcvjjaj83",
//   // "pumisbmplytidq", "cc5a47325c7adc55663ad498e217f937079f06e4c72fc43c64a53a11f5bb9fb4");
//   public static Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/saloon", "alex", "password");
// }
import org.sql2o.*;
import java.net.URI;
import java.net.URISyntaxException;

public class DB {
    private static URI dbUri;
    public static Sql2o sql2o;

    static {

        try {
            if (System.getenv("DATABASE_URL") == null) {
                dbUri = new URI("postgres://localhost:5432/saloon");
            } else {
                dbUri = new URI(System.getenv("DATABASE_URL"));
            }

            int port = dbUri.getPort();
            String host = dbUri.getHost();
            String path = dbUri.getPath();
            String username = (dbUri.getUserInfo() == null) ? "alex" : dbUri.getUserInfo().split(":")[0];
            String password = (dbUri.getUserInfo() == null) ? "password" : dbUri.getUserInfo().split(":")[1];

            sql2o = new Sql2o("jdbc:postgresql://" + host + ":" + port + path, username, password);
        } catch (URISyntaxException e ) {
        }
    }
}
