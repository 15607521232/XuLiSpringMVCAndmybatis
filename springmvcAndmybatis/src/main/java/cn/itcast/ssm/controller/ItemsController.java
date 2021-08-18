package cn.itcast.ssm.controller;

import cn.itcast.ssm.controller.validation.ValidGroup1;
import cn.itcast.ssm.exception.CustomException;
import cn.itcast.ssm.po.ItemsCustom;
import cn.itcast.ssm.po.ItemsQueryVo;
import cn.itcast.ssm.service.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Controller
//为了对url进行分类管理，可以在这里定义根路径，最终访问的路径就是根路劲+子路径
//可以限制http的请求方式
@RequestMapping(value = "/items")
public class ItemsController {


    @Autowired
    private ItemsService itemsService;

    //商品分类
    //itemtypes表示最终将方法返回值放在request中的key
    @ModelAttribute("itemtypes")
    public Map<String,String> getItemTypes(){
        Map<String,String> itemTypes = new HashMap<String, String>();
        itemTypes.put("101", "数码");
        itemTypes.put("102", "母婴");
        return itemTypes;
    }


    //商品查询
    @RequestMapping(value = "/queryItems",method = {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView queryItems(HttpServletRequest request, ItemsQueryVo itemsQueryVo) throws Exception {


        System.out.println(request.getParameter("id"));

        //调用service查找数据库，查询商品列表
        List<ItemsCustom> itemsCustomList = itemsService.findItemsList(itemsQueryVo);


        //返回ModelAndView
        ModelAndView modelAndView = new ModelAndView();

        //相当于reequest中的setattribute,在jsp页面中通过itemList取数据
        modelAndView.addObject("itemsList",itemsCustomList);

        modelAndView.setViewName("items/itemsList");

        return modelAndView;
    }

    //查询商品信息 输出json
    //itemsView/{id}中的id表示占位符，通过@PathVariable获取占位符中的参数，如果占位符中的名称和形参名一致，在@PathVariable可以不指定名称

    @RequestMapping("/itemsView/{id}")
    public @ResponseBody ItemsCustom itemsView(@PathVariable("id") Integer id) throws Exception{

        //调用service查询商品信息
        ItemsCustom itemsCustom = itemsService.findItemsById(id);
        return itemsCustom;
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

        //判断商品是否为空，根据id没有差选到商品，抛出异常，提示用户商品信息不存在
        if(itemsCustom == null){
            throw new CustomException("修改的商品信息不存在");
        }

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
    public String editItemsSubmit(Model model, HttpServletRequest request, Integer id,
                                  @Validated(value = ValidGroup1.class) ItemsCustom itemsCustom,
                                  BindingResult bindingResult, MultipartFile items_pic) throws Exception{
        //MultipartFile items_pic 接收商品图片


        //在需要校验的pojo前面添加@Validated 在需要校验的pojo后边添加BindingResult 接收校验出错的消息
        //@Validated和BindingResult bindingResult是配对出现，并且形参顺序是固定的（一前一后）。

        //获取校验错误信息
        if(bindingResult.hasErrors()){
            List<ObjectError> allErrors = bindingResult.getAllErrors();

            for (ObjectError objectError:allErrors){
                System.out.println(objectError.getDefaultMessage());
            }

            //将错误信息传到页面
            model.addAttribute("allErrors",allErrors);

            //可以直接使用model将提交pojo回显到页面
            model.addAttribute("items",itemsCustom);

            //出错重回到商品修改页面
            return "/items/editItems";

        }

        //原始名称
        String originaFilename = items_pic.getOriginalFilename();

        //上传图片
        if(items_pic != null && originaFilename != null && originaFilename.length()>0){
            //存储图片额物理路径
            String pic_path = "F:\\develop\\upload\\temp\\";


           /* public static void main(String[] args) {
                String s = "liguang.jpg";
                String name = UUID.randomUUID() +s.substring(s.lastIndexOf("."));
                System.out.println(name);

            }*/
            //新的图片名称
            String newFileName = UUID.randomUUID() + originaFilename.substring(originaFilename.lastIndexOf("."));

            //新图片
            File newFile = new File(pic_path + newFileName);

            //将内存中的数据写入磁盘
            items_pic.transferTo(newFile);
            //将新图片名称写到itemsCustom中
            itemsCustom.setPic(newFileName);


        }

        //调用service更新商品信息，页面需要将商品信息传到此方法
        itemsService.updateItems(id, itemsCustom);

        //重定向到商品查询列表页面

        //return "redirect:queryItems.action";

        //页面转发
        //return "forward:queryItems.action";

        return "/items/success";
    }


    //批量删除商品信息
    @RequestMapping("/deleteItems")
    public String deleteItems(Integer[] items_id) throws Exception{

        //调用service批量删除商品
        //service 服务暂时省略
        return "/items/success";

    }

    //批量修改商品页面，将商品信息查询出来，在页面中可以编辑商品信息

    @RequestMapping("/editItemsQuery")
    public ModelAndView editItemsQuery(HttpServletRequest request,ItemsQueryVo itemsQueryVo) throws Exception{
        //调用service查找数据库，查询商品列表
        List<ItemsCustom> itemsList = itemsService.findItemsList(itemsQueryVo);


        //返回ModelAndView
        ModelAndView modelAndView = new ModelAndView();
        //相当于request的setAttribute
        modelAndView.addObject("itemsList",itemsList);

        modelAndView.setViewName("/items/editItemsQuery");

        return modelAndView;
    }

    //批量修改商品提交
    //通过ItemQueryVo接收批量提交的商品信息，将商品信息存储到itemsQueryVo中itemsList属性中
    @RequestMapping("/editItemsAllSubmit")
    public String editItemsAllSubmit(ItemsQueryVo itemsQueryVo) throws Exception{


        return "/items/success";
    }


}
