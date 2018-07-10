package app.example;


import app.AbstractAppDecryptMessageEditorTab;

/**
 * Created by zhangmp on 2018/7/9.
 */
public class ExampleDecryptMessageEditorTab extends AbstractAppDecryptMessageEditorTab {

    @Override
    public String getTabCaption() {
        return "Example Decrypt";
    }

    @Override
    public boolean isEnabled(byte[] content, boolean isRequest) {
        return true;
    }

    @Override
    public void setMessage(byte[] content, boolean isRequest) {
        this.textEditor.setText(content);
    }
}
