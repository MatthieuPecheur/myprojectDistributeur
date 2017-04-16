package be.fp.distriWebApp.core.technical.protocol.request;

import be.fp.distriWebApp.core.enums.ProtocolEnum;
import be.fp.distriWebApp.core.technical.protocol.ProtocolRequest;

/**
 * Created by mpc on 16-04-17.
 */
public class LedAmbianceRequest extends ProtocolRequest {

    public LedAmbianceRequest(){
        this.code = ProtocolEnum.LED_AMBIANCE_REQUEST.getName();
    }


    public byte[] toBytes(){
        byte[] bytes = code.getBytes();
        return bytes;
    }
}
