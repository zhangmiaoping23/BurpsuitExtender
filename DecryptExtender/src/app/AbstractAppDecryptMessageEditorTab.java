package app;

import app.IAppDecryptMessageEditorTab;
import burp.*;

import java.io.PrintWriter;

/**
 * Created by zhangmp on 2018/7/10.
 */
public abstract class AbstractAppDecryptMessageEditorTab implements IAppDecryptMessageEditorTab {
    public void setDecryptMessageEditorTab(DecryptMessageEditorTab decryptMessageEditorTab){
        this.decryptMessageEditorTab = decryptMessageEditorTab;
        this.controller = decryptMessageEditorTab.getController();
        this.editable = decryptMessageEditorTab.isEditable();
        this.textEditor = decryptMessageEditorTab.getTextEditor();

        this.burpExtender = decryptMessageEditorTab.getBurpExtender();
        this.helpers = this.burpExtender.getHelpers();
        this.stderr = this.burpExtender.getStderr();
        this.stdout = this.burpExtender.getStdout();
    }
    protected DecryptMessageEditorTab decryptMessageEditorTab;
    protected  IMessageEditorController controller;
    protected  boolean editable;
    protected  ITextEditor textEditor;

    protected IExtensionHelpers helpers;
    protected BurpExtender burpExtender;
    protected PrintWriter stdout;
    protected PrintWriter stderr;
}
