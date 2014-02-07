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
package eu.agilejava.design4cd.dao.websocket;

import eu.agilejava.design4cd.dao.RecipeDao;
import static eu.agilejava.design4cd.dao.websocket.json.RecipeUtil.*;
import eu.agilejava.design4cd.entities.Recipe;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.json.JsonObject;

/**
 * WebSocket implementation of Recipe Dao.
 * @see RecipeDao
 * @author Ivar Grimstad <ivar.grimstad@gmail.com>
 */
@Dependent
public class RecipeWebSocketDao implements RecipeDao {

   private static final long serialVersionUID = 15464814542656L;
   
   private static final Logger LOG = Logger.getLogger(RecipeWebSocketDao.class.getName());
   private static final String ENDPOINT = "/recipe";
   private static final String COUNT_ENDPOINT = ENDPOINT + "/count/0";
   private static final String CREATE_ENDPOINT = ENDPOINT + "/create/0";
   private static final String EDIT_ENDPOINT = ENDPOINT + "/edit/";
   private static final String FIND_ENDPOINT = ENDPOINT + "/find/";
   private static final String FINDALL_ENDPOINT = ENDPOINT + "/findall/0";
   private static final String REMOVE_ENDPOINT = ENDPOINT + "/remove/";

   @Inject
   private WebSocketDatastoreClient wsClient;

   @Override
   public int count() {
      LOG.log(Level.CONFIG, "WebSocket count recipes");

      final String sessionId = wsClient.sendMessage(COUNT_ENDPOINT, null);
      return Integer.parseInt(wsClient.getMessage(sessionId) != null ? wsClient.getMessage(sessionId) : "-1");
   }

   @Override
   public void create(Recipe recipe) {
      LOG.log(Level.CONFIG, "WebSocket create recipe");

      JsonObject model = createRecipeJson(recipe);

      final String sessionId = wsClient.sendMessage(CREATE_ENDPOINT, model.toString());

      recipe = recipeFromJson(wsClient.getMessage(sessionId));
   }

   @Override
   public void edit(Recipe recipe) {
      LOG.log(Level.CONFIG, "WebSocket edit recipe {0}", recipe.getId());

      JsonObject model = createRecipeJson(recipe);

      final String sessionId = wsClient.sendMessage(EDIT_ENDPOINT + recipe.getId(), model.toString());
      recipe = recipeFromJson(wsClient.getMessage(sessionId));
   }

   @Override
   public Recipe find(Object id) {
      LOG.log(Level.CONFIG, "WebSocket find recipe {0}", id);
      final String sessionId = wsClient.sendMessage(FIND_ENDPOINT + id, null);

      return recipeFromJson(wsClient.getMessage(sessionId));
   }

   @Override
   public List<Recipe> findAll() {
      LOG.log(Level.CONFIG, "WebSocket find all recipes");
      final String sessionId = wsClient.sendMessage(FINDALL_ENDPOINT, null);
      return recipeListFromJson(wsClient.getMessage(sessionId));
   }

   @Override
   public void remove(Recipe recipe) {
      LOG.log(Level.CONFIG, "WebSocket remove recipe {0}", recipe.getId());

      JsonObject model = createRecipeJson(recipe);

      final String sessionId = wsClient.sendMessage(REMOVE_ENDPOINT + recipe.getId(), model.toString());
   }
}
