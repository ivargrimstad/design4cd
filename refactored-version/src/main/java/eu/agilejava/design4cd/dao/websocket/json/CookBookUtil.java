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
package eu.agilejava.design4cd.dao.websocket.json;

import eu.agilejava.design4cd.entities.CookBook;
import eu.agilejava.design4cd.entities.CookBookUser;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import static eu.agilejava.design4cd.dao.websocket.json.CookBookUserUtil.*;
import javax.json.JsonReader;

/**
 * JSON Utilities for CookBook.
 * 
 * @author Ivar Grimstad <ivar.grimstad@gmail.com>
 */
public final class CookBookUtil {

   private CookBookUtil() {
   }

   /**
    * Create a list of CookBook from JSON string.
    * 
    * @param message The JSON string
    * @return A list of cookbooks
    */
   public static List<CookBook> cookBookListFromJson(String message) {
      List<CookBook> returnValue = new ArrayList<>();

      if (message != null) {

         try (JsonReader reader = Json.createReader(new StringReader(message))) {
            JsonArray array = reader.readArray();

            for (int i = 0; i < array.size(); i++) {
               JsonObject model = array.getJsonObject(i);
               returnValue.add(cookBookFromJsonObject(model));
            }
         }
      }

      return returnValue;
   }

   /**
    * Create CookBook from JSON object.
    * 
    * @param model The JSON object
    * @return A cookbook
    */
   public static CookBook cookBookFromJsonObject(JsonObject model) {

      CookBook cookBook = new CookBook();
      if (model.containsKey("id")) {
         cookBook.setId(Long.valueOf((long) model.getInt("id")));
      }
      cookBook.setName(model.getString("name"));

      JsonObject ownerModel = model.getJsonObject("owner");
      CookBookUser owner = new CookBookUser();
      owner.setId(Long.valueOf((long) ownerModel.getInt("id")));
      owner.setFirstName(ownerModel.getString("firstName"));
      owner.setLastName(ownerModel.getString("lastName"));
      owner.setEmail(ownerModel.getString("email"));
      owner.setPassword(ownerModel.getString("password"));

      cookBook.setOwner(owner);

      return cookBook;
   }

   /**
    * Create CookBook from JSON string.
    * 
    * @param message The JSON string
    * @return A cookbook
    */
   public static CookBook cookBookFromJson(String message) {
      try (JsonReader reader = Json.createReader(new StringReader(message))) {
         return cookBookFromJsonObject(reader.readObject());
      }
   }

   /**
    * Create JSON object from CookBook.
    * 
    * @param cookBook The cookbook
    * @return A JSON object
    */
   public static JsonObject createCookBookJson(CookBook cookBook) {
      return cookBookBuilder(cookBook).build();
   }

   /**
    * Create JSON object builder from CookBook.
    * 
    * @param cookBook The cookbook
    * @return The JSON object builder
    */
   public static JsonObjectBuilder cookBookBuilder(CookBook cookBook) {

      JsonObjectBuilder builder = Json.createObjectBuilder()
              .add("name", cookBook.getName())
              .add("owner", cookBookUserBuilder(cookBook.getOwner()));

      if (cookBook.getId() != null) {
         builder.add("id", cookBook.getId());
      }

      return builder;
   }
}