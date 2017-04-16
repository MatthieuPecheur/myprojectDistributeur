package be.fp.distriWebApp.core.technical.protocol.response;

import be.fp.distriWebApp.core.enums.ProtocolEnum;
import be.fp.distriWebApp.core.technical.protocol.ProtocolResponse;

/**
 * Created by mpc on 16-04-17.
 */
public class CocktailEndResponse extends ProtocolResponse{


    public CocktailEndResponse(){
        this.code = ProtocolEnum.COCKTAIL_RESPONSE_END.getName();
    }
    /*getter and setters*/

}
