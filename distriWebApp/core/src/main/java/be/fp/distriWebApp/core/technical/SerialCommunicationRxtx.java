package be.fp.distriWebApp.core.technical;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

public class SerialCommunicationRxtx
{

    private static boolean  isReadStarted = false;
    private static boolean  isWriteStarted = false;
    private static InputStream in;
    private static OutputStream out;
    private static String serialPort;

    private static Thread readThread;
    private static Thread writeThread;

    private static byte[] bufferRead = new byte[2024];
    private static int lenRead = -1;


    public SerialCommunicationRxtx(String serialPort)
    {
        super();
        setSerialPort(serialPort);
    }
    public void connect () throws Exception
    {
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(serialPort);
        if ( portIdentifier.isCurrentlyOwned() )
        {
            System.out.println("Error: Port is currently in use");
        }
        else
        {
            CommPort commPort = portIdentifier.open(this.getClass().getName(),2000);

            if ( commPort instanceof SerialPort )
            {
                SerialPort serialPort = (SerialPort) commPort;
                serialPort.setSerialPortParams(57600,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);

                in = serialPort.getInputStream();
                out = serialPort.getOutputStream();

                startReadCommunication();
                startWriteCommunication();

            }
            else
            {
                System.out.println("Error: Only serial ports are handled by this example.");
            }
        }
    }

    public void unConnect(){
        stopReadCommunication();
        stopWriteCommunication();
    }

    public void startReadCommunication(){
        if(in != null){
            readThread = new Thread(new SerialReader(in));
            if(!isReadThreadAlive()){
                readThread.start();
                isReadStarted = true;
            }
        }
    }
    public void startWriteCommunication(){
        if(out != null){
            writeThread = new Thread(new SerialWriter(out));
            if(!isWriteThreadAlive()){
                writeThread.start();
                isWriteStarted = true;
            }
        }
    }

    public void stopReadCommunication(){
        if(isReadThreadAlive()) {
            isReadStarted = false;
        }
    }

    public void stopWriteCommunication(){
         if(isWriteThreadAlive()){
             isWriteStarted = false;
         }
    }

    public boolean isReadThreadAlive(){
        return readThread != null && readThread.isAlive();
    }
    public boolean isWriteThreadAlive(){
        return writeThread != null && writeThread.isAlive();
    }

    public boolean writeString(String stringToWrite) throws IOException{
        try {
            byte[] result = stringToWrite.getBytes(StandardCharsets.UTF_8);
            out.write(result);
            return true;
        }catch (IOException e){
            throw e;
        }
    }

    /** */
    public static class SerialReader implements Runnable 
    {
        InputStream in;
        
        public SerialReader ( InputStream in )
        {
            this.in = in;
        }
        
        public void run ()
        {

            try
            {
                while ( ( lenRead = this.in.read(bufferRead)) > -1 && isReadStarted == true)
                {
                    System.out.print(new String(bufferRead,0,lenRead));
                }
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }            
        }
    }

    /** */
    public static class SerialWriter implements Runnable 
    {
        OutputStream out;
        
        public SerialWriter ( OutputStream out )
        {
            this.out = out;
        }
        
        public void run ()
        {
            try
            {                
                int c = 0;
                while (( lenRead = System.in.read()) > -1 && isWriteStarted == true)
                {
                    this.out.write(c);
                }                
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }            
        }
    }

    /* getters and setters */


    public boolean isStarted() {
        return isWriteStarted && isReadStarted ;
    }


    public InputStream getIn() {
        return in;
    }

    public void setIn(InputStream in) {
        this.in = in;
    }

    public OutputStream getOut() {
        return out;
    }

    public void setOut(OutputStream out) {
        this.out = out;
    }

    public String getSerialPort() {
        return serialPort;
    }

    public void setSerialPort(String serialPort) {
        this.serialPort = serialPort;
    }

    public Thread getReadThread() {
        return readThread;
    }

    public void setReadThread(Thread readThread) {
        this.readThread = readThread;
    }

    public Thread getWriteThread() {
        return writeThread;
    }

    public void setWriteThread(Thread writeThread) {
        this.writeThread = writeThread;
    }

    public boolean isReadStarted() {
        return isReadStarted;
    }

    public void setIsReadStarted(boolean isReadStarted) {
        SerialCommunicationRxtx.isReadStarted = isReadStarted;
    }

    public boolean isWriteStarted() {
        return isWriteStarted;
    }

    public static void setIsWriteStarted(boolean isWriteStarted) {
        SerialCommunicationRxtx.isWriteStarted = isWriteStarted;
    }

    public byte[] getBufferRead() {
        return bufferRead;
    }

    public  void setBufferRead(byte[] bufferRead) {
        SerialCommunicationRxtx.bufferRead = bufferRead;
    }

    public int getLenRead() {
        return lenRead;
    }

    public void setLenRead(int lenRed) {
        SerialCommunicationRxtx.lenRead = lenRed;
    }
}