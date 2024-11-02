package com.emode.eoj.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 问题提交表
 * </p>
 *
 * @author zhl
 * @since 2024-11-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("e_p_submit")
public class EPSubmit implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键 id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 问题id
     */
    private Long problemId;

    /**
     * 状态
     */
    private String status;

    /**
     * 得分
     */
    private String score;

    /**
     * 代码输入
     */
    private String codeInput;

    /**
     * 代码输出
     */
    private String codeOutput;

    /**
     * 语种
     */
    private String language;


}
