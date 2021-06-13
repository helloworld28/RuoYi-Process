package com.ruoyi.web.controller.process;

import java.util.List;

import com.ruoyi.process.domain.BizVendorVo;
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
import com.ruoyi.process.domain.BizVendor;
import com.ruoyi.process.service.IBizVendorService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 供应商信息Controller
 *
 * @author Xianlu Tech
 * @date 2021-06-01
 */
@Controller
@RequestMapping("/process/vendor")
public class BizVendorController extends BaseController {
    private String prefix = "process/vendor";

    @Autowired
    private IBizVendorService bizVendorService;

    @RequiresPermissions("process:vendor:view")
    @GetMapping()
    public String vendor() {
        return prefix + "/vendor";
    }

    /**
     * 查询供应商信息列表
     */
    @RequiresPermissions("process:vendor:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(BizVendor bizVendor) {
        startPage();
        List<BizVendor> list = bizVendorService.selectBizVendorList(bizVendor);
        return getDataTable(list);
    }

    @RequiresPermissions("process:vendor:view")
    @GetMapping("/listWithCustomerId/{customerId}")
    public String listWithCustomerId(@PathVariable String customerId, ModelMap modelMap) {
        modelMap.put("customerId", customerId);
        return prefix + "/vendorSearch";
    }


    @RequiresPermissions("process:vendor:list")
    @PostMapping("/list/{customerId}")
    @ResponseBody
    public TableDataInfo listByCustomerId(@PathVariable String customerId, BizVendorVo bizVendor) {
        startPage();
        bizVendor.setCustomerId(customerId);
        List<BizVendorVo> list = bizVendorService.selectBizVendorListWithCustomerId(bizVendor);
        return getDataTable(list);
    }

    /**
     * 导出供应商信息列表
     */
    @RequiresPermissions("process:vendor:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(BizVendor bizVendor) {
        List<BizVendor> list = bizVendorService.selectBizVendorList(bizVendor);
        ExcelUtil<BizVendor> util = new ExcelUtil<BizVendor>(BizVendor.class);
        return util.exportExcel(list, "vendor");
    }

    /**
     * 新增供应商信息
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存供应商信息
     */
    @RequiresPermissions("process:vendor:add")
    @Log(title = "供应商信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(BizVendor bizVendor) {
        return toAjax(bizVendorService.insertBizVendor(bizVendor));
    }

    /**
     * 修改供应商信息
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        BizVendor bizVendor = bizVendorService.selectBizVendorById(id);
        mmap.put("bizVendor", bizVendor);
        return prefix + "/edit";
    }

    /**
     * 修改保存供应商信息
     */
    @RequiresPermissions("process:vendor:edit")
    @Log(title = "供应商信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(BizVendor bizVendor) {
        return toAjax(bizVendorService.updateBizVendor(bizVendor));
    }

    /**
     * 删除供应商信息
     */
    @RequiresPermissions("process:vendor:remove")
    @Log(title = "供应商信息", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(bizVendorService.deleteBizVendorByIds(ids));
    }
}
