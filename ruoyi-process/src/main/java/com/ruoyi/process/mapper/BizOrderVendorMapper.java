package com.ruoyi.process.mapper;

import com.ruoyi.process.domain.BizOrderVendor;
import java.util.List;

/**
 * 订单报数Mapper接口
 *
 * @author Powerjun Tech
 * @date 2021-06-01
 */
public interface BizOrderVendorMapper {
    /**
     * 查询订单报数
     *
     * @param id 订单报数ID
     * @return 订单报数
     */
    public BizOrderVendor selectBizOrderVendorById(Long id);

    /**
     * 查询订单报数列表
     *
     * @param bizOrderVendor 订单报数
     * @return 订单报数集合
     */
    public List<BizOrderVendor> selectBizOrderVendorList(BizOrderVendor bizOrderVendor);

    /**
     * 新增订单报数
     *
     * @param bizOrderVendor 订单报数
     * @return 结果
     */
    public int insertBizOrderVendor(BizOrderVendor bizOrderVendor);

    /**
     * 修改订单报数
     *
     * @param bizOrderVendor 订单报数
     * @return 结果
     */
    public int updateBizOrderVendor(BizOrderVendor bizOrderVendor);

    /**
     * 删除订单报数
     *
     * @param id 订单报数ID
     * @return 结果
     */
    public int deleteBizOrderVendorById(Long id);

    /**
     * 批量删除订单报数
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBizOrderVendorByIds(String[] ids);
}
