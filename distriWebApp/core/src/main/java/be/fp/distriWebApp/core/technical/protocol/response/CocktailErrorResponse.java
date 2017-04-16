package be.fp.distriWebApp.core.technical.protocol.response;

import be.fp.distriWebApp.core.enums.ProtocolEnum;
import be.fp.distriWebApp.core.technical.protocol.ProtocolResponse;

/**
 * Created by mpc on 16-04-17.
 */
public class CocktailErrorResponse extends ProtocolResponse{

    private Integer errorCode;

    public CocktailErrorResponse(){
        this.code = ProtocolEnum.COCKTAIL_RESPONSE_ERROR.getName();
    }
    /*getter and setters*/

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }
}
