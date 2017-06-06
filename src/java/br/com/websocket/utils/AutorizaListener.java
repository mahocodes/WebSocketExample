package br.com.websocket.utils;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpSession;

/**
 * Classe para controle do ciclo de vida da aplicação e autenticação do usuário.
 *
 * @author jessica-cristina
 */
public class AutorizaListener implements PhaseListener {

    /**
     *
     * @param event
     */
    @Override
    public void afterPhase(PhaseEvent event) {
        
        //Obtém o contexto atual
        FacesContext contexto = null;
        try {
            contexto = event.getFacesContext();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // Fase Restore View
        if (event.getPhaseId().equals(PhaseId.RESTORE_VIEW)) {
            /*
             * Obtém a página que atualmente está interagindo com o ciclo
             */
            boolean isDefaultPage = true;

            try {
                String paginaAtual = contexto.getViewRoot().getViewId();
                isDefaultPage = paginaAtual.lastIndexOf("index.xhtml") > -1;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            /*
             * Obtém a sessão atual
             */
            String usuario = null;
            try {
                HttpSession sessao = (HttpSession) contexto.getExternalContext().getSession(false);
                /*
                 * Restaga o login do usuário logado
                 */
                usuario = (String) sessao.getAttribute("username");
            } catch (Exception e) {
                System.out.println("Erro ao tentar obter a sessão: " + e);
                System.out.println(event.getPhaseId());
            }
            /*
             * Verifica se o usuário está logado e se não está na página de login
             */
            try {
                 NavigationHandler nh = contexto.getApplication().getNavigationHandler();
                if (usuario == null && !isDefaultPage) {
                    /*
                     * Redireciona o fluxo para a página de login
                     */
                    nh.handleNavigation(contexto, null, "index");
                }else if(usuario != null && isDefaultPage){
                    // Redireciona para a pagina do chat
                    nh.handleNavigation(contexto, null, "chat");
                }
            }catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     *
     * @param event
     */
    @Override
    public void beforePhase(PhaseEvent event) {

    }

    /**
     * Recupera id da fase atual do evento
     *
     * @return Id da fase
     */
    @Override
    public PhaseId getPhaseId() {
        return PhaseId.ANY_PHASE;
    }
}
