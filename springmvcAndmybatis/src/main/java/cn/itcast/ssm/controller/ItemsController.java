package cn.itcast.ssm.controller;

import cn.itcast.ssm.po.ItemsCustom;
import cn.itcast.ssm.service.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;



@Controller
//为了对url进行分类管理，可以在这里定义根路径，最终访问的路径就是根路劲+子路径
//可以限制http的请求方式
@RequestMapping(value = "/items")
public class ItemsController {


    @Autowired
    private ItemsService itemsService;


    //商品查询
    @RequestMapping(value = "/queryItems",method = {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView queryItems(HttpServletRequest request) throws Exception {

        request.getParameter("id");

        //调用service查找数据库，查询商品列表
        List<ItemsCustom> itemsCustomList = itemsService.findItemsList(null);


        //返回ModelAndView
        ModelAndView modelAndView = new ModelAndView();

        //相当于reequest中的setattribute,在jsp页面中通过itemList取数据
        modelAndView.addObject("itemsList",itemsCustomList);

        modelAndView.setViewName("items/itemsList");

        return modelAndView;
    }

    //商品信息修改页面  返回ModelAndView的方式
    /*@RequestMapping("/editItems")
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



    }*/

    //商品信息修改页面  返回String的方式
    //@RequestParam里面指定request传入参数名称和形参进行绑定  required表示参数必填  defaultValue默认参数
    @RequestMapping("/editItems")
    public String editItems(Model model,@RequestParam(value = "id",required = true) Integer items_id) throws Exception{
        //调用service根据商品id查询商品信息
        ItemsCustom itemsCustom = itemsService.findItemsById(items_id);

        //返回ModelAndView
        //ModelAndView modelAndView = new ModelAndView();

        //添加商品信息到ModelAndView
        //modelAndView.addObject("itemsCustom",itemsCustom);

        //商品信息插入到形参
        model.addAttribute("itemsCustom",itemsCustom);

        //商品修改页面
        //modelAndView.setViewName("/items/editItems");
        //返回逻辑视图名称
        return "/items/editItems";



    }




    //商品信息修改提交
    @RequestMapping("/editItemsSubmit")
    public String editItemsSubmit(HttpServletRequest request,Integer id, ItemsCustom itemsCustom) throws Exception{



        //调用service更新商品信息，页面需要将商品信息传到此方法
        itemsService.updateItems(id, itemsCustom);

        //重定向到商品查询列表页面

        //return "redirect:queryItems.action";

        //页面转发
        //return "forward:queryItems.action";

        return "/items/success";
    }

}
