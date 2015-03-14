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
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.json.simple.JSONValue;

/**
 *
 * @author Kuldeep
 */
@Path("/login")
public class addBooks {
   @GET
    @Produces("application/json")
    public Response doGet() {

        return Response.ok(getResults("SELECT * FROM login")).build();
        
    }
      public static JsonArray getResults(String sql, String... params) {
        JsonArray json = null;
        try {
            JsonArrayBuilder array = Json.createArrayBuilder();
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++)
                pstmt.setString(i+1, params[i]);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                array.add(Json.createObjectBuilder()
                    .add("username", rs.getInt("username"))
                    .add("password", rs.getString("password")));         
            }
            conn.close();
            json = array.build();
        } catch (SQLException ex) {
           
        }
        return json;
    }
}
