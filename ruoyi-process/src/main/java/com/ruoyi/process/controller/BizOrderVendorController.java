package com.ruoyi.web.controller.process;

import java.util.List;

import com.ruoyi.process.order.domain.BizOrder;
import com.ruoyi.process.order.service.IBizOrderService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.process.domain.BizOrderVendor;
import com.ruoyi.process.service.IBizOrderVendorService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.servlet.ModelAndView;

/**
 * 订单报数Controller
 *
 * @author Powerjun Tech
 * @date 2021-06-01
 */
@Controller
@RequestMapping("/process/order_vendor")
public class BizOrderVendorController extends BaseController {
    private String prefix = "process/order_vendor";

    @Autowired
    private IBizOrderVendorService bizOrderVendorService;
    @Autowired
    private IBizOrderService bizOrderService;

    @RequiresPermissions("process:order_vendor:view")
    @GetMapping()
    public String order_vendor() {
        return prefix + "/order_vendor";
    }

    /**
     * 查询订单报数列表
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(BizOrderVendor bizOrderVendor) {
        startPage();
        List<BizOrderVendor> list = bizOrderVendorService.selectBizOrderVendorList(bizOrderVendor);
        return getDataTable(list);
    }

    /**
     * 查询订单报数列表
     */
    @PostMapping("/list/{orderId}")
    @ResponseBody
    public TableDataInfo listByOrderId(@PathVariable String orderId) {
        startPage();
        BizOrderVendor bizOrderVendor = new BizOrderVendor();
        bizOrderVendor.setOrderId(orderId);
        List<BizOrderVendor> list = bizOrderVendorService.selectBizOrderVendorList(bizOrderVendor);
        return getDataTable(list);
    }

    /**
     * 导出订单报数列表
     */
    @RequiresPermissions("process:order_vendor:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(BizOrderVendor bizOrderVendor) {
        List<BizOrderVendor> list = bizOrderVendorService.selectBizOrderVendorList(bizOrderVendor);
        ExcelUtil<BizOrderVendor> util = new ExcelUtil<BizOrderVendor>(BizOrderVendor.class);
        return util.exportExcel(list, "order_vendor");
    }

    /**
     * 新增订单报数
     */
    @GetMapping("/add")
    public String add(@RequestParam String orderId,@RequestParam String customerId, ModelMap modelMap) {
        modelMap.put("orderId", orderId);
        modelMap.put("customerId", customerId);
        return prefix + "/add";
    }

    /**
     * 新增保存订单报数
     */
    @RequiresPermissions("process:order_vendor:add")
    @Log(title = "订单报数", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(BizOrderVendor bizOrderVendor) {
        return toAjax(bizOrderVendorService.insertBizOrderVendor(bizOrderVendor));
    }

    /**
     * 修改订单报数
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        BizOrderVendor bizOrderVendor = bizOrderVendorService.selectBizOrderVendorById(id);
        mmap.put("bizOrderVendor", bizOrderVendor);
        return prefix + "/edit";
    }

    /**
     * 修改保存订单报数
     */
    @RequiresPermissions("process:order_vendor:edit")
    @Log(title = "订单报数", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(BizOrderVendor bizOrderVendor) {
        return toAjax(bizOrderVendorService.updateBizOrderVendor(bizOrderVendor));
    }

    /**
     * 删除订单报数
     */
    @RequiresPermissions("process:order_vendor:remove")
    @Log(title = "订单报数", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(bizOrderVendorService.deleteBizOrderVendorByIds(ids));
    }
}
