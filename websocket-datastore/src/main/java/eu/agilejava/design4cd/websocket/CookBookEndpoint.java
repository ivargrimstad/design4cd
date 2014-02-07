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
package eu.agilejava.design4cd.websocket;

import eu.agilejava.design4cd.entities.CookBook;
import eu.agilejava.design4cd.service.CookBookService;
import javax.ejb.EJB;
import javax.websocket.OnMessage;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import static eu.agilejava.design4cd.websocket.json.DSCookBookUtil.*;
import static eu.agilejava.design4cd.websocket.Action.*;
import java.io.Serializable;
import javax.ejb.Stateless;

/**
 * WebSocket endpoint for CookBook.
 *
 * @author Ivar Grimstad <ivar.grimstad@gmail.com>
 */
@Stateless
@ServerEndpoint("/cookbook/{action}/{id}")
public class CookBookEndpoint implements Serializable {
   private static final long serialVersionUID = 15464814235856L;

   @EJB
   private CookBookService cookBookService;

   /**
    * Handles incoming messages.
    *
    * @param action The action to be made
    * @param id A cookbook id
    * @param message The message body
    * @return A return message
    */
   @OnMessage
   public String onMessage(@PathParam("action") String action, @PathParam("id") String id, String message) {

      String returnValue;

      switch (Action.fromString(action)) {
         case COUNT:
            returnValue = count();
            break;
         case CREATE:
            returnValue = create(cookBookFromJson(message));
            break;
         case EDIT:
            returnValue = edit(cookBookFromJson(message));
            break;
         case FIND:
            returnValue = find(Long.valueOf(id));
            break;
         case FIND_ALL:
            returnValue = findAll();
            break;
         case REMOVE:
            remove(cookBookFromJson(message));
            returnValue = "cookbook removed";
            break;
         default:
            returnValue = "no action performed";
      }

      return returnValue;
   }

   private String count() {
      return String.valueOf(cookBookService.count());
   }

   private String create(CookBook cookBook) {

      cookBookService.create(cookBook);

      return createCookBookJson(cookBook);
   }

   private String edit(CookBook cookBook) {

      cookBookService.edit(cookBook);

      return createCookBookJson(cookBook);
   }

   private String find(Long id) {
      return createCookBookJson(cookBookService.find(id));
   }

   private String findAll() {
      return createCookBookListJson(cookBookService.findAll());
   }

   private void remove(CookBook cookBook) {
      cookBookService.remove(cookBook);
   }
}
