package be.fp.distriWebApp.core.technical;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

public class SerialCommunicationRxtx
{

    private static boolean  isReadStarted = false;
    private static boolean  isReadLineStarted = false;
    private static boolean  isWriteStarted = false;

    private InputStream in;
    private OutputStream out;

    private PrintWriter fluxOut;
    private BufferedReader fluxIn;


    private BufferedReader fluxInForOut;
    private static int fact = 0;

    private static String serialPort;

    private static Thread readThread;
    private static Thread readLineThread;
    private static Thread writeThread;

    private static String lineRead;
    private static byte[] bufferRead = new byte[2048];
    private static byte[] bufferWrite = new byte[0];
    private static int lenRead = -1;


    private static final Lock lock = new ReentrantLock();
    private static final Condition cond1 = lock.newCondition();
    private static final Condition cond2 = lock.newCondition();

    private static ByteArrayInputStream inputStreamForWrite;
    private static ByteArrayOutputStream outputStreamForWrite;
    private CommPort commPort;
    private CommPortIdentifier portIdentifier;
    private SerialPort serialPortObj;

    public SerialCommunicationRxtx(String serialPort)
    {
        super();
        setSerialPort(serialPort);
    }
    public void connect () throws Exception
    {
        portIdentifier = CommPortIdentifier.getPortIdentifier(serialPort);
        if ( portIdentifier.isCurrentlyOwned() )
        {
            System.out.println("Error: Port is currently in use");
        }
        else
        {
            commPort = portIdentifier.open(this.getClass().getName(),2000);

            if ( commPort instanceof SerialPort )
            {
            	serialPortObj = (SerialPort) commPort;
            	serialPortObj.setSerialPortParams(57600,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);

                in = serialPortObj.getInputStream();
                fluxIn = new BufferedReader(new InputStreamReader(in));
                out = serialPortObj.getOutputStream();
                fluxOut = new PrintWriter(out);

                inputStreamForWrite = new ByteArrayInputStream(bufferWrite);
                outputStreamForWrite = new ByteArrayOutputStream();


                startReadCommunication();
                //startReadLineCommunication();
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
       // stopReadLineCommunication();
        stopWriteCommunication();
        commPort.close();
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

    private void startReadLineCommunication() {
        if(fluxIn  != null){
            readLineThread = new Thread(new SerialReaderLine(fluxIn));
            if(!isReadLineThreadAlive()){
                readLineThread.start();
                isReadLineStarted = true;
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
            
            try {
            	isReadStarted = false;
				fluxIn.close();
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }

    public void stopReadLineCommunication(){
        if(isReadThreadAlive()) {
           
            try {
            	isReadLineStarted = false;
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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

    public boolean isReadLineThreadAlive(){
        return readLineThread != null && readLineThread.isAlive();
    }
    public boolean isWriteThreadAlive(){
        return writeThread != null && writeThread.isAlive();
    }

    public void writeOutputStreamForWrite(byte[] byteToWrite) throws IOException {
        outputStreamForWrite.write(byteToWrite);
        bufferWrite = outputStreamForWrite.toByteArray();
        fact = 1;
        
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
                	if(lenRead > 0){
                	     lock.lock();
                         try{
                             System.out.print(new String(bufferRead,0,lenRead));
                             cond1.signal();
                           
                            // cond2.await();
                          }/*catch (InterruptedException e) {
                             e.printStackTrace();
                         }*/ finally {
                             lock.unlock();
                         }
                	}
                }
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }            
        }
    }

    public static class SerialReaderLine implements Runnable
    {
        BufferedReader fluxIn;

        public SerialReaderLine (  BufferedReader fluxIn )
        {
            this.fluxIn = fluxIn;
        }

        public void run ()
        {

            try
            {
                while ( ( lineRead = fluxIn.readLine()) != null && isReadLineStarted == true)
                {
                    lock.lock();
                    try{
                        System.out.print(lineRead);
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
                while (isWriteStarted == true)
                {
                	//length = inputStreamForWrite.read(buffer);
                	if(fact > 0){
                		this.out.write(bufferWrite);
                		this.out.flush();
                		fact = 0;
                	}
                    
                }                
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }            
        }
    }

    /* getters and setters */


    public static boolean isReadLineStarted() {
        return isReadLineStarted;
    }

    public static void setIsReadLineStarted(boolean isReadLineStarted) {
        SerialCommunicationRxtx.isReadLineStarted = isReadLineStarted;
    }

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

    public static Thread getReadLineThread() {
        return readLineThread;
    }

    public static void setReadLineThread(Thread readLineThread) {
        SerialCommunicationRxtx.readLineThread = readLineThread;
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

    public  String getLineRead() {
        return lineRead;
    }

    public void setLineRead(String lineRead) {
        SerialCommunicationRxtx.lineRead = lineRead;
    }
	public int getFact() {
		return fact;
	}
	public void setFact(int fact) {
		this.fact = fact;
	}
    
    
}