package com.ruoyi.process.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.process.mapper.BizOrderSeqMapper;
import com.ruoyi.process.domain.BizOrderSeq;
import com.ruoyi.process.service.IBizOrderSeqService;
import com.ruoyi.common.core.text.Convert;

/**
 * 订单序号Service业务层处理
 *
 * @author Powerjun Tech
 * @date 2021-05-27
 */
@Service
public class BizOrderSeqServiceImpl implements IBizOrderSeqService {
    @Autowired
    private BizOrderSeqMapper bizOrderSeqMapper;

    /**
     * 查询订单序号
     *
     * @param id 订单序号ID
     * @return 订单序号
     */
    @Override
    public BizOrderSeq selectBizOrderSeqById(Long id) {
        return bizOrderSeqMapper.selectBizOrderSeqById(id);
    }

    @Override
    public BizOrderSeq selectBizOrderSeqByUserId(String userId) {
        BizOrderSeq bizOrderSeq = new BizOrderSeq();
        bizOrderSeq.setUserId(userId);
        List<BizOrderSeq> bizOrderSeqs = selectBizOrderSeqList(bizOrderSeq);

        if (bizOrderSeqs.isEmpty()) {
            BizOrderSeq orderSeq = new BizOrderSeq();
            orderSeq.setUserId(userId);
            orderSeq.setOrderSeq(0);
            insertBizOrderSeq(orderSeq);
            return orderSeq;
        } else {
            return bizOrderSeqs.get(0);
        }
    }

    /**
     * 查询订单序号列表
     *
     * @param bizOrderSeq 订单序号
     * @return 订单序号
     */
    @Override
    public List<BizOrderSeq> selectBizOrderSeqList(BizOrderSeq bizOrderSeq) {
        return bizOrderSeqMapper.selectBizOrderSeqList(bizOrderSeq);
    }

    /**
     * 新增订单序号
     *
     * @param bizOrderSeq 订单序号
     * @return 结果
     */
    @Override
    public int insertBizOrderSeq(BizOrderSeq bizOrderSeq) {
        return bizOrderSeqMapper.insertBizOrderSeq(bizOrderSeq);
    }

    /**
     * 修改订单序号
     *
     * @param bizOrderSeq 订单序号
     * @return 结果
     */
    @Override
    public int updateBizOrderSeq(BizOrderSeq bizOrderSeq) {
        return bizOrderSeqMapper.updateBizOrderSeq(bizOrderSeq);
    }

    /**
     * 删除订单序号对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteBizOrderSeqByIds(String ids) {
        return bizOrderSeqMapper.deleteBizOrderSeqByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除订单序号信息
     *
     * @param id 订单序号ID
     * @return 结果
     */
    @Override
    public int deleteBizOrderSeqById(Long id) {
        return bizOrderSeqMapper.deleteBizOrderSeqById(id);
    }
}
