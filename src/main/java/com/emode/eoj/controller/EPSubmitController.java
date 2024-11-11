package com.emode.eoj.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.emode.eoj.pojo.EPSubmit;
import com.emode.eoj.service.IEPSubmitService;
import com.emode.eoj.util.webmvc.PageQuery;
import com.emode.eoj.util.webmvc.Restful;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 问题提交表 前端控制器
 * </p>
 *
 * @author zhl
 * @since 2024-11-02
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/e-p-submit")
public class EPSubmitController {


    private final IEPSubmitService submitService;

    /**
     * 分页
     */
    @GetMapping("/page")
    public Restful<Page<EPSubmit>> submitList(PageQuery pageQuery) {
        LambdaQueryWrapper<EPSubmit> wrapper = Wrappers.lambdaQuery(EPSubmit.class);
        wrapper.orderByDesc(EPSubmit::getCreateTime);
        return Restful.OBJECT(submitService.page(pageQuery.build(), wrapper)).build();
    }


}
