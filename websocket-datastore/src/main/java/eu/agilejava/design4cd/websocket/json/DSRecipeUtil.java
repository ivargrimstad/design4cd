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

import eu.agilejava.design4cd.entities.Recipe;
import java.io.StringReader;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;

import static eu.agilejava.design4cd.websocket.json.DSCookBookUtil.*;

/**
 * JSON Utilities for Recipe.
 * 
 * @author Ivar Grimstad <ivar.grimstad@gmail.com>
 */
public final class DSRecipeUtil {

   private DSRecipeUtil() {
   }

   /**
    * Create a Recipe from JSON string.
    * 
    * @param message The JSON string
    * @return A recipe
    */
   public static Recipe recipeFromJson(String message) {
      try (JsonReader reader = Json.createReader(new StringReader(message))) {

         JsonObject model = reader.readObject();

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
   }

   /**
    * Create JSON string from Recipe.
    * 
    * @param recipe The recipe
    * @return A JSON string representing a recipe
    */
   public static String createRecipeJson(Recipe recipe) {
      return recipeBuilder(recipe).build().toString();
   }

   /**
    * Create JSON string from list of Recipes.
    * 
    * @param recipes The recipe list
    * @return A JSON string representing a list of recipes
    */
   public static String createRecipeListJson(List<Recipe> recipes) {

      JsonArrayBuilder builder = Json.createArrayBuilder();

      for (Recipe recipe : recipes) {
         builder.add(recipeBuilder(recipe));
      }

      return builder.build().toString();
   }

   /**
    * Create a JSON object builder from Recipe.
    * 
    * @param recipe The recipe
    * @return The JSON object builder
    */
   private static JsonObjectBuilder recipeBuilder(Recipe recipe) {

      JsonObjectBuilder builder = Json.createObjectBuilder()
              .add("id", recipe.getId())
              .add("title", recipe.getTitle())
              .add("description", recipe.getDescription())
              .add("ingredients", recipe.getIngredients())
              .add("instructions", recipe.getInstructions())
              .add("cookbook", cookBookBuilder(recipe.getCookBook()));

      return builder;
   }
}
