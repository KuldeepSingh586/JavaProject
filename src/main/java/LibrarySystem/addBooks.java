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

        return Response.ok(resultMethod("SELECT * FROM login")).build();
        
    }
    @GET
    @Path("{id}")
    @Produces("application/json")
    public String doGet(@PathParam("id") String name) {
        String result = resultMethod("SELECT * FROM login where username=?", name);
        return result;

    }
    private String resultMethod(String query, String... params) {
        StringBuilder sb = new StringBuilder();
        String jsonString = "";
        try ( Connection conn = getConnection();) {
            PreparedStatement pstmt = conn.prepareStatement(query);
            for (int i = 1; i <= params.length; i++) {
                pstmt.setString(i, params[i - 1]);
            }
            ResultSet rs = pstmt.executeQuery();
            List l1 = new LinkedList();
            while (rs.next()) {
                //Refernce Example 5-2 - Combination of JSON primitives, Map and List
                //https://code.google.com/p/json-simple/wiki/EncodingExamples
                Map m1 = new LinkedHashMap();
                m1.put("uername", rs.getInt("username"));
                m1.put("password", rs.getString("password"));
               
                l1.add(m1);

            }

            jsonString = JSONValue.toJSONString(l1);
        } catch (SQLException ex) {
            System.err.println("SQL Exception Error: " + ex.getMessage());
        }
        return jsonString.replace("},", "},\n");
    }
}
