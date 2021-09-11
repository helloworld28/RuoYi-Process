package com.ruoyi.process.order.mapper;

import com.ruoyi.process.order.domain.BizOrder;

import java.util.List;

/**
 * 订单Mapper接口
 *
 * @author PowerJun Tech
 * @date 2021-05-20
 */
public interface BizOrderMapper {
    /**
     * 查询订单
     *
     * @param id 订单ID
     * @return 订单
     */
    public BizOrder selectBizOrderById(Long id);

    /**
     * 查询订单
     *
     * @param orderId 订单ID
     * @return 订单
     */
    public BizOrder selectBizOrderByOrderId(String orderId);


    /**
     * 查询订单列表
     *
     * @param bizOrder 订单
     * @return 订单集合
     */
    public List<BizOrder> selectBizOrderList(BizOrder bizOrder);

    /**
     * 新增订单
     *
     * @param bizOrder 订单
     * @return 结果
     */
    public int insertBizOrder(BizOrder bizOrder);

    /**
     * 修改订单
     *
     * @param bizOrder 订单
     * @return 结果
     */
    public int updateBizOrder(BizOrder bizOrder);

    /**
     * 删除订单
     *
     * @param id 订单ID
     * @return 结果
     */
    public int deleteBizOrderById(Long id);

    /**
     * 批量删除订单
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBizOrderByIds(String[] ids);
}
