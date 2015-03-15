/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LibrarySystem;

import static databaseCredentials.database.getConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 *
 * @author Kuldeep
 */
@Path("/user")
public class Login {
    @POST
    @Path("/add")
    public Response addUser(@FormParam("username") String username,
            @FormParam("password") String password) throws SQLException {
        String sql="Select * from login";
        
        Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery();
              String name=rs.getString("username");
              String passwordDb=rs.getString("password");
              if(name.equals(username)&&passwordDb.equals(password)) {
                  Response.status(200);
              }
        return Response.status(200)
                .entity("addUser is called, name : " + username + ", age : " + password)
                .build();
       
    }
}
