package burp;


import java.io.PrintWriter;

/**
 * Created by zhangmp on 2018/7/9.
 * 扩展可以实现此接口。通过调用 IBurpExtenderCallbacks.registerHttpListener() 注册一个 HTTP 监听器。
 * Burp 里的任何一个工具发起 HTTP 请求或收到 HTTP 响应都会通知此监听器。扩展可以得到这些交互的数据，进行分析和修改。
 * 如果在开发插件的时候需要获取到所有的 HTTP 数据包，包括通过 Repeater 工具自定义修改的请求，则必须实现此接口，重写该方法。
 */
public class HttpListener implements IHttpListener {
    public HttpListener(IExtensionHelpers helpers,
                        PrintWriter stdout){
        this.helpers = helpers;
        this.stdout = stdout;
    }

    /*
    // 指示了发起请求或收到响应的 Burp 工具的 ID，所有的 toolFlag 定义在 IBurpExtenderCallbacks 接口中。
    int toolFlag

    // 指示该消息是请求消息（值为True）还是响应消息（值为False）
    messageIsRequest

    // 被处理的消息的详细信息，是一个 IHttpRequestResponse 对象
    messageInfo
     */
    @Override
    public void processHttpMessage(int toolFlag, boolean messageIsRequest, IHttpRequestResponse messageInfo) {

    }

    IExtensionHelpers helpers;
    PrintWriter stdout;
}
