package com.ruoyi.process.mapper;

import com.ruoyi.process.domain.BizVendor;
import java.util.List;

/**
 * 供应商信息Mapper接口
 *
 * @author Xianlu Tech
 * @date 2021-06-01
 */
public interface BizVendorMapper {
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
     * 删除供应商信息
     *
     * @param id 供应商信息ID
     * @return 结果
     */
    public int deleteBizVendorById(Long id);

    /**
     * 批量删除供应商信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBizVendorByIds(String[] ids);
}