package be.fp.distriWebApp.core.technical.protocol.response;

import be.fp.distriWebApp.core.enums.ProtocolEnum;
import be.fp.distriWebApp.core.technical.protocol.ProtocolResponse;

/**
 * Created by mpc on 16-04-17.
 */
public class CocktailAckResponse extends ProtocolResponse{

    public CocktailAckResponse(){
        this.code = ProtocolEnum.COCKTAIL_RESPONSE_ACK.getName();
    }
    /*getter and setters*/

}
