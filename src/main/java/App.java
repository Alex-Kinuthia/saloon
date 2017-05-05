// imports
import java.util.HashMap;
import java.util.Map;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";
    ProcessBuilder process = new ProcessBuilder();
    Integer port;
    if (process.environment().get("PORT") !=null) {
      port = Integer.parseInt(process.environment().get("PORT"));
    } else {
      port = 4567;
    }

    setPort(port);

 // creating a root route in App.java file that will render our home page
    get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("stylists", Stylist.all());
            model.put("template", "templates/index.vtl");
            return new ModelAndView(model, layout);
          }, new VelocityTemplateEngine());


          // it is responsible for rendering the template with the new-client form
        get("/clients/new", (request, response) -> {
          Map<String, Object> model = new HashMap<String, Object>();
          model.put("template", "templates/client-form.vtl");
            return new ModelAndView(model, layout);
            }, new VelocityTemplateEngine());

            // route to display all clients
                    get("/clients", (request, response) -> {
                      Map<String, Object> model = new HashMap<String, Object>();
                      model.put("clients", Client.all());
                      model.put("template", "templates/clients.vtl");
                      return new ModelAndView(model, layout);
                    }, new VelocityTemplateEngine());

                    // linking the client with stylist
                            get("/stylists/:stylist_id/clients/:id", (request, response) -> {
                                Map<String, Object> model = new HashMap<String, Object>();
                                Stylist stylist = Stylist.find(Integer.parseInt(request.params(":stylist_id")));
                                Client client = Client.find(Integer.parseInt(request.params(":id")));
                                model.put("stylist", stylist);
                                model.put("client", client);
                                model.put("template", "templates/client.vtl");
                                return new ModelAndView(model, layout);
                              }, new VelocityTemplateEngine());

                      //  adding new stylists
                              get("/stylists/new", (request, response) -> {
                                Map<String, Object> model = new HashMap<String, Object>();
                                model.put("template", "templates/stylist-form.vtl");
                                return new ModelAndView(model, layout);
                                }, new VelocityTemplateEngine());

                      // displaying the stylists
                                get("/stylists", (request, response) -> {
                                  Map<String, Object> model = new HashMap<String, Object>();
                                  model.put("stylists", Stylist.all());
                                  model.put("template", "templates/stylists.vtl");
                                  return new ModelAndView(model, layout);
                                }, new VelocityTemplateEngine());


                                 // routing and a basic template setup
                                        get("/stylists/:id", (request, response) -> {
                                          Map<String, Object> model = new HashMap<String, Object>();
                                          Stylist stylist = Stylist.find(Integer.parseInt(request.params(":id")));
                                          model.put("stylist", stylist);
                                          model.put("template", "templates/stylist.vtl");
                                          return new ModelAndView(model, layout);
                                        }, new VelocityTemplateEngine());

// relating stylist to the clients
        get("stylists/:id/clients/new", (request, response) -> {
          Map<String, Object> model = new HashMap<String, Object>();
          Stylist stylist = Stylist.find(Integer.parseInt(request.params(":id")));
          model.put("stylist", stylist);
          model.put("template", "templates/stylist-clients-form.vtl");
          return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

// showing success after  adding client
        post("/clients", (request,response) -> {
           Map<String, Object> model = new HashMap<String, Object>();
           String description = request.queryParams("description");
           Stylist stylist = Stylist.find(Integer.parseInt(request.queryParams("stylistId")));
           Client newClient = new Client(description, stylist.getId());
           model.put("template", "templates/success.vtl");
           return new ModelAndView(model, layout);
         }, new VelocityTemplateEngine());


// indicating stylist has already  offered the service
        post("/stylists", (request, response) -> {
                Map<String, Object> model = new HashMap<String, Object>();
                String description = request.queryParams("description");
                Stylist newStylist = new Stylist(name);
                newStylist.save();
                model.put("template", "templates/stylist-success.vtl");
                return new ModelAndView(model, layout);
              }, new VelocityTemplateEngine());


// a route to process new-client form submission
        post("/clients", (request, response) -> {
              Map<String, Object> model = new HashMap<String, Object>();
              Stylist stylist = Stylist.find(Integer.parseInt(request.queryParams("stylistId")));
              String description = request.queryParams("description");
              Client newClient = new Client(description, stylist.getId());
              newClient.save();
              model.put("stylist", stylist);
              model.put("template", "templates/stylist-client-success.vtl");
              return new ModelAndView(model, layout);
            }, new VelocityTemplateEngine());


// enhancing particular clients belong to a particular stylist
          post("/stylists/:stylist_id/clients/:id", (request, response) -> {
          Map<String, Object> model = new HashMap<String, Object>();
          Clients clients = Clients.find(Integer.parseInt(request.params("id")));
          String description = request.queryParams("description");
          Stylist stylist = Stylist.find(client.getStylistId());
          client.update(description);
          String url = String.format("/stylists/%d/clients/%d", stylist.getId(), client.getId());
          response.redirect(url);
          return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

// enabling the deletion of clients
        post("/stylists/:stylist_id/clients/:id/delete", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Client client = Client.find(Integer.parseInt(request.params("id")));
      Stylist stylist = Stylist.find(stylist.getStylistId());
      client.delete();
      model.put("stylist", stylist);
      model.put("template", "templates/stylist.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
      }
    }
