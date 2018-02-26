import java.nio.ByteBuffer
import java.nio.channels.SocketChannel

class TomcatStopMain {
    
    String shutdownCommand
    
    int shutdownPort = -1
    
    /**
     * <p>The main method for the TomcatStopMain class. It stops the running tomcat server
     * which was started with the builds run task using the given shutdown command and
     * port number. This allows one to control the server with Gradle in multiple ways.</p>
     * 
     * <p>The list of process arguments:
     * <ol>
     * <li>The shutdown command to listen to</li>
     * <li>The shutdown port to listen on</li>
     * </ol>
     * </p>
     * 
     */
    public static void main(String[] args) {
        final TomcatStopMain tc = new TomcatStopMain()
        tc.shutdownCommand = args[0]
        tc.shutdownPort = Integer.parseInt(args[1])
        tc.stop()
    }
    
    /**
     * Stops the tomcat server using the given port and shutdown command set on this instance.
     */
    public void stop() {
        final InetAddress ia = Inet4Address.localHost
        final InetSocketAddress isa = new InetSocketAddress(ia, shutdownPort)
        final SocketChannel sc = SocketChannel.open(isa)
        try {
            sc.write(ByteBuffer.wrap(shutdownCommand.getBytes("UTF-8")))
        } finally {
            sc.close()
        }
    }
}