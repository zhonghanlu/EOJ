package com.emode.eoj.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 语言参数配置表
 * </p>
 *
 * @author zhl
 * @since 2024-11-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("e_language")
public class ELanguage implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键 id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 语言名称
     */
    private String language;

    /**
     * 编译请求参数
     */
    private String compileArgs;

    /**
     * 运行请求参数
     */
    private String runArgs;

    /**
     * 编译环境
     */
    private String compileEnv;

    /**
     * 运行环境
     */
    private String runEnv;

    /**
     * 编译输入文件名
     */
    private String compileCopyIn;

    /**
     * 运行输入文件名
     */
    private String runCopyIn;

    /**
     * 缓存名
     */
    private String copyOutCached;

    /**
     * 默认模板
     */
    private String template;

    /**
     * 排序
     */
    private Integer sort;


}
