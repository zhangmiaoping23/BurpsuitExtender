package tool;

import burp.*;

import java.util.List;

/**
 * Created by zhangmp on 2018/12/6.
 */
public class BurpPluginUtils {

    /*
        判断完后,可以通过以下语句
        IParameter parameter = this.helpers.getRequestParameter(content,"mkey");
     */
    public static boolean isParamNameExist(List<IParameter> listParameter,String paramName){
        IParameter parameter;
        int size = listParameter.size();
        String name = "";
        boolean ret = false;
        for (int index = 0; index < size; index++) {
            parameter = listParameter.get(index);
            name = parameter.getName();
            if(name.equals(paramName)) {
                ret = true;
            }
        }
        return ret;
    }

    /*
        header: POST /hwmarket/api/encryptApi2 HTTP/1.1
        header: Content-Encoding: gzip
        header: Connection: close
        header: User-Agent: HiSpace_9.0.0.303_HONOR
        header: Host: stores1.hispace.hicloud.com
        header: Content-Type: application/x-gzip
        header: Content-Length: 578
     */
    public static String getHeaderValue(List<String> headers,String headerName){
        String headerValue = "";
        String header = "";
        String lowerHeader = "";
        String lowerHeaderName = headerName.toLowerCase();
        int size = headers.size();
        for (int index = 0; index < size; index++) {
            header = headers.get(index);
            lowerHeader = header.toLowerCase();
            if(lowerHeader.startsWith(lowerHeaderName)){
                headerValue = header.substring(headerName.length());
                headerValue = headerValue.trim();
            }
        }
        return headerValue;
    }

    public static String getHost(boolean isRequest, byte[] content, IExtensionHelpers helpers,IHttpService httpService){
        String host = "";
        List<String> headers;
        if(isRequest){
            IRequestInfo requestInfo = helpers.analyzeRequest(content);
            headers = requestInfo.getHeaders();
            host = BurpPluginUtils.getHeaderValue(headers,"Host:");
        }else{
            if(null != httpService) {
                //在请求中，此处得到的host有可能是ip
                host = httpService.getHost();
            }
        }

        return host;
    }
}
