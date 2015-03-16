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
import javax.ws.rs.core.UriInfo;

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
        String sql = "Select * from login";

        Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        String name = rs.getString("username");
        String passwordDb = rs.getString("password");
        if (name.equals(username) && passwordDb.equals(password)) {
    
            return Response.ok().build();
        } else {
            return Response.status(500).build();
        }

    }
}
