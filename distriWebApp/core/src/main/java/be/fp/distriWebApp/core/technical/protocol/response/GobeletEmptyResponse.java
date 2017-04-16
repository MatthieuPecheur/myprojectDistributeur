package be.fp.distriWebApp.core.technical.protocol.response;

import be.fp.distriWebApp.core.enums.ProtocolEnum;
import be.fp.distriWebApp.core.technical.protocol.ProtocolResponse;

/**
 * Created by mpc on 16-04-17.
 */
public class GobeletEmptyResponse extends ProtocolResponse{


    public GobeletEmptyResponse(){
        this.code = ProtocolEnum.GOBELET_RESPONSE_NO.getName();
    }
    /*getter and setters*/

}
