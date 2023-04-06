package top.ashun.myspringmvc.core;

import org.reflections.Reflections;
import top.ashun.myspringmvc.annotation.Controller;
import top.ashun.myspringmvc.annotation.RequestMapping;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author 18483
 */
public class ControllerScanner {
    public static List<Class<?>> scanControllers() {
        Reflections reflections = new Reflections("");
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Controller.class);
        List<Class<?>> controllers = new ArrayList<>();
        for (Class<?> clazz : annotated) {
            controllers.add(clazz);
        }
        return controllers;
    }
    public static void rawScanMethods(){
        rawScanMethods("");
    }

    public static void rawScanMethods(String packageName) {
        // 指定包名
        String packagePath = packageName.replace('.', File.separatorChar);
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            java.net.URL resource = classLoader.getResource(packagePath);
            java.nio.file.Path path = java.nio.file.Paths.get(resource.toURI());
            java.nio.file.Files.walk(path).filter(p -> p.toString().endsWith(".class")).forEach(p -> {
                String className = p.toString().substring(path.toString().length() + 1, p.toString().length() - 6).replace(File.separatorChar, '.');
                try {
                    Class<?> clazz = classLoader.loadClass(className);
                    if (!clazz.isAnnotation() && clazz.isAnnotationPresent(Controller.class)) {
                        for (Method method : clazz.getDeclaredMethods()) {
                            if (method.isAnnotationPresent(RequestMapping.class)) {
                                RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                                String requestPath = "".equals(requestMapping.path()) ? requestMapping.value() : requestMapping.path();
                                String requestType = requestMapping.method();
                                HandlerMap.putHandler(requestPath, new Handler(clazz.newInstance(), method.getName()), requestType);
                            }
                        }
                    }
                } catch (ClassNotFoundException e) {
                    // 忽略类不存在的情况
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (Exception e) {
            // 忽略异常
        }
    }
}
