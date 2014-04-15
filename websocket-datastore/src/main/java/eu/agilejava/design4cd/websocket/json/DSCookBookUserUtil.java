/*
 * The MIT License
 *
 * Copyright 2013 Ivar Grimstad <ivar.grimstad@gmail.com>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package eu.agilejava.design4cd.websocket.json;

import eu.agilejava.design4cd.entities.CookBookUser;
import javax.json.Json;
import javax.json.JsonObjectBuilder;

import static eu.agilejava.design4cd.websocket.json.DSCookBookUserUtil.*;
import java.io.StringReader;
import java.util.List;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonReader;

/**
 * JSON Utilities for CookBookUser.
 * 
 * @author Ivar Grimstad <ivar.grimstad@gmail.com>
 */
public final class DSCookBookUserUtil {

   private DSCookBookUserUtil() {
   }

   /**
    * Create JSON object builder from CookBookUser.
    * 
    * @param user The user
    * @return A JSON object builder
    */
   public static JsonObjectBuilder cookBookUserBuilder(CookBookUser user) {
      return Json.createObjectBuilder()
              .add("id", user.getId())
              .add("firstName", user.getFirstName())
              .add("lastName", user.getLastName())
              .add("email", user.getEmail())
              .add("password", user.getPassword());
   }

   /**
    * Create CookBookUser from JSON string.
    * 
    * @param message The JSON string
    * @return A user
    */
   public static CookBookUser cookBookUserFromJson(String message) {

      try (JsonReader reader = Json.createReader(new StringReader(message))) {
         return cookBookUserFromJsonObject(reader.readObject());
      }
   }

   /**
    * Create CookBookUser from JSON object.
    * 
    * @param model The JSON object
    * @return A user
    */
   public static CookBookUser cookBookUserFromJsonObject(JsonObject model) {
         CookBookUser user = new CookBookUser();
         if (model.containsKey("id")) {
            user.setId(Long.valueOf((long) model.getInt("id")));
         }
         user.setFirstName(model.getString("firstName"));
         user.setLastName(model.getString("lastName"));
         user.setEmail(model.getString("email"));
         user.setPassword(model.getString("password"));

         return user;
   }
   
   /**
    * Create JSON string from CookBookUser.
    * 
    * @param user The user
    * @return A JSON string
    */
   public static String createCookBookUserJson(CookBookUser user) {
      return cookBookUserBuilder(user).build().toString();
   }

   /**
    * Create JSON string from list of CookBookUsers.
    * 
    * @param users The user list
    * @return A JSON string representing list of users
    */
   public static String createCookBookUserListJson(List<CookBookUser> users) {

      JsonArrayBuilder builder = Json.createArrayBuilder();

      for (CookBookUser user : users) {
         builder.add(cookBookUserBuilder(user));
      }

      return builder.build().toString();
   }
}
