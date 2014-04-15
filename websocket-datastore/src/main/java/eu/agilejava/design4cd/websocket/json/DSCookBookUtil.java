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

import eu.agilejava.design4cd.entities.CookBook;
import java.io.StringReader;
import java.util.List;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import static eu.agilejava.design4cd.websocket.json.DSCookBookUserUtil.*;
import javax.json.JsonArrayBuilder;
import javax.json.JsonReader;

/**
 * JSON Utilities for CookBook.
 *
 * @author Ivar Grimstad <ivar.grimstad@gmail.com>
 */
public final class DSCookBookUtil {

   private DSCookBookUtil() {
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
      cookBook.setOwner(cookBookUserFromJsonObject(model.getJsonObject("owner")));

      return cookBook;
   }

   /**
    * Create JSON string from CookBook.
    *
    * @param cookBook The cookbook
    * @return A JSON string representing a cookbook
    */
   public static String createCookBookJson(CookBook cookBook) {
      return cookBookBuilder(cookBook).build().toString();
   }

   /**
    * Create JSON string from list of CookBooks.
    *
    * @param cookBooks The cookbooks
    * @return JSON string representing list of cookbooks
    */
   public static String createCookBookListJson(List<CookBook> cookBooks) {

      JsonArrayBuilder builder = Json.createArrayBuilder();

      for (CookBook cookBook : cookBooks) {
         builder.add(cookBookBuilder(cookBook));
      }

      return builder.build().toString();
   }

   /**
    * Create JSON object builder from CookBook.
    *
    * @param cookBook The cookbook
    * @return A JSON object builder
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
