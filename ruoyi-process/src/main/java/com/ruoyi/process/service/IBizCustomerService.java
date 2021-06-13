package com.ruoyi.process.service;

import com.ruoyi.process.domain.BizCustomer;
import java.util.List;

/**
 * 客户信息Service接口
 *
 * @author Xianlu Tech
 * @date 2021-06-13
 */
public interface IBizCustomerService {
    /**
     * 查询客户信息
     *
     * @param id 客户信息ID
     * @return 客户信息
     */
    public BizCustomer selectBizCustomerById(Long id);

    /**
     * 查询客户信息列表
     *
     * @param bizCustomer 客户信息
     * @return 客户信息集合
     */
    public List<BizCustomer> selectBizCustomerList(BizCustomer bizCustomer);

    /**
     * 新增客户信息
     *
     * @param bizCustomer 客户信息
     * @return 结果
     */
    public int insertBizCustomer(BizCustomer bizCustomer);

    /**
     * 修改客户信息
     *
     * @param bizCustomer 客户信息
     * @return 结果
     */
    public int updateBizCustomer(BizCustomer bizCustomer);

    /**
     * 批量删除客户信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBizCustomerByIds(String ids);

    /**
     * 删除客户信息信息
     *
     * @param id 客户信息ID
     * @return 结果
     */
    public int deleteBizCustomerById(Long id);
}
