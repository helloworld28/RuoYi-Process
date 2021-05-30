package com.ruoyi.web.controller.process;

import com.github.promeg.pinyinhelper.Pinyin;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.process.order.domain.BizOrder;
import com.ruoyi.process.order.domain.BizOrderVo;
import com.ruoyi.process.order.service.BizOrderProcessService;
import com.ruoyi.process.order.service.IBizOrderService;
import com.ruoyi.system.domain.SysUser;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.ruoyi.process.utils.ParameterUtils.transToVariables;

/**
 * 订单Controller
 *
 * @author PowerJun Tech
 * @date 2021-05-20
 */
@Controller
@RequestMapping("/process/order")
public class BizOrderController extends BaseController {
    private String prefix = "process/order";

    @Autowired
    private IBizOrderService bizOrderService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private BizOrderProcessService orderProcessService;

    @RequiresPermissions("process:order:view")
    @GetMapping()
    public String order(ModelMap mmap) {
        mmap.put("currentUser", ShiroUtils.getSysUser());
        return prefix + "/order";
    }

    /**
     * 查询订单列表
     */
    @RequiresPermissions("process:order:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(BizOrder bizOrder) {
        startPage();

        List<BizOrder> list = bizOrderService.selectBizOrderList(bizOrder);
        return getDataTable(list);
    }

    /**
     * 导出订单列表
     */
    @RequiresPermissions("process:order:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(BizOrder bizOrder) {
        List<BizOrder> list = bizOrderService.selectBizOrderList(bizOrder);
        ExcelUtil<BizOrder> util = new ExcelUtil<BizOrder>(BizOrder.class);
        return util.exportExcel(list, "order");
    }

    /**
     * 新增订单
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存订单
     */
    @RequiresPermissions("process:order:add")
    @Log(title = "订单", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(BizOrder bizOrder) {
        Long userId = ShiroUtils.getUserId();
        if (SysUser.isAdmin(userId)) {
            return error("提交申请失败：不允许管理员提交申请！");
        }

        return toAjax(bizOrderService.insertBizOrder(bizOrder));
    }

    /**
     * 提交申请
     */
    @Log(title = "提交订单申请", businessType = BusinessType.UPDATE)
    @PostMapping("/submitApply")
    @ResponseBody
    public AjaxResult submitApply(Long id) {
        BizOrder order = bizOrderService.selectBizOrderById(id);
        String applyUserId = ShiroUtils.getLoginName();
        bizOrderService.submitApply(order, applyUserId);
        return success();
    }

    /**
     * 修改订单
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        BizOrder bizOrder = bizOrderService.selectBizOrderById(id);
        mmap.put("bizOrder", bizOrder);
        return prefix + "/edit";
    }



    @PostMapping("/claimOrder")
    @ResponseBody
    public AjaxResult acceptOrder(BizOrderVo bizOrder) {
        bizOrderService.claimOrder(bizOrder, ShiroUtils.getLoginName());

        return success();
    }

    @RequiresPermissions("process:order:todoView")
    @GetMapping("/orderTodo")
    public String todoView() {
        return prefix + "/orderTodo";
    }

    /**
     * 我的待办列表
     *
     * @param bizLeave
     * @return
     */
    @RequiresPermissions("process:order:todoView")
    @PostMapping("/taskList")
    @ResponseBody
    public TableDataInfo taskList(BizOrderVo bizLeave) {
        startPage();
        List<BizOrderVo> list = bizOrderService.findTodoTasks(bizLeave, ShiroUtils.getLoginName());
        return getDataTable(list);
    }

    /**
     * 修改保存订单
     */
    @RequiresPermissions("process:order:edit")
    @Log(title = "订单", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(BizOrder bizOrder) {
        return toAjax(bizOrderService.updateBizOrder(bizOrder));
    }

    /**
     * 删除订单
     */
    @RequiresPermissions("process:order:remove")
    @Log(title = "订单", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(bizOrderService.deleteBizOrderByIds(ids));
    }

    /**
     * 加载审批弹窗
     *
     * @param taskId
     * @return
     */
    @GetMapping("/showVerifyDialog/{taskId}")
    public ModelAndView showVerifyDialog(@PathVariable("taskId") String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String verifyName = StringUtils.capitalize(task.getTaskDefinitionKey());
        return new ModelAndView("forward:/" + prefix + "/task" + verifyName+ "/" + taskId);
    }

    @GetMapping("/taskAcceptOrder/{taskId}")
    public String taskAcceptOrder(@PathVariable String taskId, ModelMap modelMap) {
        modelMap.put("bizOrder", orderProcessService.getBizOrderByTaskId(taskId));
        return prefix + "/taskAcceptOrder";
    }

    @GetMapping("/taskReports/{taskId}")
    public String taskReports(@PathVariable String taskId, ModelMap modelMap) {
        modelMap.put("bizOrder", orderProcessService.getBizOrderByTaskId(taskId));
        return prefix + "/taskReports";
    }


    /**
     * 完成报数
     *
     * @return
     */
    @RequestMapping(value = "/completeReports/{taskId}", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public AjaxResult completeReports(@PathVariable("taskId") String taskId,
                                      @ModelAttribute("preloadLeave") BizOrderVo orderVo, HttpServletRequest request) {
        Enumeration<String> parameterNames = request.getParameterNames();
        try {
            Map<String, Object> variables = transToVariables(request, parameterNames);
            logger.info("completeReports with variables {}", variables);

            orderProcessService.completeReports(orderVo, variables);

            return success("任务已完成");
        } catch (Exception e) {
            logger.error("error on complete task {}, variables={}", new Object[]{taskId, e});
            return error("完成任务失败");
        }
    }

    @GetMapping("/taskConfirmProduct/{taskId}")
    public String taskConfirmProducts(@PathVariable("taskId") String taskId,  ModelMap mmap) {

        BizOrderVo order = orderProcessService.getBizOrderByTaskId(taskId);
        mmap.put("bizOrder", order);

        //获取最新的批复数据
        Optional<Comment> previousTaskComment = orderProcessService.getPreviousTaskComment(order.getInstanceId());
        if (previousTaskComment.isPresent()) {
            mmap.put("reports", previousTaskComment.get().getFullMessage());
        }

        return prefix + "/taskConfirmProduct";
    }

    /**
     * 门店确认结果
     *
     * @return
     */
    @RequestMapping(value = "/confirmProduct/{taskId}", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public AjaxResult confirmProduct(@PathVariable("taskId") String taskId,
                                      @ModelAttribute("preloadLeave") BizOrderVo orderVo, HttpServletRequest request) {
        Enumeration<String> parameterNames = request.getParameterNames();
        try {
            Map<String, Object> variables = transToVariables(request, parameterNames);
            logger.info("confirmProduct with variables {}", variables);

            orderProcessService.confirmProduct(orderVo, variables);

            return success("任务已完成");
        } catch (Exception e) {
            logger.error("error on complete task {}, variables={}", new Object[]{taskId, e});
            return error("完成任务失败");
        }
    }


}
