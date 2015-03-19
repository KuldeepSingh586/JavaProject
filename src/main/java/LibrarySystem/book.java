/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LibrarySystem;

import static databaseCredentials.database.getConnection;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.stream.JsonParser;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
     * doPost Method takes one parameter of type String. Used to Insert the
     * values into Product table. get the name, description, quantity by using
     * HashMap call doUpdate Method
     *
     * @param strValue
     */
    @POST
    @Consumes("application/json")
    public void doPost(String strValue) {
        JsonParser jsonParserObj = Json.createParser(new StringReader(strValue));
        Map<String, String> map = new HashMap<>();
        String name = "", value;
        while (jsonParserObj.hasNext()) {
            JsonParser.Event event = jsonParserObj.next();
            switch (event) {
                case KEY_NAME:
                    name = jsonParserObj.getString();
                    break;
                case VALUE_STRING:
                    value = jsonParserObj.getString();
                    map.put(name, value);
                    break;
                case VALUE_NUMBER:
                    value = Integer.toString(jsonParserObj.getInt());
                    map.put(name, value);
                    break;
            }

        }
        System.out.println(map);
        String getName = map.get("name");
        String getbookcode = map.get("bookcode");
        String getauthor = map.get("author");
        String getarrivaldate= map.get("arrivaldate");
        String getQuantity = map.get("quantity");
        String getlocation = map.get("location_rack");
        doUpdate("INSERT INTO book (name, bookcode, author,arrivaldate, quantity, location_rack ) "
                + "VALUES (?, ?, ?, ?, ?, ?)", getName, getbookcode,getauthor,getarrivaldate, getQuantity,getlocation);

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
                        .add("arrivaldate", rs.getString("arrivaldate"))
                         .add("quantity", rs.getInt("quantity"))
                        .add("location_rack", rs.getString("location_rack")).build();

                jsonArrayObj.add(json);
            }
        } catch (SQLException ex) {
            System.err.println("SQL Exception Error: " + ex.getMessage());
        }

        strJson = jsonArrayObj.build().toString();
        return strJson;
    }

      /**
     * doUpdate Method accepts two arguments Update the entries in the table
     * 'product'
     *
     * @param query
     * @param params
     * @return numChanges
     */
    private int doUpdate(String query, String... params) {
        int numChanges = 0;
        try (Connection conn = getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(query);
            for (int i = 1; i <= params.length; i++) {
                pstmt.setString(i, params[i - 1]);
            }
            numChanges = pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("SQL EXception in doUpdate Method" + ex.getMessage());
        }
        return numChanges;
    }
}
