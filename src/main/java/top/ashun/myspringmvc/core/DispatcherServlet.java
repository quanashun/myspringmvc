package top.ashun.myspringmvc.core;

import lombok.extern.slf4j.Slf4j;
import top.ashun.myspringmvc.dto.UserDTO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;


/**
 * @author 18483
 */
@Slf4j
public class DispatcherServlet extends HttpServlet {
    public static final ThreadLocal<UserDTO> threadLocalUserDTO = new ThreadLocal<>();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserDTO userDTO = new UserDTO();
        //测试用
//        userDTO.setUserName("qks");
        // 根据请求设置UserDTO，为threadLocalUserDTO设置值
        if("true".equals(request.getAttribute("isLogin"))){
            userDTO.setUserName((String) request.getAttribute("userName"));
            userDTO.setUserId((String) request.getAttribute("userId"));
            userDTO.setEmail((String) request.getAttribute("email"));
        }
        threadLocalUserDTO.set(userDTO);
//        System.out.println(threadLocalUserDTO.get().getUserName());
        log.info("{}  URL:{}  userId:{}", threadLocalUserDTO.get().getUserName(),request.getRequestURI(),userDTO.getUserId());

        Object result;
        Handler handler = HandlerMap.getHandler(request.getRequestURI(), request.getMethod());
        if(handler != null) {
            try {
                result = handler.getObject().getClass().getMethod(handler.getMethodName()).invoke(handler.getObject());
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
            if(result == null) {
                PrintWriter writer = response.getWriter();
                writer.write("null");
                writer.flush();
                writer.close();
            }else if(result instanceof Integer){
                PrintWriter writer = response.getWriter();
                writer.write(String.valueOf(request));
                writer.flush();
                writer.close();
            }
            if(result instanceof String) {
                PrintWriter writer = response.getWriter();
                writer.write((String) result);
                writer.flush();
                writer.close();
            }
        }
        PrintWriter writer = response.getWriter();
        writer.write("404");
        writer.flush();
        writer.close();
    }
}
