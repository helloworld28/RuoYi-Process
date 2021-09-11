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
import com.ruoyi.process.domain.BizOrderDetail;
import com.ruoyi.process.service.IBizOrderDetailService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 订单明细Controller
 *
 * @author Xianlu Tech
 * @date 2021-06-19
 */
@Controller
@RequestMapping("/process/order/detail")
public class BizOrderDetailController extends BaseController {
    private String prefix = "process/order_detail";

    @Autowired
    private IBizOrderDetailService bizOrderDetailService;

    @RequiresPermissions("process:detail:view")
    @GetMapping()
    public String detail() {
        return prefix + "/order_detail";
    }

    /**
     * 查询订单明细列表
     */
    @RequiresPermissions("process:detail:list")
    @PostMapping("/list/{orderId}")
    @ResponseBody
    public TableDataInfo list(@PathVariable String orderId, BizOrderDetail bizOrderDetail) {
        startPage();
        List<BizOrderDetail> list = bizOrderDetailService.selectBizOrderDetailList(bizOrderDetail);
        return getDataTable(list);
    }


    /**
     * 新增订单明细
     */
    @GetMapping("/add")
    public String add(String orderId, ModelMap modelMap) {
        modelMap.put("orderId", orderId);
        return prefix + "/add";
    }

    /**
     * 新增保存订单明细
     */
    @RequiresPermissions("process:detail:add")
    @Log(title = "订单明细", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(BizOrderDetail bizOrderDetail) {
        return toAjax(bizOrderDetailService.insertBizOrderDetail(bizOrderDetail));
    }

    /**
     * 修改订单明细
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        BizOrderDetail bizOrderDetail = bizOrderDetailService.selectBizOrderDetailById(id);
        mmap.put("bizOrderDetail", bizOrderDetail);
        return prefix + "/edit";
    }

    /**
     * 修改保存订单明细
     */
    @RequiresPermissions("process:detail:edit")
    @Log(title = "订单明细", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(BizOrderDetail bizOrderDetail) {
        return toAjax(bizOrderDetailService.updateBizOrderDetail(bizOrderDetail));
    }

    /**
     * 删除订单明细
     */
    @RequiresPermissions("process:detail:remove")
    @Log(title = "订单明细", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(bizOrderDetailService.deleteBizOrderDetailByIds(ids));
    }
}
