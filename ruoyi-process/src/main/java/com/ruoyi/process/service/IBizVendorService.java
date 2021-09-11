package com.ruoyi.process.service;

import com.ruoyi.process.domain.BizVendor;
import com.ruoyi.process.domain.BizVendorVo;

import java.util.List;

/**
 * 供应商信息Service接口
 *
 * @author Xianlu Tech
 * @date 2021-09-11
 */
public interface IBizVendorService {
    /**
     * 查询供应商信息
     *
     * @param id 供应商信息ID
     * @return 供应商信息
     */
    public BizVendor selectBizVendorById(Long id);

    /**
     * 查询供应商信息列表
     *
     * @param bizVendor 供应商信息
     * @return 供应商信息集合
     */
    public List<BizVendor> selectBizVendorList(BizVendor bizVendor);

    List<BizVendorVo> selectBizVendorListWithCustomerId(BizVendorVo bizVendorVo);

    /**
     * 新增供应商信息
     *
     * @param bizVendor 供应商信息
     * @return 结果
     */
    public int insertBizVendor(BizVendor bizVendor);

    /**
     * 修改供应商信息
     *
     * @param bizVendor 供应商信息
     * @return 结果
     */
    public int updateBizVendor(BizVendor bizVendor);

    /**
     * 批量删除供应商信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBizVendorByIds(String ids);

    /**
     * 删除供应商信息信息
     *
     * @param id 供应商信息ID
     * @return 结果
     */
    public int deleteBizVendorById(Long id);
}
