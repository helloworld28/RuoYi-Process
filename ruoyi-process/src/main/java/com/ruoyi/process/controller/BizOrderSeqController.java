package com.ruoyi.web.controller.process;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.process.domain.BizOrderSeq;
import com.ruoyi.process.service.IBizOrderSeqService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 订单序号Controller
 *
 * @author Powerjun Tech
 * @date 2021-05-27
 */
@Controller
@RequestMapping("/process/orderSeq")
public class BizOrderSeqController extends BaseController {
    private String prefix = "process/orderSeq";

    @Autowired
    private IBizOrderSeqService bizOrderSeqService;

    @RequiresPermissions("process:orderSeq:view")
    @GetMapping()
    public String orderSeq() {
        return prefix + "/orderSeq";
    }

    /**
     * 查询订单序号列表
     */
    @RequiresPermissions("process:orderSeq:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(BizOrderSeq bizOrderSeq) {
        startPage();
        List<BizOrderSeq> list = bizOrderSeqService.selectBizOrderSeqList(bizOrderSeq);
        return getDataTable(list);
    }

    /**
     * 导出订单序号列表
     */
    @RequiresPermissions("process:orderSeq:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(BizOrderSeq bizOrderSeq) {
        List<BizOrderSeq> list = bizOrderSeqService.selectBizOrderSeqList(bizOrderSeq);
        ExcelUtil<BizOrderSeq> util = new ExcelUtil<BizOrderSeq>(BizOrderSeq.class);
        return util.exportExcel(list, "orderSeq");
    }

    /**
     * 新增订单序号
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存订单序号
     */
    @RequiresPermissions("process:orderSeq:add")
    @Log(title = "订单序号", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(BizOrderSeq bizOrderSeq) {
        return toAjax(bizOrderSeqService.insertBizOrderSeq(bizOrderSeq));
    }

    /**
     * 修改订单序号
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        BizOrderSeq bizOrderSeq = bizOrderSeqService.selectBizOrderSeqById(id);
        mmap.put("bizOrderSeq", bizOrderSeq);
        return prefix + "/edit";
    }

    /**
     * 修改保存订单序号
     */
    @RequiresPermissions("process:orderSeq:edit")
    @Log(title = "订单序号", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(BizOrderSeq bizOrderSeq) {
        return toAjax(bizOrderSeqService.updateBizOrderSeq(bizOrderSeq));
    }

    /**
     * 删除订单序号
     */
    @RequiresPermissions("process:orderSeq:remove")
    @Log(title = "订单序号", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(bizOrderSeqService.deleteBizOrderSeqByIds(ids));
    }
}
