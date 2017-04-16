package be.fp.distriWebApp.core.technical.protocol.response;

import be.fp.distriWebApp.core.enums.ProtocolEnum;
import be.fp.distriWebApp.core.technical.protocol.ProtocolResponse;

/**
 * Created by mpc on 16-04-17.
 */
public class LedAmbianceResponse extends ProtocolResponse{

    public LedAmbianceResponse(){
        this.code = ProtocolEnum.LED_AMBIANCE_RESPONSE.getName();
    }
    /*getter and setters*/

}
