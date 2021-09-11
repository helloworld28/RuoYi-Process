package com.ruoyi.process.service;

import com.ruoyi.process.domain.BizOrderDetail;
import java.util.List;

/**
 * 订单明细Service接口
 *
 * @author Xianlu Tech
 * @date 2021-06-19
 */
public interface IBizOrderDetailService {
    /**
     * 查询订单明细
     *
     * @param id 订单明细ID
     * @return 订单明细
     */
    public BizOrderDetail selectBizOrderDetailById(Long id);

    /**
     * 查询订单明细列表
     *
     * @param bizOrderDetail 订单明细
     * @return 订单明细集合
     */
    public List<BizOrderDetail> selectBizOrderDetailList(BizOrderDetail bizOrderDetail);

    public List<BizOrderDetail> selectBizOrderDetailList(String orderId);

    /**
     * 新增订单明细
     *
     * @param bizOrderDetail 订单明细
     * @return 结果
     */
    public int insertBizOrderDetail(BizOrderDetail bizOrderDetail);

    /**
     * 修改订单明细
     *
     * @param bizOrderDetail 订单明细
     * @return 结果
     */
    public int updateBizOrderDetail(BizOrderDetail bizOrderDetail);

    /**
     * 批量删除订单明细
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBizOrderDetailByIds(String ids);

    /**
     * 删除订单明细信息
     *
     * @param id 订单明细ID
     * @return 结果
     */
    public int deleteBizOrderDetailById(Long id);

}
