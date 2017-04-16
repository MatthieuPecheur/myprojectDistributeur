package be.fp.distriWebApp.core.technical.protocol.response;

import be.fp.distriWebApp.core.enums.ProtocolEnum;
import be.fp.distriWebApp.core.technical.protocol.ProtocolRequest;
import be.fp.distriWebApp.core.technical.protocol.ProtocolResponse;

/**
 * Created by mpc on 16-04-17.
 */
public class AuthentificationRfidReadResponse extends ProtocolResponse {

    public AuthentificationRfidReadResponse(){
        this.code = ProtocolEnum.AUTHEN_RFID_RESPONSE_READ.getName();
    }

}
