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
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 *
 * @author Kuldeep
 */
@Path("book")
public class book {
    
    /**
     * doGet method select all attribute from product table. call resultMethod()
     * pass all product table data in resultMethod(). store all table data in
     * String result variable
     *
     * @return result
     */
    @GET
    @Produces("application/json")
    public String doGet() {
        String books=resultMethod("SELECT * FROM book");

        return books;
    }

     /**
     * resultMethod accepts two arguments It executes the Query get ProductID,
     * name, description, quantity. Used JSON object model and provides methods
     * to add name/value pairs to the object model and to return the resulting
     * object
     *
     * @param query
     * @param params
     * @throws SQLException
     * @return
     */
    private String resultMethod(String query, String... params) {
        String strJson = "";
        JsonArrayBuilder jsonArrayObj = Json.createArrayBuilder();
        try (Connection conn = getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(query);
            for (int i = 1; i <= params.length; i++) {
                pstmt.setString(i, params[i - 1]);
            }
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                JsonObject json = Json.createObjectBuilder()
                        .add("name", rs.getString("name"))
                        .add("bookcode", rs.getString("bookcode"))
                        .add("author", rs.getString("author"))
                        .add("arrivaldate", rs.getInt("arrivaldate"))
                         .add("quantity", rs.getInt("quantity"))
                        .add("location_rack", rs.getInt("location_rack")).build();

                jsonArrayObj.add(json);
            }
        } catch (SQLException ex) {
            System.err.println("SQL Exception Error: " + ex.getMessage());
        }

        strJson = jsonArrayObj.build().toString();
        return strJson;
    }

    
}
