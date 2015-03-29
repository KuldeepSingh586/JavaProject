/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LibrarySystem;

import static databaseCredentials.database.getConnection;
import java.net.URI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Kuldeep
 */
@Path("/user")
public class Login {

    @POST
    @Path("/add")
    public URI addUser(@FormParam("username") String username,
            @FormParam("password") String password) throws SQLException {
        String sql = "Select * from login WHERE username = ? AND password = ?";

        Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, username);
        pstmt.setString(2, password);
        ResultSet rs = pstmt.executeQuery();
        rs.next();
        String name = rs.getString("username");
        String passwordDb = rs.getString("password");
        if (name.equals(username) && passwordDb.equals(password)) 
    
        {   
             return UriBuilder.fromUri("librarymgmtsys-javaprojkuldeep.rhcloud.com/welcome.html").build();
        // return Response.ok().build();
        }
        return UriBuilder.fromUri("librarymgmtsys-javaprojkuldeep.rhcloud.com").uri("/welcome.html").build();
    }
}
