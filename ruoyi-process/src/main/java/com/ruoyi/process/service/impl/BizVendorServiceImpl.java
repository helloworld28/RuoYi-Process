package com.ruoyi.process.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.process.domain.BizVendorVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.process.mapper.BizVendorMapper;
import com.ruoyi.process.domain.BizVendor;
import com.ruoyi.process.service.IBizVendorService;
import com.ruoyi.common.core.text.Convert;

/**
 * 供应商信息Service业务层处理
 *
 * @author Xianlu Tech
 * @date 2021-09-11
 */
@Service
public class BizVendorServiceImpl implements IBizVendorService {
    @Autowired
    private BizVendorMapper bizVendorMapper;

    /**
     * 查询供应商信息
     *
     * @param id 供应商信息ID
     * @return 供应商信息
     */
    @Override
    public BizVendor selectBizVendorById(Long id) {
        return bizVendorMapper.selectBizVendorById(id);
    }

    /**
     * 查询供应商信息列表
     *
     * @param bizVendor 供应商信息
     * @return 供应商信息
     */
    @Override
    public List<BizVendor> selectBizVendorList(BizVendor bizVendor) {
        return bizVendorMapper.selectBizVendorList(bizVendor);
    }

    /**
     * 查询供应商信息列表（带客户ID条件去判断是否买过）
     *
     * @param bizVendorVo
     * @return
     */
    @Override
    public List<BizVendorVo> selectBizVendorListWithCustomerId(BizVendorVo bizVendorVo) {
        return bizVendorMapper.selectBizVendorListWithCustomerId(bizVendorVo);
    }

    /**
     * 新增供应商信息
     *
     * @param bizVendor 供应商信息
     * @return 结果
     */
    @Override
    public int insertBizVendor(BizVendor bizVendor) {
        bizVendor.setCreateTime(DateUtils.getNowDate());
        return bizVendorMapper.insertBizVendor(bizVendor);
    }

    /**
     * 修改供应商信息
     *
     * @param bizVendor 供应商信息
     * @return 结果
     */
    @Override
    public int updateBizVendor(BizVendor bizVendor) {
        bizVendor.setUpdateTime(DateUtils.getNowDate());
        return bizVendorMapper.updateBizVendor(bizVendor);
    }

    /**
     * 删除供应商信息对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteBizVendorByIds(String ids) {
        return bizVendorMapper.deleteBizVendorByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除供应商信息信息
     *
     * @param id 供应商信息ID
     * @return 结果
     */
    @Override
    public int deleteBizVendorById(Long id) {
        return bizVendorMapper.deleteBizVendorById(id);
    }
}
