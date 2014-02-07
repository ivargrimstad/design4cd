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

import eu.agilejava.design4cd.dao.CookBookDao;
import eu.agilejava.design4cd.entities.CookBook;
import java.util.List;
import javax.inject.Inject;
import javax.json.JsonObject;

import static eu.agilejava.design4cd.dao.websocket.json.CookBookUtil.*;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.Dependent;

/**
 * WebSocket implementation of CookBookDao.
 * @see CookBookDao
 * @author Ivar Grimstad <ivar.grimstad@gmail.com>
 */
@Dependent
public class CookBookWebSocketDao implements CookBookDao {

   private static final long serialVersionUID = 15464814542256L;
   
   private static final Logger LOG = Logger.getLogger(CookBookWebSocketDao.class.getName());
   private static final String ENDPOINT = "/cookbook";
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
      LOG.log(Level.CONFIG, "WebSocket count cookbooks");

      final String sessionId = wsClient.sendMessage(COUNT_ENDPOINT, null);
      return Integer.parseInt(wsClient.getMessage(sessionId) != null ? wsClient.getMessage(sessionId) : "-1");
   }

   @Override
   public void create(CookBook cookBook) {
      LOG.log(Level.CONFIG, "WebSocket create cookbook");

      JsonObject model = createCookBookJson(cookBook);

      final String sessionId = wsClient.sendMessage(CREATE_ENDPOINT, model.toString());

      cookBook = cookBookFromJson(wsClient.getMessage(sessionId));
   }

   @Override
   public void edit(CookBook cookBook) {
      LOG.log(Level.CONFIG, "WebSocket edit edit cookbook {0}", cookBook.getId());

      JsonObject model = createCookBookJson(cookBook);

      final String sessionId = wsClient.sendMessage(EDIT_ENDPOINT + cookBook.getId(), model.toString());
      cookBook = cookBookFromJson(wsClient.getMessage(sessionId));
   }

   @Override
   public CookBook find(Object id) {
      LOG.log(Level.CONFIG, "WebSocket find cookbook {0}", id);
      final String sessionId = wsClient.sendMessage(FIND_ENDPOINT + id, null);

      return cookBookFromJson(wsClient.getMessage(sessionId));
   }

   @Override
   public List<CookBook> findAll() {
      LOG.log(Level.CONFIG, "WebSocket find All cookbooks");
      final String sessionId = wsClient.sendMessage(FINDALL_ENDPOINT, null);
      return cookBookListFromJson(wsClient.getMessage(sessionId));
   }

   @Override
   public void remove(CookBook cookBook) {
      LOG.log(Level.CONFIG, "WebSocket remove cookbook {0}", cookBook.getId());

      JsonObject model = createCookBookJson(cookBook);

      final String sessionId = wsClient.sendMessage(REMOVE_ENDPOINT + cookBook.getId(), model.toString());
   }
}
