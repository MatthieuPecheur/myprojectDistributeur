package be.fp.distriWebApp.core.technical.protocol.response;

import be.fp.distriWebApp.core.enums.ProtocolEnum;
import be.fp.distriWebApp.core.technical.protocol.ProtocolResponse;

/**
 * Created by mpc on 16-04-17.
 */
public class EtatTechniqueResponse extends ProtocolResponse{



    public EtatTechniqueResponse(){
        this.code = ProtocolEnum.ETAT_TECHNIQUE_RESPONSE.getName();
    }
    /*getter and setters*/

}
