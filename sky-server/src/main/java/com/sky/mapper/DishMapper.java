package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DishMapper {

    /**
     * 分页查询菜品
     * @param dishPageQueryDTO
     * @return
     */
    Page<DishVO> pageQuary(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 根据分类id查询菜品数量
     * @param categoryId
     * @return
     */
    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);

    /**
     * 插入菜品数据
     * @param dish
     */
    @AutoFill(value = OperationType.INSERT)
    void insert(Dish dish);

    /**
     * 根据主键查询菜品
     * @param id
     * @return
     */
    @Select("select * from dish where id=#{id}")
    Dish getById(Long id);

    /**
     * 根据主键删除菜品
     * @param id
     */
    @Delete("delete from dish where id=#{id}")
    void deleteById(Long id);

    /**
     * 修改菜品信息
     * @param dish
     */
    void update(Dish dish);
}
