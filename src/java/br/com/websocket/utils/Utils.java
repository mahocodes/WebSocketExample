package br.com.websocket.utils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author jessica-cristina
 */
public class Utils {
    /**
     * Método para recuperar atributo na sessão
     *
     * @param atributo
     * @return
     */
    public static Object getValorSessao(String atributo) {
        try {
            HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance()
                    .getExternalContext().getSession(false);
            return sessao.getAttribute(atributo);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Método para remover valor sessão
     *
     * @param atributo
     */
    public static void removeValorSessao(String atributo) {
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        sessao.removeAttribute(atributo);
    }

    /**
     * Método para definir valor em sessão
     *
     * @param atributo
     * @param o
     */
    public static void setValorSessao(String atributo, Object o) {
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        sessao.setAttribute(atributo, o);
    }
    /**
     * Método para adicionar mensagens no contexto
     *
     * @param string Cabeçalho da mensagem
     * @param warning Mensagem a ser exibida
     * @param severity Tipo da mensagem (FacesMessage.)
     */
    public static void warning(String string, String warning, FacesMessage.Severity severity) {
        FacesContext.getCurrentInstance().addMessage(string, new FacesMessage(severity, warning, ""));
    }
}
