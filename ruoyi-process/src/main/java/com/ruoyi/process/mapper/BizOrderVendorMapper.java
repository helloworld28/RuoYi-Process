package com.ruoyi.process.mapper;

import com.ruoyi.process.domain.BizOrderVendor;
import java.util.List;

/**
 * 订单供应商信息Mapper接口
 *
 * @author Xianlu Tech
 * @date 2021-06-13
 */
public interface BizOrderVendorMapper {
    /**
     * 查询订单供应商信息
     *
     * @param id 订单供应商信息ID
     * @return 订单供应商信息
     */
    public BizOrderVendor selectBizOrderVendorById(Long id);

    /**
     * 查询订单供应商信息列表
     *
     * @param bizOrderVendor 订单供应商信息
     * @return 订单供应商信息集合
     */
    public List<BizOrderVendor> selectBizOrderVendorList(BizOrderVendor bizOrderVendor);

    /**
     * 新增订单供应商信息
     *
     * @param bizOrderVendor 订单供应商信息
     * @return 结果
     */
    public int insertBizOrderVendor(BizOrderVendor bizOrderVendor);

    /**
     * 修改订单供应商信息
     *
     * @param bizOrderVendor 订单供应商信息
     * @return 结果
     */
    public int updateBizOrderVendor(BizOrderVendor bizOrderVendor);

    /**
     * 删除订单供应商信息
     *
     * @param id 订单供应商信息ID
     * @return 结果
     */
    public int deleteBizOrderVendorById(Long id);

    /**
     * 批量删除订单供应商信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBizOrderVendorByIds(String[] ids);
}
