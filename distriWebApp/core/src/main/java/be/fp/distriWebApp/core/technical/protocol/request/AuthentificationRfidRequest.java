package be.fp.distriWebApp.core.technical.protocol.request;

import be.fp.distriWebApp.core.enums.ProtocolEnum;
import be.fp.distriWebApp.core.technical.protocol.ProtocolRequest;

/**
 * Created by mpc on 16-04-17.
 */
public class AuthentificationRfidRequest extends ProtocolRequest {

    public AuthentificationRfidRequest(){
        this.code = ProtocolEnum.AUTHEN_RFID_REQUEST.getName() + "eot";
    }
    public byte[] toBytes(){
        byte[] bytes = code.getBytes();
        return bytes ;
    }
}
