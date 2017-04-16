package be.fp.distriWebApp.core.technical.protocol.request;

import be.fp.distriWebApp.core.enums.ProtocolEnum;
import be.fp.distriWebApp.core.technical.protocol.ProtocolRequest;

import java.math.BigDecimal;
import java.util.LinkedList;

/**
 * Created by mpc on 16-04-17.
 */
public class CocktailRequest extends ProtocolRequest {

    private LinkedList<CocktailRequestElement> cocktailList;
    public CocktailRequest(){
        this.code = ProtocolEnum.COCKTAIL_REQUEST.getName();
    }

    public LinkedList<CocktailRequestElement> getCocktailList() {
        return cocktailList;
    }

    public void setCocktailList(LinkedList<CocktailRequestElement> cocktailList) {
        this.cocktailList = cocktailList;
    }


    public byte[] toBytes(){
        byte[] bytes = code.getBytes();
        return bytes;
    }

    /*SubElement*/

    private class CocktailRequestElement{
        Integer numPompe;
        BigDecimal time;
        byte[] color;

        /*Getter and setters*/

        public Integer getNumPompe() {
            return numPompe;
        }

        public void setNumPompe(Integer numPompe) {
            this.numPompe = numPompe;
        }

        public BigDecimal getTime() {
            return time;
        }

        public void setTime(BigDecimal time) {
            this.time = time;
        }

        public byte[] getColor() {
            return color;
        }

        public void setColor(byte[] color) {
            this.color = color;
        }
    }

}
