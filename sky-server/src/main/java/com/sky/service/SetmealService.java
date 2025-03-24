package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.SetmealVO;

import java.util.List;

public interface SetmealService {
    /**
     * 启用或停用套餐
     * @param status
     * @param id
     */
    void startOrStop(Integer status, Long id);

    /**
     * 新增套餐
     * @param setmealDTO
     */
    void saveWithDish(SetmealDTO setmealDTO);

    /**
     * 套餐分页查询
     * @param setmealPageQueryDTO
     * @return
     */
    PageResult pageQuary(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * 删除套餐
     * @param ids
     */
    void delete(List<Long> ids);

    /**
     * 修改套餐信息
     * @param setmealDTO
     */
    void update(SetmealDTO setmealDTO);

    /**
     * 根据id查询套餐信息
     * @param id
     * @return
     */
    SetmealVO getById(Long id);
}
