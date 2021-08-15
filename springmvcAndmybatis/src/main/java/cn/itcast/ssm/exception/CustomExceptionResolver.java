package cn.itcast.ssm.exception;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统抛出的异常
 */


public class CustomExceptionResolver  implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        //Object 就是处理器适配器要执行的handle对象(只有method)

        //解析出异常类型
        //如果该 异常类型是系统 自定义的异常，直接取出异常信息，在错误页面展示
        //如果该 异常类型不是系统 自定义的异常，构造一个自定义的异常类型（信息为“未知错误”）
        CustomException customException = null;
        if(e instanceof CustomException){
            customException = (CustomException) e;
        }else {
            customException = new CustomException("位置错误");
        }

        //错误信息
        String message = customException.getMessage();

        ModelAndView modelAndView = new ModelAndView();

        //将错误信息传到页面
        modelAndView.addObject("customException",customException);

        //指向错误页面
        modelAndView.setViewName("/exception/errors");







        return null;
    }
}
