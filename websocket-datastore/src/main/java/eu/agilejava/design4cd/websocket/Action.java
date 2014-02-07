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

import java.util.HashMap;
import java.util.Map;

/**
 * Enum for actions supported by WebSocket API.
 *
 * @author Ivar Grimstad <ivar.grimstad@gmail.com>
 */
enum Action {

   COUNT("count"),
   CREATE("create"),
   EDIT("edit"),
   FIND("find"),
   FIND_ALL("findall"),
   REMOVE("remove");

   private final String action;
   private final static Map<String, Action> actions = new HashMap<>();
   
   static {
      for(Action action : values() ) {
         actions.put(action.action, action);
      }
   }

   private Action(final String action) {
      this.action = action;
   }
   
   static Action fromString(final String action) {
      return actions.get(action);
   }
}
