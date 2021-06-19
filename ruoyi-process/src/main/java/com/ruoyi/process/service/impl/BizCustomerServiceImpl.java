package com.ruoyi.process.service.impl;

import java.util.List;

import cn.hutool.extra.pinyin.PinyinUtil;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.process.mapper.BizCustomerMapper;
import com.ruoyi.process.domain.BizCustomer;
import com.ruoyi.process.service.IBizCustomerService;
import com.ruoyi.common.core.text.Convert;

/**
 * 客户信息Service业务层处理
 *
 * @author Xianlu Tech
 * @date 2021-06-13
 */
@Service
public class BizCustomerServiceImpl implements IBizCustomerService {
    @Autowired
    private BizCustomerMapper bizCustomerMapper;

    /**
     * 查询客户信息
     *
     * @param id 客户信息ID
     * @return 客户信息
     */
    @Override
    public BizCustomer selectBizCustomerById(Long id) {
        return bizCustomerMapper.selectBizCustomerById(id);
    }

    /**
     * 查询客户信息列表
     *
     * @param bizCustomer 客户信息
     * @return 客户信息
     */
    @Override
    public List<BizCustomer> selectBizCustomerList(BizCustomer bizCustomer) {
        return bizCustomerMapper.selectBizCustomerList(bizCustomer);
    }

    /**
     * 新增客户信息
     *
     * @param bizCustomer 客户信息
     * @return 结果
     */
    @Override
    public int insertBizCustomer(BizCustomer bizCustomer) {
        bizCustomer.setCreateTime(DateUtils.getNowDate());
        if (StringUtils.isBlank(bizCustomer.getCustomerId())) {
            String pinyin = PinyinUtil.getPinyin(bizCustomer.getName(), "");
            String lastPhoneNo = bizCustomer.getPhone().substring(5);
            bizCustomer.setCustomerId(pinyin + lastPhoneNo);
        }
        return bizCustomerMapper.insertBizCustomer(bizCustomer);
    }

    /**
     * 修改客户信息
     *
     * @param bizCustomer 客户信息
     * @return 结果
     */
    @Override
    public int updateBizCustomer(BizCustomer bizCustomer) {
        bizCustomer.setUpdateTime(DateUtils.getNowDate());
        return bizCustomerMapper.updateBizCustomer(bizCustomer);
    }

    /**
     * 删除客户信息对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteBizCustomerByIds(String ids) {
        return bizCustomerMapper.deleteBizCustomerByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除客户信息信息
     *
     * @param id 客户信息ID
     * @return 结果
     */
    @Override
    public int deleteBizCustomerById(Long id) {
        return bizCustomerMapper.deleteBizCustomerById(id);
    }
}
