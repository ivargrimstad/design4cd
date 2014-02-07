package eu.agilejava.design4cd.dao.websocket;

import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.Dependent;
import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import javax.ws.rs.client.Client;

/**
 * WebSocket client for the datastore.
 * 
 * @author Ivar Grimstad <ivar.grimstad@gmail.com>
 */
@Dependent
@ClientEndpoint
public class WebSocketDatastoreClient implements Serializable {

   private static final long serialVersionUID = 15464335465856L;
   
   private static final String BASE_URI = "ws://localhost:8080/websocket-datastore";
   private Map<String, String> messages = new HashMap<>();
   private CountDownLatch messageLatch;

   /**
    * Sends message to the WebSocket server.
    * 
    * @param endpoint The server endpoint
    * @param msg The message
    * @return a return message
    */
   public String sendMessage(String endpoint, String msg) {

      String returnValue = "-1";
      try {
         messageLatch = new CountDownLatch(1);
         WebSocketContainer container = ContainerProvider.getWebSocketContainer();
         String uri = BASE_URI + endpoint;
         Session session = container.connectToServer(this, URI.create(uri));
         session.getBasicRemote().sendText(msg != null ? msg : "");
         returnValue = session.getId();

         messageLatch.await(100, TimeUnit.SECONDS);

      } catch (DeploymentException | IOException | InterruptedException ex) {
         Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
      }

      return returnValue;
   }

   /**
    * Handles incoming message.
    * 
    * @param session The WebSocket session
    * @param message The message
    */
   @OnMessage
   public void onMessage(Session session, String message) {
      messages.put(session.getId(), message);
      messageLatch.countDown();
   }

   /**
    * Gets the message for this session.
    * 
    * @param sessionId The session id
    * @return The message
    */
   public String getMessage(String sessionId) {
      return messages.get(sessionId);
   }
}
