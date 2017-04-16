package be.fp.distriWebApp.core.technical.protocol.response;

import be.fp.distriWebApp.core.enums.ProtocolEnum;
import be.fp.distriWebApp.core.technical.protocol.ProtocolResponse;

/**
 * Created by mpc on 16-04-17.
 */
public class GobeletToEarlyResponse extends ProtocolResponse{


    public GobeletToEarlyResponse(){
        this.code = ProtocolEnum.GOBELET_RESPONSE_TO_EARLY.getName();
    }
    /*getter and setters*/

}
