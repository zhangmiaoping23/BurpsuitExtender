package tool;

/**
 * Created by zhangmp on 2018/12/5.
 */
public class ParamFormatTool {
    public static String formatParams(String params,String code){
        StringBuilder stringBuilder = new StringBuilder();
        try {
            String[] items = params.split("&");
            int itemSize = items.length;
            for(int i = 0; i < itemSize; i++){
                stringBuilder.append(StringUtils.toCodeString(items[i],code));
                if(i != itemSize - 1){
                    stringBuilder.append("\r\n&");
                }
            }
        }catch (Exception e){

        }
        return stringBuilder.toString();
    }
}
