package be.fp.distriWebApp.core.technical.protocol.response;

import be.fp.distriWebApp.core.enums.ProtocolEnum;
import be.fp.distriWebApp.core.technical.protocol.ProtocolResponse;

/**
 * Created by mpc on 16-04-17.
 */
public class LedMapNokResponse extends ProtocolResponse{

    public LedMapNokResponse(){
        this.code = ProtocolEnum.LED_TABLE_RESPONSE_NOK.getName();
    }
    /*getter and setters*/

}
