package br.com.websocket.managedbeans;

import br.com.websocket.utils.Utils;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

/**
 *
 * @author jessica-cristina
 * Bean gerenciado. Gerencia a página do chat
 */
@ManagedBean
@ViewScoped
public class ChatMBeans {
    public static Set<Session> chatUsersSession = Collections.synchronizedSet(new HashSet<Session>());
    private String usuario;
    public ChatMBeans(){
        usuario = Utils.getValorSessao("username").toString();
    }

    /**
     * Verifica disponibilidade de nome de usuário
     * @param usuario
     * @return 
     */
    public static boolean usuarioDisponivel(String usuario){
        Iterator<Session> iterator = ChatMBeans.chatUsersSession.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getUserProperties().get("username").toString().equalsIgnoreCase(usuario))
                return false;
        }
        return true;
    }

    public String getUsuario() {
        return usuario;
    }
    
    /**
     * Efetua logoff
     * @return 
     */
    public String sair(){
        // Invalidar sessão
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        sessao.invalidate();
        return "index";
    }
    
}
