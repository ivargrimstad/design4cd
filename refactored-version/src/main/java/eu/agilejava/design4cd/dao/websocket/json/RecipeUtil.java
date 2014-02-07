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

import eu.agilejava.design4cd.entities.Recipe;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;

import static eu.agilejava.design4cd.dao.websocket.json.CookBookUtil.*;

/**
 * JSON Utilities for Recipe.
 *
 * @author Ivar Grimstad <ivar.grimstad@gmail.com>
 */
public class RecipeUtil {

   private RecipeUtil() {
   }

   /**
    * Create list of Recipe from JSON string.
    * 
    * @param message The JSON string
    * @return A recipe list
    */
   public static List<Recipe> recipeListFromJson(String message) {
      List<Recipe> returnValue = new ArrayList<>();

      if (message != null) {

         try (JsonReader reader = Json.createReader(new StringReader(message))) {
            JsonArray array = reader.readArray();

            for (int i = 0; i < array.size(); i++) {
               JsonObject model = array.getJsonObject(i);
               returnValue.add(recipeFromJsonObject(model));
            }
         }
      }

      return returnValue;
   }

   /**
    * Create Recipe from JSON object.
    * 
    * @param model The JSON object
    * @return A recipe
    */
   public static Recipe recipeFromJsonObject(JsonObject model) {

      Recipe recipe = new Recipe();
      if (model.containsKey("id")) {
         recipe.setId(Long.valueOf((long) model.getInt("id")));
      }
      recipe.setTitle(model.getString("title"));
      recipe.setDescription(model.getString("description"));
      recipe.setIngredients(model.getString("ingredients"));
      recipe.setInstructions(model.getString("instructions"));
      recipe.setCookBook(cookBookFromJsonObject(model.getJsonObject("cookbook")));

      return recipe;
   }

   /**
    * Create Recipe from JSON string.
    * 
    * @param message The JSON string
    * @return A recipe
    */
   public static Recipe recipeFromJson(String message) {
      try (JsonReader reader = Json.createReader(new StringReader(message))) {
         return recipeFromJsonObject(reader.readObject());
      }
   }

   /**
    * Create JSON object from Recipe.
    * 
    * @param recipe The recipe
    * @return A JSON object
    */
   public static JsonObject createRecipeJson(Recipe recipe) {
      JsonObjectBuilder builder = Json.createObjectBuilder()
              .add("title", recipe.getTitle())
              .add("description", recipe.getDescription())
              .add("ingredients", recipe.getIngredients())
              .add("instructions", recipe.getInstructions())
              .add("cookbook", cookBookBuilder(recipe.getCookBook()));

      if( recipe.getId() != null ) {
         builder.add("id", recipe.getId());
      }
      
      return builder.build();
   }
}
