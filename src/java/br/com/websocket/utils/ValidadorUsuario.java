package br.com.websocket.utils;

import br.com.websocket.managedbeans.ChatMBeans;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author jessica-cristina
 */
@FacesValidator("ValidadorUsuario")
/**
 * Classe para validação de Usuário.
 *
 * @author jessica-cristina
 */
public class ValidadorUsuario implements Validator {

    /**
     *
     * @param fc
     * @param uic
     * @param valor
     * @throws ValidatorException
     */
    @Override
    public void validate(FacesContext fc, UIComponent uic, Object valor) throws ValidatorException {
        if (!ChatMBeans.usuarioDisponivel(valor.toString())) {
            FacesMessage message = new FacesMessage();
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            message.setSummary("Escolha outro apelido");
            throw new ValidatorException(message);
        }
    }
}
