
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.apache.catalina.WebResourceRoot
import org.apache.catalina.core.StandardContext
import org.apache.catalina.startup.Tomcat
import org.apache.catalina.webresources.EmptyResourceSet
import org.apache.catalina.webresources.StandardRoot

class TomcatMain {
    
    File directory

    int port = -1
    
    String shutdownCommand
    
    int shutdownPort = -1

    public void validate() {
        if(!directory && !directory.exists()) {
            throw new IllegalStateException("The directory must be set.")
        }
    }
    
    /**
     * <p>The main method for the TomcatMain class; runs and executes Tomcat when the classpath is correctly setup.
     * There is an expectation this is run from the build project in which this is defined, so is very contextual
     * to the build environment.</p>
     * 
     * <p>The list of process arguments:
     * <ol>
     * <li>The tomcat web directory</li>
     * <li>The port for tomcat to run on</li>
     * <li>The shutdown command to listen to</li>
     * <li>The shutdown port to listen on</li>
     * </ol>
     * </p>
     * 
     */
    public static void main(String[] args) {
        final TomcatMain tc = new TomcatMain()
        tc.directory = new File(args[0])
        tc.port = Integer.parseInt(args[1])
        tc.shutdownCommand = args[2]
        tc.shutdownPort = Integer.parseInt(args[3])
        tc.run()
    }
    
    /**
     * Starts and runs the embedded tomcat instance per the definition of the main methods argument definition.
     */
    public void run() {

        final Tomcat tomcat = new Tomcat()

        tomcat.port = port
        tomcat.server.port = shutdownPort
        StandardContext ctx = (StandardContext) tomcat.addWebapp("", directory.absolutePath)

        println("Configuring tomcat with basedir ${directory.absolutePath} and port ${port}")

        WebResourceRoot resources = new StandardRoot(ctx);
        resources.addPreResources(new EmptyResourceSet(resources));
        ctx.setResources(resources);
        tomcat.start();
        tomcat.server.await()
    }
}