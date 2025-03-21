package com.sky.mapper;
import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DishFlavorMapper {
    /**
     * 批量插入口味数据
     * @param flavors
     */
    void insertbatch(List<DishFlavor> flavors);

    /**
     * 根据菜品id删除口味数据
     * @param dishId
     */
    @Delete("delete from dish_Flavor where dish_id=#{dishId}")
    void deleteByDishId(Long dishId);
}

