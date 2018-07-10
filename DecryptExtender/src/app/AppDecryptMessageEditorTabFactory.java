package app;

import app.example.ExampleDecryptMessageEditorTab;
import burp.DecryptMessageEditorTab;

/**
 * Created by zhangmp on 2018/7/10.
 */
public class AppDecryptMessageEditorTabFactory {
    public static IAppDecryptMessageEditorTab createInstance(DecryptMessageEditorTab decryptMessageEditorTab){
        //根据你想要解密的App进行初始化
        AbstractAppDecryptMessageEditorTab appDecryptMessageEditorTab = new ExampleDecryptMessageEditorTab();//此条语句根据实际替换
        appDecryptMessageEditorTab.setDecryptMessageEditorTab(decryptMessageEditorTab);
        return appDecryptMessageEditorTab;
    }
}
