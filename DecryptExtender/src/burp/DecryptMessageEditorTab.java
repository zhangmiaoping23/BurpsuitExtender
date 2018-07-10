package burp;

import app.AppDecryptMessageEditorTabFactory;
import app.IAppDecryptMessageEditorTab;

import java.awt.*;

/**
 * Created by zhangmp on 2018/7/9.
 */
public class DecryptMessageEditorTab implements IMessageEditorTab{
    public DecryptMessageEditorTab(BurpExtender burpExtender,IMessageEditorController controller, boolean editable){
        this.burpExtender = burpExtender;
        this.controller = controller;
        this.editable = editable;
        this.textEditor =this.burpExtender.callbacks.createTextEditor();
        this.textEditor.setEditable(editable);
        this.appDecryptMessageEditorTab = AppDecryptMessageEditorTabFactory.createInstance(this);
    }
    @Override
    public String getTabCaption() {
        //burpExtender.stdout.println("getTabCaption");
        return this.appDecryptMessageEditorTab.getTabCaption();
    }

    @Override
    public Component getUiComponent() {
        //burpExtender.stdout.println("getUiComponent");
        return this.textEditor.getComponent();
    }

    @Override
    public boolean isEnabled(byte[] content, boolean isRequest) {
        // 在显示一个新的 HTTP 消息时，启用自定义的标签页
        // 通过 content 和 isRequest 也可以对特定的消息进行设置
        //burpExtender.stdout.println("isEnabled");
        return this.appDecryptMessageEditorTab.isEnabled(content,isRequest);
    }

    @Override
    public void setMessage(byte[] content, boolean isRequest) {
        // // 把请求消息里面的 data 参数进行 Base64 编码操作
        // 这里并未处理参数中没有 data 时的异常
        // burpExtender.stdout.println("setMessage");
        this.appDecryptMessageEditorTab.setMessage(content,isRequest);
    }

    @Override
    public byte[] getMessage() {
        //burpExtender.stdout.println("getMessage");
        return this.textEditor.getText();
    }

    @Override
    public boolean isModified() {
        //burpExtender.stdout.println("isModified");
        return this.textEditor.isTextModified();
    }

    @Override
    public byte[] getSelectedData() {
        //burpExtender.stdout.println("getSelectedData");
        return this.textEditor.getSelectedText();
    }

    public BurpExtender getBurpExtender(){
        return this.burpExtender;
    }
    private BurpExtender burpExtender;

    public IMessageEditorController getController() {
        return controller;
    }

    public boolean isEditable() {
        return editable;
    }

    public ITextEditor getTextEditor() {
        return textEditor;
    }

    private IMessageEditorController controller;
    private  boolean editable;
    ITextEditor textEditor;

    private IAppDecryptMessageEditorTab appDecryptMessageEditorTab;
}
