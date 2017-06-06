package br.com.websocket;

import br.com.websocket.managedbeans.ChatMBeans;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Iterator;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author jessica-cristina
 */
@ServerEndpoint("/WebSocketExample")
public class WebSocket {
    

    public WebSocket() {
    }
    /**
     * Abertura de conexão
     * @param session 
     */
    @OnOpen
    public void handleOpen(Session session){
        ChatMBeans.chatUsersSession.add(session);
    }
    /**
     * Manipula mensagem websocket recebida
     * @param message
     * @param session 
     */
    @OnMessage
    public void handleMessage(String message,Session session){
        String userName = (String) session.getUserProperties().get("username");
        if (userName==null){
            session.getUserProperties().put("username", message);
            try {
                session.getBasicRemote().sendText(buildJsonData("", message, "username_connected"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
            Iterator<Session> iterator = ChatMBeans.chatUsersSession.iterator();
            while(iterator.hasNext()){
                Session s = iterator.next();
                if (!s.equals(session)){
                    try {
                        s.getBasicRemote().sendText(buildJsonData("",message,"otherUser"));
                        session.getBasicRemote().sendText(
                                buildJsonData("", s.getUserProperties().get("username").toString(), "otherUser"));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }else{
            try {
                Iterator<Session> iterator = ChatMBeans.chatUsersSession.iterator();
                while (iterator.hasNext()) {
                    iterator.next().getBasicRemote().sendText(buildJsonData(userName, message, "message"));
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    /**
     * Fechamento de conexão
     * @param session
     * @throws IOException 
     */
    @OnClose
    public void handleClose(Session session) throws IOException{
        ChatMBeans.chatUsersSession.remove(session);
        Iterator<Session> iterator = ChatMBeans.chatUsersSession.iterator();
        while(iterator.hasNext()){
            Session s = iterator.next();
            if (!s.equals(session)){
                s.getBasicRemote().sendText(
                        buildJsonData("", session.getUserProperties().get("username").toString(),"username_disconnected"));
            }
        }
    }
    /**
     * Constrói mensagem json
     * @param username
     * @param message
     * @param dado
     * @return 
     */
    public static String buildJsonData(String username, String message, String dado){
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        
        jsonObjectBuilder.add(dado,username.isEmpty()? message: username +": "+message);
        jsonObjectBuilder.add("username",username);
        
        StringWriter stringWriter = new StringWriter();
        JsonWriter jsonWriter = Json.createWriter(stringWriter);
        jsonWriter.write(jsonObjectBuilder.build());
        return stringWriter.toString();
    }
}
