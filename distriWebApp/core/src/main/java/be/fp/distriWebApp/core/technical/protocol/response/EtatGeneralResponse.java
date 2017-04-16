package be.fp.distriWebApp.core.technical.protocol.response;

import be.fp.distriWebApp.core.enums.ProtocolEnum;
import be.fp.distriWebApp.core.technical.protocol.ProtocolResponse;

/**
 * Created by mpc on 16-04-17.
 */
public class EtatGeneralResponse extends ProtocolResponse{

    public EtatGeneralResponse(){
        this.code = ProtocolEnum.ETAT_GENERAL_RESPONSE.getName();
    }
    /*getter and setters*/

}
