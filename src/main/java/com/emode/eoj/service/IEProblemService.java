package com.emode.eoj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.emode.eoj.model.request.EProblemSubmitRequest;
import com.emode.eoj.pojo.EProblem;

/**
 * <p>
 * 问题表 服务类
 * </p>
 *
 * @author zhl
 * @since 2024-11-02
 */
public interface IEProblemService extends IService<EProblem> {

    void submit(EProblemSubmitRequest request);
}
