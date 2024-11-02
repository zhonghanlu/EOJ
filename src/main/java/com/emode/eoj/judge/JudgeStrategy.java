package com.emode.eoj.judge;


import com.emode.eoj.judge.param.JudgeContext;
import com.emode.eoj.judge.param.JudgeParam;
import com.emode.eoj.util.bean.SpringBean;
import org.springframework.stereotype.Component;

@Component
public abstract class JudgeStrategy {

    /**
     * 编译 -》 运行 -》 删除
     */
    public static void run(JudgeContext judgeContext) {
        JudgeStrategy judgeStrategy = SpringBean.getBean(JudgeStrategy.class);
        // 编译
        long execId = judgeStrategy.compile(judgeContext);
        // 执行代码
        judgeStrategy.exec(judgeContext);
        // 删除执行文件
        judgeStrategy.delete(execId);
    }


    /**
     * 编译
     */
    private long compile(JudgeContext judgeContext) {
        return 0;
    }

    /**
     * 执行
     */
    private void exec(JudgeContext judgeContext) {
    }

    /**
     * 删除执行文件
     */
    private void delete(Long execId) {
    }

    /**
     * 根据各种语言构建参数
     */
    private JudgeParam buildParam(String language) {
        // TODO: 根据类语言进行参数构建
        JudgeParam judgeParam = JudgeParam.builder().build();

        return judgeParam;
    }

}
