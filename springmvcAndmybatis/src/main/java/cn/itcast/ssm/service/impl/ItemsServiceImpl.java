package cn.itcast.ssm.service.impl;

import java.util.List;

import cn.itcast.ssm.exception.CustomException;
import cn.itcast.ssm.mapper.ItemsMapper;
import cn.itcast.ssm.po.Items;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import cn.itcast.ssm.mapper.ItemsMapperCustom;
import cn.itcast.ssm.po.ItemsCustom;
import cn.itcast.ssm.po.ItemsQueryVo;
import cn.itcast.ssm.service.ItemsService;

/**
 * 
 * <p>Title: ItemsServiceImpl</p>
 * <p>Description: 商品管理</p>
 *
 */
public class ItemsServiceImpl implements ItemsService{
	
	@Autowired
	private ItemsMapperCustom itemsMapperCustom;


	@Autowired
	private ItemsMapper itemsMapper;


	@Override
	public List<ItemsCustom> findItemsList(ItemsQueryVo itemsQueryVo)
			throws Exception {
		//通过ItemsMapperCustom查询数据库
		return itemsMapperCustom.findItemsList(itemsQueryVo);
	}

	@Override
	public ItemsCustom findItemsById(Integer id) throws Exception {
		Items items = itemsMapper.selectByPrimaryKey(id);

		//判断商品是否为空，根据id没有差选到商品，抛出异常，提示用户商品信息不存在
		if(items == null){
			throw new CustomException("修改的商品信息不存在");
		}

		//中间对商品信息进行业务处理
		// ......
		// 返回ItemsCustom
		ItemsCustom itemsCustom = new ItemsCustom();

		//将Items的属性值拷贝到itemsCustom
		BeanUtils.copyProperties(items,itemsCustom);


		return itemsCustom;
	}

	@Override
	public void updateItems(Integer id, ItemsCustom itemsCustom) throws Exception {

		//添加业务校验，通常在service接口对关键参数进行校验
		//例如校验id是否为空，通常为空抛出异常  更新商品信息使用updateByPrimaryKeyWithBLOBs更新items表中所有字段，包括大文本类型字段
		itemsCustom.setId(id);
		itemsMapper.updateByPrimaryKeyWithBLOBs(itemsCustom);



	}
}
