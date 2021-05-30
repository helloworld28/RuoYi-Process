package com.ruoyi.process.service;

import com.ruoyi.process.domain.BizOrderSeq;
import java.util.List;

/**
 * 订单序号Service接口
 *
 * @author Powerjun Tech
 * @date 2021-05-27
 */
public interface IBizOrderSeqService {
    /**
     * 查询订单序号
     *
     * @param id 订单序号ID
     * @return 订单序号
     */
    public BizOrderSeq selectBizOrderSeqById(Long id);

    /**
     * 查询订单序号
     *
     * @param userId 订单序号ID
     * @return 订单序号
     */
    public BizOrderSeq selectBizOrderSeqByUserId(String userId);

    /**
     * 查询订单序号列表
     *
     * @param bizOrderSeq 订单序号
     * @return 订单序号集合
     */
    public List<BizOrderSeq> selectBizOrderSeqList(BizOrderSeq bizOrderSeq);

    /**
     * 新增订单序号
     *
     * @param bizOrderSeq 订单序号
     * @return 结果
     */
    public int insertBizOrderSeq(BizOrderSeq bizOrderSeq);

    /**
     * 修改订单序号
     *
     * @param bizOrderSeq 订单序号
     * @return 结果
     */
    public int updateBizOrderSeq(BizOrderSeq bizOrderSeq);

    /**
     * 批量删除订单序号
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBizOrderSeqByIds(String ids);

    /**
     * 删除订单序号信息
     *
     * @param id 订单序号ID
     * @return 结果
     */
    public int deleteBizOrderSeqById(Long id);
}
