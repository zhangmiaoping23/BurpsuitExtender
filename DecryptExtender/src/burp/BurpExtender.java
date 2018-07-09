package burp;
import java.io.PrintWriter;

public class BurpExtender implements IBurpExtender {

    @Override
    public void registerExtenderCallbacks(IBurpExtenderCallbacks callbacks) {
        // set our extension name
        this.callbacks = callbacks;
        this.callbacks.setExtensionName("decrypt extension");

        //在初始化的时候将burp设置成不拦截模式
        this.callbacks.setProxyInterceptionEnabled(false);

        helpers =  this.callbacks.getHelpers();

        stdout = new PrintWriter(callbacks.getStdout(), true);
        PrintWriter stderr = new PrintWriter(callbacks.getStderr(), true);

        this.callbacks.registerHttpListener(new HttpListener(this.helpers,this.stdout));
        // write a message to our output stream
        stdout.println("registerExtenderCallbacks Success ");

    }

    IExtensionHelpers helpers;
    PrintWriter stdout;
    IBurpExtenderCallbacks callbacks;
}