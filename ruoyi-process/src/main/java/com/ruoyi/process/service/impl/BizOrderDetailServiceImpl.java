package com.ruoyi.process.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.process.mapper.BizOrderDetailMapper;
import com.ruoyi.process.domain.BizOrderDetail;
import com.ruoyi.process.service.IBizOrderDetailService;
import com.ruoyi.common.core.text.Convert;

/**
 * 订单明细Service业务层处理
 *
 * @author Xianlu Tech
 * @date 2021-06-19
 */
@Service
public class BizOrderDetailServiceImpl implements IBizOrderDetailService {
    @Autowired
    private BizOrderDetailMapper bizOrderDetailMapper;

    /**
     * 查询订单明细
     *
     * @param id 订单明细ID
     * @return 订单明细
     */
    @Override
    public BizOrderDetail selectBizOrderDetailById(Long id) {
        return bizOrderDetailMapper.selectBizOrderDetailById(id);
    }

    /**
     * 查询订单明细列表
     *
     * @param bizOrderDetail 订单明细
     * @return 订单明细
     */
    @Override
    public List<BizOrderDetail> selectBizOrderDetailList(BizOrderDetail bizOrderDetail) {
        return bizOrderDetailMapper.selectBizOrderDetailList(bizOrderDetail);
    }

    @Override
    public List<BizOrderDetail> selectBizOrderDetailList(String orderId) {
        BizOrderDetail bizOrderDetail = new BizOrderDetail();
        bizOrderDetail.setOrderId(orderId);
        return selectBizOrderDetailList(bizOrderDetail);
    }

    /**
     * 新增订单明细
     *
     * @param bizOrderDetail 订单明细
     * @return 结果
     */
    @Override
    public int insertBizOrderDetail(BizOrderDetail bizOrderDetail) {
        bizOrderDetail.setCreateTime(DateUtils.getNowDate());
        return bizOrderDetailMapper.insertBizOrderDetail(bizOrderDetail);
    }

    /**
     * 修改订单明细
     *
     * @param bizOrderDetail 订单明细
     * @return 结果
     */
    @Override
    public int updateBizOrderDetail(BizOrderDetail bizOrderDetail) {
        bizOrderDetail.setUpdateTime(DateUtils.getNowDate());
        return bizOrderDetailMapper.updateBizOrderDetail(bizOrderDetail);
    }

    /**
     * 删除订单明细对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteBizOrderDetailByIds(String ids) {
        return bizOrderDetailMapper.deleteBizOrderDetailByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除订单明细信息
     *
     * @param id 订单明细ID
     * @return 结果
     */
    @Override
    public int deleteBizOrderDetailById(Long id) {
        return bizOrderDetailMapper.deleteBizOrderDetailById(id);
    }
}
