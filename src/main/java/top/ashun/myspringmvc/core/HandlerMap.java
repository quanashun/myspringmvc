package top.ashun.myspringmvc.core;

import top.ashun.myspringmvc.enums.RequestMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 18483
 */
public class HandlerMap {
    private static Map<String, Handler> getHandlerMap = new HashMap<>();
    private static Map<String, Handler> postHandlerMap = new HashMap<>();

    public static Handler getHandler(String path,String method){
        if(RequestMethod.GET.equals(method)){
            return getHandlerMap.get(path);
        }else if(RequestMethod.POST.equals(method)){
            return postHandlerMap.get(path);
        }
        return null;
    }

    public static void putHandler(String path, Handler handler,String method){
        if(RequestMethod.GET.equals(method)){
             getHandlerMap.put(path,handler);
        }else if(RequestMethod.POST.equals(method)){
             postHandlerMap.put(path,handler);
        }
    }
}
