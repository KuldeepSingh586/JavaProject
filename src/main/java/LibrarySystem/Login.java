/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LibrarySystem;

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
    public Response login(
    @FormParam("username") String username,
     @FormParam("password")String password) {
        return Response.status(200).entity("username is:"+username+",password"+password).build();
    }
}
