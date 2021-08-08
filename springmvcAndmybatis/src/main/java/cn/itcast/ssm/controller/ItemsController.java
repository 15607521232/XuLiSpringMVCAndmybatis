package cn.itcast.ssm.controller;

import cn.itcast.ssm.po.ItemsCustom;
import cn.itcast.ssm.service.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;



@Controller
public class ItemsController {


    @Autowired
    private ItemsService itemsService;


    //商品查询
    @RequestMapping("/queryItems")
    public ModelAndView queryItems() throws Exception {

        //调用service查找数据库，查询商品列表
        List<ItemsCustom> itemsCustomList = itemsService.findItemsList(null);


        //返回ModelAndView
        ModelAndView modelAndView = new ModelAndView();

        //相当于reequest中的setattribute,在jsp页面中通过itemList取数据
        modelAndView.addObject("itemsList",itemsCustomList);

        modelAndView.setViewName("items/itemsList");

        return modelAndView;
    }

    //商品信息修改页面
    @RequestMapping("/editItems")
    public ModelAndView editItems() throws Exception{
        //调用service根据商品id查询商品信息
        ItemsCustom itemsCustom = itemsService.findItemsById(1);

        //返回ModelAndView
        ModelAndView modelAndView = new ModelAndView();

        //添加商品信息到ModelAndView
        modelAndView.addObject("itemsCustom",itemsCustom);

        //商品修改页面
        modelAndView.setViewName("/items/editItems");

        return modelAndView;



    }


    //商品信息修改提交
    @RequestMapping("/editItemsSubmit")
    public String editItemsSubmit(Integer id, ItemsCustom itemsCustom) throws Exception{
        //调用service更新商品信息，页面需要将商品信息传到此方法
        itemsService.updateItems(id, itemsCustom);

        return "success";
    }

}
