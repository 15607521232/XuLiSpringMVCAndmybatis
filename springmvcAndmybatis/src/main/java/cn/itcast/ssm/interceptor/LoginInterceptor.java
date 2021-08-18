package cn.itcast.ssm.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor  implements HandlerInterceptor {

    //进入handler方法之前执行
    //用于身份认证身份授权
    //比如身份认证、如果认证不通过表示当前用户没有登录，需要此方法拦截不再向下执行


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //获取请求的url
        String url = request.getRequestURI();

        //判断url是否是公开地址
        if(url.indexOf("login.action")>=0){


            //如果是登录提交 放行
            return true;
        }

        //判断session
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        if(username != null){
            return true;
        }

        //执行这里表示用户身份需要认证，跳转到登录页面
        request.getRequestDispatcher("/WEB-INF/jsp/login/login.jsp").forward(request,response);

        //表示拦截
        return false;
    }


    //进入Handler方法之后，返回modelAndView之前执行
    //应用场景从modelAndView出发：将公用的模型数据(比如菜单导航)在这里传到视图，也可以在这里统一指定视图
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("LoginInterceptor postHandle ");
    }


    //执行Handler完成执行此方法
    //应用场景：统一异常处理，统一日志处理
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("LoginInterceptor  afterCompletion");
    }
}
