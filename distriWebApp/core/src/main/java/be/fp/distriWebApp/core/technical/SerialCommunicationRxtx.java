package be.fp.distriWebApp.core.technical;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Stream;

public class SerialCommunicationRxtx
{

    private static boolean  isReadStarted = false;
    private static boolean  isWriteStarted = false;

    private InputStream in;
    private OutputStream out;

    private PrintWriter fluxOut;
    private BufferedReader fluxIn;


    private BufferedReader fluxInForOut;

    private static String serialPort;

    private static Thread readThread;
    private static Thread writeThread;

    private static byte[] bufferRead = new byte[2048];
    private static byte[] bufferWrite = new byte[2048];
    private static int lenRead = -1;


    private static final Lock lock = new ReentrantLock();
    private static final Condition cond1 = lock.newCondition();
    private static final Condition cond2 = lock.newCondition();

    private static ByteArrayInputStream inputStreamForWrite;
    private static ByteArrayOutputStream outputStreamForWrite;

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
                fluxIn = new BufferedReader(new InputStreamReader(in));
                out = serialPort.getOutputStream();
                fluxOut = new PrintWriter(out);

                inputStreamForWrite = new ByteArrayInputStream(bufferWrite);
                outputStreamForWrite = new ByteArrayOutputStream();


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
        if(in  != null){
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

    public void writeOutputStreamForWrite(byte[] byteToWrite) throws IOException {
        bufferWrite = new byte[2048];
        bufferWrite = byteToWrite ;
        outputStreamForWrite.write(bufferWrite);
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
                    lock.lock();
                    try{
                        System.out.print(new String(bufferRead,0,lenRead));
                        cond1.signal();
                        cond2.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }

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
                int length = 0;
                byte[] buffer = new byte[2048];
                while (( length = inputStreamForWrite.read(buffer)) > -1 && isWriteStarted == true)
                {
                    this.out.write(buffer);
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

    public BufferedReader getFluxIn() {
        return fluxIn;
    }

    public void setFluxIn(BufferedReader fluxIn) {
        this.fluxIn = fluxIn;
    }

    public PrintWriter getFluxOut() {
        return fluxOut;
    }

    public void setFluxOut(PrintWriter fluxOut) {
        this.fluxOut = fluxOut;
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

    public ByteArrayInputStream getInputStreamForWrite() {
        return inputStreamForWrite;
    }

    public void setInputStreamForWrite(ByteArrayInputStream inputStreamForWrite) {
        SerialCommunicationRxtx.inputStreamForWrite = inputStreamForWrite;
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

    public Lock getLock() {
        return lock;
    }

    public Condition getCond1() {
        return cond1;
    }

    public Condition getCond2() {
        return cond2;
    }
}