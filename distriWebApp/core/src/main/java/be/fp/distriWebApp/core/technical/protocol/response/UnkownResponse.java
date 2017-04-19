package be.fp.distriWebApp.core.technical.protocol.response;

import be.fp.distriWebApp.core.enums.ProtocolEnum;
import be.fp.distriWebApp.core.technical.protocol.ProtocolResponse;

/**
 * Created by mpc on 16-04-17.
 */
public class UnkownResponse extends ProtocolResponse {

    private String codeUnknow;
    public UnkownResponse(){
        this.code = ProtocolEnum.INCONNUE_RESPONSE.getName();
    }

    public String getCodeUnknow() {
        return codeUnknow;
    }

    public void setCodeUnknow(String codeUnknow) {
        this.codeUnknow = codeUnknow;
    }
}
