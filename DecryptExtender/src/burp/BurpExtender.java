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
        stderr = new PrintWriter(callbacks.getStderr(), true);

        //this.callbacks.registerHttpListener(new HttpListener(this.helpers,this.stdout));

       // 注册一个自定义的消息编辑器标签页的工厂。扩展插件可以在 Burp 的 HTTP 编辑器中渲染或编辑 HTTP 消息。
        callbacks.registerMessageEditorTabFactory(new MessageEditorTabFactory(this));
        // write a message to our output stream
        stdout.println("registerExtenderCallbacks Success ");

    }

    public IExtensionHelpers getHelpers() {
        return helpers;
    }

    public PrintWriter getStdout() {
        return stdout;
    }

    public PrintWriter getStderr() {
        return stderr;
    }

    IExtensionHelpers helpers;
    PrintWriter stdout;
    PrintWriter stderr;
    IBurpExtenderCallbacks callbacks;
}