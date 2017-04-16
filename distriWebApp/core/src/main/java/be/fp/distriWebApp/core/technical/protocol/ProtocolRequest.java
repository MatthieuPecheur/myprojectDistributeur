package be.fp.distriWebApp.core.technical.protocol;

/**
 * Created by mpc on 16-04-17.
 */
public abstract class ProtocolRequest extends Protocol{
    abstract public byte[] toBytes();
}
