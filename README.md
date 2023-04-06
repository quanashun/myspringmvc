手写SpringMVC

主要实现了：
1. 内嵌Tomcat，无需打war包
2. 自定义@Controller和@RequestMapping注解，启动时自动扫描加载
3. 全局只有一个DispatcherServlet，根据请求方法和路径转发到指定到自定义方法

未实现的：
1. 解析HttpServletRequest的参数并在需要时传递给自定义方法；解析自定义方法的返回值并包装成HttpServletResponse
2. 拦截器

<a href="http://ashun.top">欢迎大家来我的博客阅读具体实现思路</a>