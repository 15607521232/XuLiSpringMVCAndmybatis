package cn.itcast.ssm.controller;

import cn.itcast.ssm.po.ItemsCustom;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

/**
 * Json数据在SpringMVC中的使用
 */


@Controller
public class JsonTest {
    //请求json串（商品信息），输出json串(商品信息)

   /* @RequestBody将请求的商品信息的json串转成itemsCustom对象
    @ResponseBody将itemsCustom转成json输出*/

    @RequestMapping("/requestJson")
    public  @ResponseBody ItemsCustom requestJson(@RequestBody ItemsCustom itemsCustom){

        //@ResponseBody将itemsCustom转成json输出
        return itemsCustom;

    }


    @RequestMapping("/responsetJson")
    public  @ResponseBody ItemsCustom responsetJson( ItemsCustom itemsCustom){

        //@ResponseBody将itemsCustom转成json输出
        return itemsCustom;

    }





}
