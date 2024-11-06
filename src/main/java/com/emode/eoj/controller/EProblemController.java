package com.emode.eoj.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.emode.eoj.model.dto.JudgementDTO;
import com.emode.eoj.model.request.EProblemAnswerKeyRequest;
import com.emode.eoj.model.request.EProblemSubmitRequest;
import com.emode.eoj.pojo.EProblem;
import com.emode.eoj.service.IEProblemService;
import com.emode.eoj.util.webmvc.PageQuery;
import com.emode.eoj.util.webmvc.Restful;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * <p>
 * 问题表 前端控制器
 * </p>
 *
 * @author zhl
 * @since 2024-11-02
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/e-problem")
public class EProblemController {

    private final IEProblemService problemService;

    /**
     * 分页
     */
    @GetMapping("/page")
    public Restful<Page<EProblem>> problemList(PageQuery pageQuery) {
        return Restful.OBJECT(problemService.page(pageQuery.build())).build();
    }

    /**
     * 根据 id 查询
     */
    @GetMapping("/getInfo/{id}")
    public Restful<EProblem> getInfo(@PathVariable("id") Long id) {
        return Restful.OBJECT(problemService.getById(id)).build();
    }


    /**
     * 提交
     */
    @PostMapping("/submit")
    public Restful<JudgementDTO> problemList(@RequestBody EProblemSubmitRequest request) {
        return Restful.OBJECT(problemService.submit(request)).build();
    }

    /**
     * 编写题解
     */
    @PostMapping("/write-answer-key")
    public Restful<Void> writeAnswerKey(@RequestBody EProblemAnswerKeyRequest request) {
        problemService.writeAnswerKey(request);
        return Restful.SUCCESS().build();
    }


}
