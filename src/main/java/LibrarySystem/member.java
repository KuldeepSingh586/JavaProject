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
import javax.json.JsonObjectBuilder;
import javax.json.stream.JsonParser;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author Kuldeep
 */
@Path("member")
public class member {

    /**
     * doGet method select all attribute from members table. call resultMethod()
     * pass all members table data in resultMethod(). store all table data in
     * String result variable
     *
     * @return result
     */
    @GET
    @Produces("application/json")
    public String doGet() {

        String result = resultMethod("SELECT * FROM members");
        return result;

    }

    /**
     * doGet Method takes one parameter of type of string pass all members table
     * data in resultMethod(). store all table data in String result variable
     *
     * @param id
     * @return result
     */
    @GET
    @Path("{id}")
    @Produces("application/json")
    public String doGet(@PathParam("id") String id) {
        String result = resultMethod("SELECT * FROM members where id=?", id);
        return result;

    }

    /**
     * doPost Method takes one parameter of type String. Used to Insert the
     * values into Product table. get the name, address, amount by using HashMap
     * call doUpdate Method
     *
     * @param strValue
     */
    @POST
    @Consumes("application/json")
    public void doPost(String strValue) {
        int valueRow = 0;
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
        String getAddress = map.get("address");
        String getDateIssue = map.get("issuedate");
        String getDateDeadline = map.get("deadlinedate");
        String getAmount = map.get("amount");
        doUpdate("INSERT INTO  `members` (  `name` ,  `address` ,  `issuedate` ,  `deadlinedate` ,  `amount` )  "
                + "VALUES (   ?,  ?,  ?,  ?, ? )"
                , getName, getAddress, getDateIssue, getDateDeadline, getAmount);

    }

    /**
     * doPut Method takes two parameters of type string Used to Insert the
     * values into Product table. get the name, address, amount by using HashMap
     *
     * @param id
     * @param strValue
     */
    @PUT
    @Path("{id}")
    @Consumes("application/json")
    public void doPut(@PathParam("id") String id, String strValue) {
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
        String getAddress = map.get("address");
        String getDateIssue = map.get("issuedate");
        String getDateDeadline = map.get("deadlinedate");
        String getAmount = map.get("amount");
        doUpdate("update members set id = ?, name = ?, address = ?, issuedate = ?, deadlinedate = ?, amount=? where id = ?", id, getName, getAddress, getDateIssue, getDateDeadline, getAmount, id);
    }

    /**
     * doDelete takes one parameter of type String. Used to delete the values
     * into Product table. get the name, address, amount by using Simple Json
     * Library
     *
     * @param id
     * @param strValue
     */
    @DELETE
    @Path("{id}")
    @Consumes("application/json")
    public void doDelete(@PathParam("id") String id, String strValue) {
        doUpdate("DELETE FROM members WHERE `id`=?", id);
    }

    /**
     * resultMethod accepts two arguments It executes the Query get ProductID,
     * name, address, amount. Used JSON object model and provides methods to add
     * name/value pairs to the object model and to return the resulting object
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
                JsonObjectBuilder json = Json.createObjectBuilder()
                        .add("member id", rs.getInt("id"))
                        .add("name", rs.getString("name"))
                        .add("address", rs.getString("address"))
                        .add("issuedate", rs.getString("issuedate"))
                        .add("deadlinedate", rs.getString("deadlinedate"))
                        .add("amount", rs.getInt("amount"));

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
     * 'members'
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
