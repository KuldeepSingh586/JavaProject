/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LibrarySystem;

import static databaseCredentials.database.getConnection;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 *
 * @author Kuldeep
 */
@Path("/book")
public class addBooks {
   @GET
    @Produces("application/json")
    public Response doGet() {

        return Response.ok(getResults("SELECT * FROM books")).build();
        
    }
      public static JsonArray getResults(String sql, String... params) {
        JsonArray json = null;
        try {
            JsonArrayBuilder array = Json.createArrayBuilder();
            try (Connection conn = getConnection()) {
                PreparedStatement pstmt = conn.prepareStatement(sql);
                for (int i = 0; i < params.length; i++)
                    pstmt.setString(i+1, params[i]);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    array.add(Json.createObjectBuilder()
                            .add("username", rs.getString("username"))
                            .add("password", rs.getString("password")));         
                }
            }
            json = array.build();
        } catch (SQLException ex) {
           
        }
        return json;
    }
}
