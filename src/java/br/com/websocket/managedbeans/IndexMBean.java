package br.com.websocket.managedbeans;

import br.com.websocket.utils.Utils;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author jessica-cristina
 * Bean gerenciado. Gerencia página index
 */
@ManagedBean
@RequestScoped
public class IndexMBean {
    private String usuario;
    /**
     * Creates a new instance of IndexMBean
     */
    public IndexMBean() {
    }
    /**
     * Efetua logon do usuário no chat
     */
    
    public String logar(){
        Utils.setValorSessao("username", usuario);
        return "chat";
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
    
}
