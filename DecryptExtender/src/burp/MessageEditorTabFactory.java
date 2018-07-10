package burp;

/**
 * Created by zhangmp on 2018/7/9.
 */
public class MessageEditorTabFactory implements IMessageEditorTabFactory {
    public MessageEditorTabFactory(BurpExtender burpExtender){
        this.burpExtender = burpExtender;
    }

    /**
    *Burp 将会对每一个 HTTP 消息编辑器调用一次此方法(请求与响应各一个），此工厂必须返回一个新的 IMessageEditorTab 对象
     */
    @Override
    public IMessageEditorTab createNewInstance(IMessageEditorController controller, boolean editable) {
        //burpExtender.stdout.println("createNewInstance");
        return new DecryptMessageEditorTab(this.burpExtender,controller,editable);
    }

    private BurpExtender burpExtender;
}
