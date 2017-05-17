import org.sql2o.*;

public class DB {
  // public static Sql2o sql2o = new Sql2o("jdbc:postgresql://ec2-54-227-237-223.compute-1.amazonaws.com:5432/dbqrqqcvjjaj83",
  // "pumisbmplytidq", "cc5a47325c7adc55663ad498e217f937079f06e4c72fc43c64a53a11f5bb9fb4");
  public static Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/saloon", "alex", "password");
}
