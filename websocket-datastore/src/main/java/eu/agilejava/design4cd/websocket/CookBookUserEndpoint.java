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

import eu.agilejava.design4cd.entities.CookBookUser;
import eu.agilejava.design4cd.service.CookBookUserService;
import javax.ejb.EJB;
import javax.websocket.OnMessage;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import static eu.agilejava.design4cd.websocket.json.DSCookBookUserUtil.*;
import static eu.agilejava.design4cd.websocket.Action.*;
import java.io.Serializable;
import javax.ejb.Stateless;

/**
 * WebSocket endpoint for CookBookUser.
 *
 * @author Ivar Grimstad <ivar.grimstad@gmail.com>
 */
@Stateless
@ServerEndpoint("/user/{action}/{id}")
public class CookBookUserEndpoint implements Serializable {

   private static final long serialVersionUID = 15464814555856L;

   @EJB
   private CookBookUserService cookBookUserService;

   /**
    * Handles incoming messages.
    *
    * @param action The action to be made
    * @param id A user id
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
            returnValue = create(cookBookUserFromJson(message));
            break;
         case EDIT:
            returnValue = edit(cookBookUserFromJson(message));
            break;
         case FIND:
            returnValue = find(Long.valueOf(id));
            break;
         case FIND_ALL:
            returnValue = findAll();
            break;
         case REMOVE:
            remove(cookBookUserFromJson(message));
            returnValue = "cookbook removed";
            break;
         default:
            returnValue = "no action performed";
      }

      return returnValue;
   }

   private String count() {
      return String.valueOf(cookBookUserService.count());
   }

   private String create(CookBookUser user) {

      cookBookUserService.create(user);

      return createCookBookUserJson(user);
   }

   private String edit(CookBookUser user) {

      cookBookUserService.edit(user);

      return createCookBookUserJson(user);
   }

   private String find(Long id) {
      return createCookBookUserJson(cookBookUserService.find(id));
   }

   private String findAll() {
      return createCookBookUserListJson(cookBookUserService.findAll());
   }

   private void remove(CookBookUser user) {
      cookBookUserService.remove(user);
   }
}
