package app.huawei;


import app.AbstractAppDecryptMessageEditorTab;
import app.vivo.Base64Util;
import app.vivo.StoreWave;
import burp.IHttpService;
import burp.IParameter;
import burp.IRequestInfo;
import burp.IResponseInfo;
import tool.*;

import java.net.URLDecoder;
import java.util.List;

/**
 * Created by zhangmp on 2018/7/9.
 */
public class HuaweiDecryptMessageEditorTab extends AbstractAppDecryptMessageEditorTab {

    @Override
    public String getTabCaption() {
        return "Huawei Decrypt";
    }

    @Override
    public boolean isEnabled(byte[] content, boolean isRequest) {
        boolean ret = false;
        try {
            do {
                if(null == content){
                    break;
                }
                if(null == this.controller){
                    break;
                }

                String host = "";
                host = BurpPluginUtils.getHost(isRequest,content,this.helpers,this.controller.getHttpService());
                if ((null != host)) {
                    if (host.equals("grs.hicloud.com")
                            || host.equals("dnkeeper.hicloud.com")
                            || host.equals("stores1.hispace.hicloud.com")
                            || host.equals("ucs1.hispace.hicloud.com")
                            || host.contains("metrics1.data.hicloud.com")) {
                        ret = true;
                    }
                    if(false == isRequest){
                        if(host.equals("122.11.38.50")
                                || host.equals("118.194.35.11")
                                || host.equals("117.78.58.131")
                                || host.equals("117.78.53.50")){
                            ret = true;
                        }
                    }
                }
            }while(false);
            }catch (Exception e){
                this.stdout.println("isEnabled exception: " + e.toString());
            }
        return ret;
    }

    @Override
    public void setMessage(byte[] content, boolean isRequest) {
        if(null == content){
            this.textEditor.setText(null);
            this.textEditor.setEditable(false);
            return;
        }

        try {
            StringBuilder stringBuilder = new StringBuilder();
            int bodyOffset;
            int bodySize;
            JsonFormatTool jsonFormatTool = new JsonFormatTool();
            String host = BurpPluginUtils.getHost(isRequest,content,this.helpers,this.controller.getHttpService());
            if(isRequest) {
                IRequestInfo requestInfo = this.helpers.analyzeRequest(content);
                bodyOffset = requestInfo.getBodyOffset();
                bodySize = content.length - bodyOffset;
                byte bodyContent[] = new byte[bodySize];
                System.arraycopy(content, bodyOffset, bodyContent, 0, bodySize);


                //拷贝原来的头部,头部包含了尾部换行
                stringBuilder.append(new String(content,0,bodyOffset,"UTF-8"));

                if(host.equals("stores1.hispace.hicloud.com")
                        || host.equals("ucs1.hispace.hicloud.com")){
                    String unzipInfo = Tool.ungzip(bodyContent);
                    stringBuilder.append(ParamFormatTool.formatParams(unzipInfo,"UTF-8"));
                }


            }else{
                IResponseInfo responseInfo = this.helpers.analyzeResponse(content);
                bodyOffset = responseInfo.getBodyOffset();
                bodySize = content.length - bodyOffset;
                byte bodyContent[] = new byte[bodySize];
                System.arraycopy(content, bodyOffset, bodyContent, 0, bodySize);


                //拷贝原来的头部,头部包含了尾部换行
                stringBuilder.append(new String(content,0,bodyOffset,"UTF-8"));

                if(host.equals("grs.hicloud.com")){
                    stringBuilder.append(jsonFormatTool.formatJson(new String(bodyContent,0,bodySize)));
                }

                if(host.equals("stores1.hispace.hicloud.com")
                        || host.equals("ucs1.hispace.hicloud.com")
                        || host.equals("122.11.38.50")
                        || host.equals("118.194.35.11")
                        || host.equals("117.78.58.131")
                        || host.equals("117.78.53.50")){
                    String unzipInfo = new String(Tool.ungzipByte(bodyContent),"UTF-8");
                    stringBuilder.append(jsonFormatTool.formatJson(StringUtils.toCodeString(unzipInfo,"UTF-8")));
                }

            }
            this.textEditor.setText(stringBuilder.toString().getBytes("UTF-8"));
        }catch (Exception e){
            this.stdout.println("setMessage exception: " + e.toString());
        }
    }
}
