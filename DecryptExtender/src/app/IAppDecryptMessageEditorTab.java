package app;

import burp.DecryptMessageEditorTab;

/**
 * Created by zhangmp on 2018/7/10.
 */
public interface IAppDecryptMessageEditorTab {
    public String getTabCaption();

    public boolean isEnabled(byte[] content, boolean isRequest);

    public void setMessage(byte[] content, boolean isRequest);
}
