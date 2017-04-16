package be.fp.distriWebApp.core.technical.protocol.request;

import be.fp.distriWebApp.core.enums.ProtocolEnum;
import be.fp.distriWebApp.core.technical.protocol.ProtocolRequest;

/**
 * Created by mpc on 16-04-17.
 */
public class EtatGeneralRequest extends ProtocolRequest {

    public EtatGeneralRequest(){
        this.code = ProtocolEnum.ETAT_GENERAL_REQUEST.getName();
    }

    public byte[] toBytes(){
        byte[] bytes = code.getBytes();
        return bytes;
    }

}
