package com.ruoyi.process.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.process.mapper.BizOrderVendorMapper;
import com.ruoyi.process.domain.BizOrderVendor;
import com.ruoyi.process.service.IBizOrderVendorService;
import com.ruoyi.common.core.text.Convert;

/**
 * 订单报数Service业务层处理
 *
 * @author Powerjun Tech
 * @date 2021-06-01
 */
@Service
public class BizOrderVendorServiceImpl implements IBizOrderVendorService {
    @Autowired
    private BizOrderVendorMapper bizOrderVendorMapper;

    /**
     * 查询订单报数
     *
     * @param id 订单报数ID
     * @return 订单报数
     */
    @Override
    public BizOrderVendor selectBizOrderVendorById(Long id) {
        return bizOrderVendorMapper.selectBizOrderVendorById(id);
    }

    /**
     * 查询订单报数列表
     *
     * @param bizOrderVendor 订单报数
     * @return 订单报数
     */
    @Override
    public List<BizOrderVendor> selectBizOrderVendorList(BizOrderVendor bizOrderVendor) {
        return bizOrderVendorMapper.selectBizOrderVendorList(bizOrderVendor);
    }

    /**
     * 新增订单报数
     *
     * @param bizOrderVendor 订单报数
     * @return 结果
     */
    @Override
    public int insertBizOrderVendor(BizOrderVendor bizOrderVendor) {
        bizOrderVendor.setCreateTime(DateUtils.getNowDate());
        return bizOrderVendorMapper.insertBizOrderVendor(bizOrderVendor);
    }

    /**
     * 修改订单报数
     *
     * @param bizOrderVendor 订单报数
     * @return 结果
     */
    @Override
    public int updateBizOrderVendor(BizOrderVendor bizOrderVendor) {
        bizOrderVendor.setUpdateTime(DateUtils.getNowDate());
        return bizOrderVendorMapper.updateBizOrderVendor(bizOrderVendor);
    }

    /**
     * 删除订单报数对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteBizOrderVendorByIds(String ids) {
        return bizOrderVendorMapper.deleteBizOrderVendorByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除订单报数信息
     *
     * @param id 订单报数ID
     * @return 结果
     */
    @Override
    public int deleteBizOrderVendorById(Long id) {
        return bizOrderVendorMapper.deleteBizOrderVendorById(id);
    }

    @Override
    public List<BizOrderVendor> selectBizOrderVendorList(String orderId) {

        BizOrderVendor bizOrderVendor = new BizOrderVendor();
        bizOrderVendor.setOrderId(orderId);
        return selectBizOrderVendorList(bizOrderVendor);
    }
}
