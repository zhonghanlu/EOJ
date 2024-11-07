/*
 Navicat Premium Data Transfer

 Source Server         : mysql8.3
 Source Server Type    : MySQL
 Source Server Version : 80300 (8.3.0)
 Source Host           : localhost:3307
 Source Schema         : eoj

 Target Server Type    : MySQL
 Target Server Version : 80300 (8.3.0)
 File Encoding         : 65001

 Date: 07/11/2024 17:57:55
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for e_case
-- ----------------------------
DROP TABLE IF EXISTS `e_case`;
CREATE TABLE `e_case`  (
  `id` bigint NOT NULL,
  `problem_id` int NULL DEFAULT NULL,
  `input_case` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `output_case` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of e_case
-- ----------------------------
INSERT INTO `e_case` VALUES (1, 1, '1 1', '2');
INSERT INTO `e_case` VALUES (2, 1, '2 2', '4');
INSERT INTO `e_case` VALUES (3, 1, '33 33', '66');

-- ----------------------------
-- Table structure for e_language
-- ----------------------------
DROP TABLE IF EXISTS `e_language`;
CREATE TABLE `e_language`  (
  `id` bigint NOT NULL,
  `language` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `compile_args` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `run_args` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `compile_env` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `run_env` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `compile_copy_in` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `run_copy_in` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `copy_out_cached` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `template` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `sort` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of e_language
-- ----------------------------
INSERT INTO `e_language` VALUES (1, 'java', '/usr/bin/javac,Main.java', '/usr/bin/java,Main', 'PATH=/usr/bin:/bin', 'PATH=/usr/bin:/bin, LANG=en_US.UTF-8, LC_ALL=en_US.UTF-8, LANGUAGE=en_US:en', 'Main.java', 'Main.class', 'Main.class', 'import java.util.Scanner;\r\npublic class Main{\r\n    public static void main(String[] args){\r\n        Scanner in=new Scanner(System.in);\r\n        int a=in.nextInt();\r\n        int b=in.nextInt();\r\n        System.out.println((a+b));\r\n    }\r\n}', 1);
INSERT INTO `e_language` VALUES (2, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `e_language` VALUES (3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for e_p_submit
-- ----------------------------
DROP TABLE IF EXISTS `e_p_submit`;
CREATE TABLE `e_p_submit`  (
  `id` bigint NOT NULL COMMENT '主键',
  `problem_id` bigint NULL DEFAULT NULL,
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `score` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `code_input` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `code_output` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `language` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of e_p_submit
-- ----------------------------
INSERT INTO `e_p_submit` VALUES (1644560167993376, 1, 'TO_JUDGE', NULL, 'import java.io.*;\nimport java.util.*;\n\npublic class Main\n{\n  public static void main(String args[]) throws Exception\n  {\n    Scanner cin = new Scanner(System.in);\n    int a = cin.nextInt(), b = cin.nextInt();\n    System.out.println(a + b);\n  }\n}\n', NULL, 'java');
INSERT INTO `e_p_submit` VALUES (1644560795041824, 1, 'TO_JUDGE', NULL, 'import java.io.*;\nimport java.util.*;\n\npublic class Main\n{\n  public static void main(String args[]) throws Exception\n  {\n    Scanner cin = new Scanner(System.in);\n    int a = cin.nextInt(), b = cin.nextInt();\n    System.out.println(a + b);\n  }\n}\n', NULL, 'java');
INSERT INTO `e_p_submit` VALUES (1644562338545696, 1, 'TO_JUDGE', NULL, 'import java.io.*;\nimport java.util.*;\n\npublic class Main\n{\n  public static void main(String args[]) throws Exception\n  {\n    Scanner cin = new Scanner(System.in);\n    int a = cin.nextInt(), b = cin.nextInt();\n    System.out.println(a + b);\n  }\n}\n', NULL, 'java');
INSERT INTO `e_p_submit` VALUES (1644562546163744, 1, 'TO_JUDGE', NULL, 'import java.io.*;\nimport java.util.*;\n\npublic class Main\n{\n  public static void main(String args[]) throws Exception\n  {\n    Scanner cin = new Scanner(System.in);\n    int a = cin.nextInt(), b = cin.nextInt();\n    System.out.println(a + b);\n  }\n}\n', NULL, 'java');
INSERT INTO `e_p_submit` VALUES (1644564630732832, 1, 'TO_JUDGE', NULL, 'import java.io.*;\nimport java.util.*;\n\npublic class Main\n{\n  public static void main(String args[]) throws Exception\n  {\n    Scanner cin = new Scanner(System.in);\n    int a = cin.nextInt(), b = cin.nextInt();\n    System.out.println(a + b);\n  }\n}\n', NULL, 'java');
INSERT INTO `e_p_submit` VALUES (1644566761439264, 1, 'TO_JUDGE', NULL, 'import java.io.*;\nimport java.util.*;\n\npublic class Main\n{\n  public static void main(String args[]) throws Exception\n  {\n    Scanner cin = new Scanner(System.in);\n    int a = cin.nextInt(), b = cin.nextInt();\n    System.out.println(a + b);\n  }\n}\n', NULL, 'java');
INSERT INTO `e_p_submit` VALUES (1644568728567840, 1, 'TO_JUDGE', NULL, 'import java.io.*;\nimport java.util.*;\n\npublic class Main\n{\n  public static void main(String args[]) throws Exception\n  {\n    Scanner cin = new Scanner(System.in);\n    int a = cin.nextInt(), b = cin.nextInt();\n    System.out.println(a + b);\n  }\n}\n', NULL, 'java');
INSERT INTO `e_p_submit` VALUES (1644569070403616, 1, 'TO_JUDGE', NULL, 'import java.io.*;\nimport java.util.*;\n\npublic class Main\n{\n  public static void main(String args[]) throws Exception\n  {\n    Scanner cin = new Scanner(System.in);\n    int a = cin.nextInt(), b = cin.nextInt();\n    System.out.println(a + b);\n  }\n}\n', NULL, 'java');
INSERT INTO `e_p_submit` VALUES (1644569273827360, 1, 'TO_JUDGE', NULL, 'import java.io.*;\nimport java.util.*;\n\npublic class Main\n{\n  public static void main(String args[]) throws Exception\n  {\n    Scanner cin = new Scanner(System.in);\n    int a = cin.nextInt(), b = cin.nextInt();\n    System.out.println(a + b);\n  }\n}\n', NULL, 'java');
INSERT INTO `e_p_submit` VALUES (1644569380782112, 1, 'TO_JUDGE', NULL, 'import java.io.*;\nimport java.util.*;\n\npublic class Main\n{\n  public static void main(String args[]) throws Exception\n  {\n    Scanner cin = new Scanner(System.in);\n    int a = cin.nextInt(), b = cin.nextInt();\n    System.out.println(a + b);\n  }\n}\n', NULL, 'java');
INSERT INTO `e_p_submit` VALUES (1644569563234336, 1, 'TO_JUDGE', NULL, 'import java.io.*;\nimport java.util.*;\n\npublic class Main\n{\n  public static void main(String args[]) throws Exception\n  {\n    Scanner cin = new Scanner(System.in);\n    int a = cin.nextInt(), b = cin.nextInt();\n    System.out.println(a + b);\n  }\n}\n', NULL, 'java');
INSERT INTO `e_p_submit` VALUES (1644569974276128, 1, 'TO_JUDGE', NULL, 'import java.io.*;\nimport java.util.*;\n\npublic class Main\n{\n  public static void main(String args[]) throws Exception\n  {\n    Scanner cin = new Scanner(System.in);\n    int a = cin.nextInt(), b = cin.nextInt();\n    System.out.println(a + b);\n  }\n}\n', NULL, 'java');
INSERT INTO `e_p_submit` VALUES (1644571423408160, 1, 'TO_JUDGE', NULL, 'import java.io.*;\nimport java.util.*;\n\npublic class Main\n{\n  public static void main(String args[]) throws Exception\n  {\n    Scanner cin = new Scanner(System.in);\n    int a = cin.nextInt(), b = cin.nextInt();\n    System.out.println(a + b);\n  }\n}\n', '{\"score\":99.99,\"testCaseResultList\":[{\"inputCase\":\"1 1\",\"outputCase\":\"2\",\"result\":true},{\"inputCase\":\"2 2\",\"outputCase\":\"4\",\"result\":true},{\"inputCase\":\"33 33\",\"outputCase\":\"66\",\"result\":true}]}', 'java');
INSERT INTO `e_p_submit` VALUES (1644572021096480, 1, 'TO_JUDGE', NULL, 'import java.io.*;\nimport java.util.*;\n\npublic class Main\n{\n  public static void main(String args[]) throws Exception\n  {\n    Scanner cin = new Scanner(System.in);\n    int a = cin.nextInt(), b = cin.nextInt();\n    System.out.println(a + b);\n  }\n}\n', '{\"score\":99.99,\"testCaseResultList\":[{\"inputCase\":\"1 1\",\"outputCase\":\"2\",\"result\":true},{\"inputCase\":\"2 2\",\"outputCase\":\"4\",\"result\":true},{\"inputCase\":\"33 33\",\"outputCase\":\"66\",\"result\":true}]}', 'java');
INSERT INTO `e_p_submit` VALUES (1644572109176864, 1, 'TO_JUDGE', NULL, 'import java.io.*;\nimport java.util.*;\n\npublic class Main\n{\n  public static void main(String args[]) throws Exception\n  {\n    Scanner cin = new Scanner(System.in);\n    int a = cin.nextInt(), b = cin.nextInt();\n    System.out.println(a + b);\n  }\n}\n', '{\"score\":99.99,\"testCaseResultList\":[{\"inputCase\":\"1 1\",\"outputCase\":\"2\",\"result\":true},{\"inputCase\":\"2 2\",\"outputCase\":\"4\",\"result\":true},{\"inputCase\":\"33 33\",\"outputCase\":\"66\",\"result\":true}]}', 'java');
INSERT INTO `e_p_submit` VALUES (1644572895608864, 1, 'TO_JUDGE', '100.0', 'import java.io.*;\nimport java.util.*;\n\npublic class Main\n{\n  public static void main(String args[]) throws Exception\n  {\n    Scanner cin = new Scanner(System.in);\n    int a = cin.nextInt(), b = cin.nextInt();\n    System.out.println(a + b);\n  }\n}\n', '{\"score\":100.0,\"testCaseResultList\":[{\"inputCase\":\"1 1\",\"outputCase\":\"2\",\"result\":true},{\"inputCase\":\"2 2\",\"outputCase\":\"4\",\"result\":true},{\"inputCase\":\"33 33\",\"outputCase\":\"66\",\"result\":true}]}', 'java');
INSERT INTO `e_p_submit` VALUES (1644573021437984, 1, 'TO_JUDGE', NULL, 'import java.io.*;\nimport java.util.*;\n\npublic class Main\n{\n  public static void main(String args[]) throws Exception\n  {\n    Scanne cin = new Scanner(System.in);\n    int a = cin.nextInt(), b = cin.nextInt();\n    System.out.println(a + b);\n  }\n}\n', NULL, 'java');
INSERT INTO `e_p_submit` VALUES (1644573082255392, 1, 'TO_JUDGE', NULL, 'import java.io.*;\nimport java.util.*;\n\npublic class Main\n{\n  public static void main(String args[]) throws Exception\n  {\n    Scanne cin = new Scanner(System.in);\n    int a = cin.nextInt(), b = cin.nextInt();\n    System.out.println(a + b);\n  }\n}\n', NULL, 'java');
INSERT INTO `e_p_submit` VALUES (1644574183260192, 1, 'JUDGE_COMPILE_ERROR', '0', 'import java.io.*;\nimport java.util.*;\n\npublic class Main\n{\n  public static void main(String args[]) throws Exception\n  {\n    Scanne cin = new Scanner(System.in);\n    int a = cin.nextInt(), b = cin.nextInt();\n    System.out.println(a + b);\n  }\n}\n', 'Main.java:8: error: cannot find symbol\n    Scanne cin = new Scanner(System.in);\n    ^\n  symbol:   class Scanne\n  location: class Main\n1 error\n', 'java');
INSERT INTO `e_p_submit` VALUES (1644574386683936, 1, 'JUDGE_COMPILE_ERROR', '0', 'import java.io.*;\nimport java.util.*;\n\npublic class Main\n{\n  public static void main(String args[]) throws Exception\n  {\n    Scanne cin = new Scanner(System.in);\n    int a = cin.nextInt(), b = cin.nextInt();\n    System.out.println(a + b);\n  }\n}\n', 'Main.java:8: error: cannot find symbol\n    Scanne cin = new Scanner(System.in);\n    ^\n  symbol:   class Scanne\n  location: class Main\n1 error\n', 'java');
INSERT INTO `e_p_submit` VALUES (1644574881611808, 1, 'JUDGE_COMPILE_ERROR', '0', 'import java.io.*;\nimport java.util.*;\n\npublic class Main\n{\n  public static void main(String args[]) throws Exception\n  {\n    Scanne cin = new Scanner(System.in);\n    int a = cin.nextInt(), b = cin.nextInt();\n    System.out.println(a + b);\n  }\n}\n', 'Main.java:8: error: cannot find symbol\n    Scanne cin = new Scanner(System.in);\n    ^\n  symbol:   class Scanne\n  location: class Main\n1 error\n', 'java');

-- ----------------------------
-- Table structure for e_problem
-- ----------------------------
DROP TABLE IF EXISTS `e_problem`;
CREATE TABLE `e_problem`  (
  `id` bigint NOT NULL,
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `answer_key` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of e_problem
-- ----------------------------
INSERT INTO `e_problem` VALUES (1, '### 标题\n`【编程入门】A+B 输入输出练习I`\n### 题目描述\n`你的任务是计算a+b。这是为了acm初学者专门设计的题目。你肯定发现还有其他题目跟这道题的标题类似，这些问题也都是专门为初学者提供的。\n输入两个数，回车后输出结果，然后再输入一对，回车再输出结果。。。。`\n### 输入内容\n`输入包含一系列的a和b对，通过空格隔开。一对a和b占一行。`\n### 输出内容\n`对于每次输入的每对a和b，你需要依次输出a、b的和。`\n### 输入示例\n`1 5`\n### 输出示例\n`6`', '树老师正在写');
INSERT INTO `e_problem` VALUES (2, '计算1+1,2', '·12312311231231231231\n\n\n### 111');
INSERT INTO `e_problem` VALUES (3, '计算1+1,3', '**1232131**');
INSERT INTO `e_problem` VALUES (4, '123123123', '12312312311231231收到GV阿发的噶发的噶事梵蒂冈 森岛帆高水电费是是否收到');
INSERT INTO `e_problem` VALUES (5, '计算1+1,5', NULL);
INSERT INTO `e_problem` VALUES (6, '计算1+1,1', '我不会啊啊啊啊啊');
INSERT INTO `e_problem` VALUES (7, '计算1+1,2', NULL);
INSERT INTO `e_problem` VALUES (8, '计算1+1,3', '1231312312');
INSERT INTO `e_problem` VALUES (9, '计算1+1,4', NULL);
INSERT INTO `e_problem` VALUES (10, '计算1+1,5', '1231231');
INSERT INTO `e_problem` VALUES (11, '计算1+1,1', '树老师正在写');
INSERT INTO `e_problem` VALUES (12, '计算1+1,2', NULL);
INSERT INTO `e_problem` VALUES (13, '计算1+1,3', NULL);
INSERT INTO `e_problem` VALUES (14, '计算1+1,4', NULL);
INSERT INTO `e_problem` VALUES (15, '计算1+1,5', NULL);
INSERT INTO `e_problem` VALUES (16, '计算1+1,1', '树老师正在写');
INSERT INTO `e_problem` VALUES (17, '计算1+1,2', NULL);
INSERT INTO `e_problem` VALUES (18, '计算1+1,3', NULL);
INSERT INTO `e_problem` VALUES (19, '计算1+1,4', NULL);

SET FOREIGN_KEY_CHECKS = 1;
