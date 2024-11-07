/*
 Navicat Premium Dump SQL

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80200 (8.2.0)
 Source Host           : 127.0.0.1:3306
 Source Schema         : eoj

 Target Server Type    : MySQL
 Target Server Version : 80200 (8.2.0)
 File Encoding         : 65001

 Date: 06/11/2024 19:42:08
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for e_case
-- ----------------------------
DROP TABLE IF EXISTS `e_case`;
CREATE TABLE `e_case` (
  `id` bigint NOT NULL COMMENT '主键 id',
  `problem_id` bigint DEFAULT NULL COMMENT '问题 id',
  `input_case` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '输入',
  `output_case` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '输出',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='测试用例表';

-- ----------------------------
-- Records of e_case
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for e_language
-- ----------------------------
DROP TABLE IF EXISTS `e_language`;
CREATE TABLE `e_language` (
  `id` bigint NOT NULL COMMENT '主键 id',
  `language` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '语言名称',
  `compile_args` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '编译请求参数',
  `run_args` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '运行请求参数',
  `compile_env` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '编译环境',
  `run_env` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '运行环境',
  `compile_copy_in` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '编译输入文件名',
  `run_copy_in` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '运行输入文件名',
  `copyOutCached` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '缓存名',
  `template` longtext COLLATE utf8mb4_general_ci COMMENT '默认模板',
  `sort` int DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='语言参数配置表';

-- ----------------------------
-- Records of e_language
-- ----------------------------
BEGIN;
INSERT INTO `e_language` (`id`, `language`, `compile_args`, `run_args`, `compile_env`, `run_env`, `compile_copy_in`, `run_copy_in`, `copyOutCached`, `template`, `sort`) VALUES (1, 'Java', '/usr/bin/javac,Main.java', '/usr/bin/java,Main', 'PATH=/usr/bin:/bin, LANG=en_US.UTF-8, LC_ALL=en_US.UTF-8, LANGUAGE=en_US:en', 'PATH=/usr/bin:/bin, LANG=en_US.UTF-8, LC_ALL=en_US.UTF-8, LANGUAGE=en_US:en', 'Main.java', 'Main.class', 'Main.class', 'import java.util.Scanner;\r\npublic class Main{\r\n    public static void main(String[] args){\r\n        Scanner in=new Scanner(System.in);\r\n        int a=in.nextInt();\r\n        int b=in.nextInt();\r\n        System.out.println((a+b));\r\n    }\r\n}', 1);
INSERT INTO `e_language` (`id`, `language`, `compile_args`, `run_args`, `compile_env`, `run_env`, `compile_copy_in`, `run_copy_in`, `copyOutCached`, `template`, `sort`) VALUES (2, 'Python3', '/usr/bin/python3,-c,\\\"import py_compile; py_compile.compile(\'a.py\', \'a.pyc\', doraise=True)\\\"', '/usr/bin/python3,a.py', 'PATH=/usr/bin:/bin,LANG=en_US.UTF-8,LANGUAGE=en_US:en, LC_ALL=en_US.UTF-8, PYTHONIOENCODING=utf-8', 'PATH=/usr/bin:/bin, LANG=en_US.UTF-8,LANGUAGE=en_US:en, LC_ALL=en_US.UTF-8, PYTHONIOENCODING=utf-8', 'a.py', 'a.py', 'a.py', 'a, b = map(int, input().split())\r\nprint(a + b)', 2);
INSERT INTO `e_language` (`id`, `language`, `compile_args`, `run_args`, `compile_env`, `run_env`, `compile_copy_in`, `run_copy_in`, `copyOutCached`, `template`, `sort`) VALUES (3, 'C++', '/usr/bin/g++,a.cc,-o,a', 'a', 'PATH=/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin,LANG=en_US.UTF-8,LC_ALL=en_US.UTF-8,LANGUAGE=en_US:en,HOME=/w', 'PATH=/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin,LANG=en_US.UTF-8,LC_ALL=en_US.UTF-8,LANGUAGE=en_US:en,HOME=/w', 'a.cc', 'a', 'a', '#include<iostream>\r\nusing namespace std;\r\nint main()\r\n{\r\n    int a,b;\r\n    cin >> a >> b;\r\n    cout << a + b;\r\n    return 0;\r\n}', 3);
COMMIT;

-- ----------------------------
-- Table structure for e_p_submit
-- ----------------------------
DROP TABLE IF EXISTS `e_p_submit`;
CREATE TABLE `e_p_submit` (
  `id` bigint NOT NULL COMMENT '主键 id',
  `problem_id` bigint DEFAULT NULL COMMENT '问题id',
  `status` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '状态',
  `score` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '得分',
  `code_input` longtext COLLATE utf8mb4_general_ci COMMENT '代码输入',
  `code_output` longtext COLLATE utf8mb4_general_ci COMMENT '代码输出',
  `language` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '语种',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='问题提交表';

-- ----------------------------
-- Records of e_p_submit
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for e_problem
-- ----------------------------
DROP TABLE IF EXISTS `e_problem`;
CREATE TABLE `e_problem` (
  `id` bigint NOT NULL COMMENT '主键 id',
  `content` longtext COLLATE utf8mb4_general_ci COMMENT '问题主体内容',
  `answer_key` longtext COLLATE utf8mb4_general_ci COMMENT '题解',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='问题表';

-- ----------------------------
-- Records of e_problem
-- ----------------------------
BEGIN;
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
