package top.ashun.myspringmvc;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import top.ashun.myspringmvc.core.ControllerScanner;
import top.ashun.myspringmvc.core.DispatcherServlet;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author 18483
 */
public class Application {

    public static void main(String[] args) throws LifecycleException, IOException {
        //扫描所有controller类和带有@RequestMapping注解的方法
        ControllerScanner.rawScanMethods();

        Path tempBaseDir = Files.createTempDirectory("tomcat-temp-base-dir");
        // 创建临时目录作为应用文档资源的目录
        Path tempDocDir = Files.createTempDirectory("tomcat-temp-doc-dir");

        Tomcat tomcat = new Tomcat();
        Connector connector = new Connector();
        // 设置绑定端口
        connector.setPort(8080);
        tomcat.getService().addConnector(connector);
        tomcat.setConnector(connector);
        tomcat.getHost().setAutoDeploy(false);
        tomcat.setBaseDir(tempBaseDir.toFile().getAbsolutePath());

        // 创建应用上下文
        String contextPath = "";
        StandardContext context = (StandardContext) tomcat.addWebapp(contextPath, tempDocDir.toFile().getAbsolutePath());

        tomcat.addServlet(contextPath, "dispatcherServlet", DispatcherServlet.class.getName());
        //注意不要忘记设置Servlet路径映射
        context.addServletMappingDecoded("/*", "dispatcherServlet");
        tomcat.start();
        tomcat.getServer().await();
    }
}
