package be.fp.distriWebApp.core.technical.protocol.response;

import be.fp.distriWebApp.core.enums.ProtocolEnum;
import be.fp.distriWebApp.core.technical.protocol.ProtocolResponse;

/**
 * Created by mpc on 16-04-17.
 */
public class CocktailStartResponse extends ProtocolResponse{

    public CocktailStartResponse(){
        this.code = ProtocolEnum.COCKTAIL_RESPONSE_START.getName();
    }
    /*getter and setters*/

}
