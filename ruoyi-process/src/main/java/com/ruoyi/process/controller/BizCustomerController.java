package com.ruoyi.web.controller.process;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ruoyi.framework.util.ShiroUtils;
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
import com.ruoyi.process.domain.BizCustomer;
import com.ruoyi.process.service.IBizCustomerService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 客户信息Controller
 *
 * @author Xianlu Tech
 * @date 2021-06-13
 */
@Controller
@RequestMapping("/process/customer")
public class BizCustomerController extends BaseController {
    private String prefix = "process/customer";

    @Autowired
    private IBizCustomerService bizCustomerService;

    @RequiresPermissions("process:customer:view")
    @GetMapping()
    public String customer() {
        return prefix + "/customer";
    }

    /**
     * 查询客户信息列表
     */
    @RequiresPermissions("process:customer:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(BizCustomer bizCustomer) {
        startPage();
        List<BizCustomer> list = bizCustomerService.selectBizCustomerList(bizCustomer);
        return getDataTable(list);
    }

    /**
     * 客户自动完成输入框列表
     * 必须返回组件要求的数据格式{value:[]}
     * @param bizCustomer
     * @return
     */
    @RequiresPermissions("process:customer:list")
    @GetMapping("/listByUserId")
    @ResponseBody
    public Map<String, Object> listByUserId(BizCustomer bizCustomer) {
        bizCustomer.setCreateBy(ShiroUtils.getLoginName());
        List<BizCustomer> list = bizCustomerService.selectBizCustomerList(bizCustomer);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("value", list);
        return resultMap;
    }


    /**
     * 导出客户信息列表
     */
    @RequiresPermissions("process:customer:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(BizCustomer bizCustomer) {
        List<BizCustomer> list = bizCustomerService.selectBizCustomerList(bizCustomer);
        ExcelUtil<BizCustomer> util = new ExcelUtil<BizCustomer>(BizCustomer.class);
        return util.exportExcel(list, "customer");
    }

    /**
     * 新增客户信息
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存客户信息
     */
    @RequiresPermissions("process:customer:add")
    @Log(title = "客户信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(BizCustomer bizCustomer) {
        return toAjax(bizCustomerService.insertBizCustomer(bizCustomer));
    }

    /**
     * 修改客户信息
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        BizCustomer bizCustomer = bizCustomerService.selectBizCustomerById(id);
        mmap.put("bizCustomer", bizCustomer);
        return prefix + "/edit";
    }

    /**
     * 修改保存客户信息
     */
    @RequiresPermissions("process:customer:edit")
    @Log(title = "客户信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(BizCustomer bizCustomer) {
        return toAjax(bizCustomerService.updateBizCustomer(bizCustomer));
    }

    /**
     * 删除客户信息
     */
    @RequiresPermissions("process:customer:remove")
    @Log(title = "客户信息", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(bizCustomerService.deleteBizCustomerByIds(ids));
    }
}
