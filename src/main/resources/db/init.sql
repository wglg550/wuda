-- MySQL dump 10.13  Distrib 5.7.26, for macos10.14 (x86_64)
--
-- Host: localhost    Database: wuda
-- ------------------------------------------------------
-- Server version	8.0.20

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `sequence`
--

DROP TABLE IF EXISTS `sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sequence` (
  `name` varchar(50) NOT NULL,
  `current_value` int NOT NULL,
  `increment` int NOT NULL DEFAULT '1',
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='序列表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_b_attachment`
--

DROP TABLE IF EXISTS `t_b_attachment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_b_attachment` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '附件名称',
  `path` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '路径',
  `type` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类型',
  `size` double NOT NULL COMMENT '大小',
  `md5` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'MD5',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `create_time` bigint DEFAULT NULL COMMENT '创建时间',
  `create_id` bigint DEFAULT NULL COMMENT '创建人id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=71545027200811009 DEFAULT CHARSET=utf8 COMMENT='附件表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_b_dimension`
--

DROP TABLE IF EXISTS `t_b_dimension`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_b_dimension` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `module_id` bigint NOT NULL COMMENT '模块id',
  `course_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '科目名称',
  `course_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '科目编码',
  `knowledge_first` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '知识点一级',
  `identifier_first` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '编号一级',
  `knowledge_second` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '知识点二级',
  `identifier_second` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '编号二级',
  `description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=71238625618034691 DEFAULT CHARSET=utf8 COMMENT='维度信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_b_level`
--

DROP TABLE IF EXISTS `t_b_level`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_b_level` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `school_id` bigint DEFAULT NULL COMMENT '学校id',
  `module_id` bigint DEFAULT NULL COMMENT '模块id',
  `code` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '等级',
  `level` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '层次',
  `rule` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '规则',
  `degree` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '分数范围',
  `diagnose_result` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '诊断结果',
  `learn_advice` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '学习建议',
  `formula` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '换算公式',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=71196741877104652 DEFAULT CHARSET=utf8 COMMENT='等级信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_b_major`
--

DROP TABLE IF EXISTS `t_b_major`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_b_major` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `school_id` bigint NOT NULL COMMENT '学校id',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '专业名称',
  `code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '编码',
  `create_id` bigint DEFAULT NULL COMMENT '创建人',
  `create_time` bigint DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=69488928901562369 DEFAULT CHARSET=utf8 COMMENT='专业信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_b_module`
--

DROP TABLE IF EXISTS `t_b_module`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_b_module` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `school_id` bigint DEFAULT NULL COMMENT '学校id',
  `college_id` bigint DEFAULT NULL COMMENT '学院id',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '名称',
  `code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '编码',
  `description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '描述',
  `proficiency` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '熟练度定义',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '备注',
  `degree` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '熟练度等级范围',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=71196741877104642 DEFAULT CHARSET=utf8 COMMENT='模块信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_b_school`
--

DROP TABLE IF EXISTS `t_b_school`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_b_school` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '学校名称',
  `code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '学校编码',
  `create_id` bigint DEFAULT NULL COMMENT '创建人',
  `create_time` bigint DEFAULT NULL COMMENT '创建时间',
  `enable` tinyint DEFAULT '1' COMMENT '是否启用，0：停用，1：启用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='学校信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_b_school_college`
--

DROP TABLE IF EXISTS `t_b_school_college`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_b_school_college` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `school_id` bigint NOT NULL COMMENT '学校id',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '学院名称',
  `code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '学院编码',
  `create_id` bigint DEFAULT NULL COMMENT '创建人',
  `create_time` bigint DEFAULT NULL COMMENT '创建时间',
  `enable` tinyint DEFAULT '1' COMMENT '是否启用，0：停用，1：启用',
  `access_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '密钥key',
  `access_secret` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '密钥secret',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=71609175179788289 DEFAULT CHARSET=utf8 COMMENT='学院信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_b_teacher`
--

DROP TABLE IF EXISTS `t_b_teacher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_b_teacher` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `school_id` bigint NOT NULL COMMENT '学校id',
  `college_id` bigint DEFAULT NULL COMMENT '学院id',
  `major_id` bigint DEFAULT NULL COMMENT '专业id',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '姓名',
  `enable` tinyint DEFAULT '1' COMMENT '是否启用，0：停用，1：启用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=69488926967988316 DEFAULT CHARSET=utf8 COMMENT='教师信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_b_teacher_exam_student`
--

DROP TABLE IF EXISTS `t_b_teacher_exam_student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_b_teacher_exam_student` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `teacher_id` bigint DEFAULT NULL COMMENT '教师id',
  `exam_student_id` bigint DEFAULT NULL COMMENT '考生主键',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=69488934916194440 DEFAULT CHARSET=utf8 COMMENT='教师考生关系表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_b_user`
--

DROP TABLE IF EXISTS `t_b_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_b_user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `school_id` bigint NOT NULL COMMENT '学校id',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '姓名',
  `login_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '登录名',
  `password` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `create_id` bigint DEFAULT NULL COMMENT '创建人',
  `create_time` bigint DEFAULT NULL COMMENT '创建时间',
  `enable` tinyint DEFAULT '1' COMMENT '是否启用，0：停用，1：启用',
  `college_id` bigint DEFAULT NULL COMMENT '学院id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_e_answer`
--

DROP TABLE IF EXISTS `t_e_answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_e_answer` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `exam_record_id` bigint NOT NULL COMMENT '考试记录id',
  `main_number` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '大题号',
  `sub_number` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '小题号',
  `type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '类型',
  `answer` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '作答内容',
  `score` double(8,4) DEFAULT NULL COMMENT '分数',
  `history` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '作答轨迹',
  `duration_seconds` int DEFAULT NULL COMMENT '作答时长',
  `version` bigint DEFAULT NULL COMMENT '版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=69488934920388675 DEFAULT CHARSET=utf8 COMMENT='考生作答信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_e_course`
--

DROP TABLE IF EXISTS `t_e_course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_e_course` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `exam_id` bigint DEFAULT NULL COMMENT '考试id',
  `course_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '科目名称',
  `course_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '科目编码',
  `create_id` bigint DEFAULT NULL COMMENT '创建人',
  `create_time` bigint DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `course_schoolId_courseName_courseCode` (`exam_id`,`course_name`,`course_code`)
) ENGINE=InnoDB AUTO_INCREMENT=71238625433485313 DEFAULT CHARSET=utf8 COMMENT='科目信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_e_exam`
--

DROP TABLE IF EXISTS `t_e_exam`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_e_exam` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '名称',
  `code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '编码',
  `enable` tinyint DEFAULT '1' COMMENT '是否启用，0：停用，1：启用',
  `create_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人',
  `create_time` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建时间',
  `access_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '密钥key',
  `access_secret` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '密钥secret',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=69814228445298689 DEFAULT CHARSET=utf8 COMMENT='考试批次表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_e_exam_record`
--

DROP TABLE IF EXISTS `t_e_exam_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_e_exam_record` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `exam_id` bigint NOT NULL COMMENT '考试批次id',
  `exam_student_id` bigint NOT NULL COMMENT '考生主键',
  `paper_id` bigint NOT NULL COMMENT '试卷id',
  `objective_score` double(8,4) DEFAULT NULL COMMENT '客观分',
  `subjective_score` double(8,4) DEFAULT NULL COMMENT '主观分',
  `sum_score` double(8,4) DEFAULT NULL COMMENT '总分',
  `create_id` bigint DEFAULT NULL COMMENT '创建人',
  `create_time` bigint DEFAULT NULL COMMENT '创建时间',
  `mark_detail` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '评分明细',
  `remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=69488934916194441 DEFAULT CHARSET=utf8 COMMENT='考试记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_e_exam_student`
--

DROP TABLE IF EXISTS `t_e_exam_student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_e_exam_student` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `exam_id` bigint NOT NULL COMMENT '考试批次id',
  `student_id` bigint NOT NULL COMMENT '学生id',
  `course_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '科目名称',
  `course_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '科目编码',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '姓名',
  `student_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '学号',
  `exam_number` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '准考证号',
  `grade` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '年级',
  `major_id` bigint DEFAULT NULL COMMENT '专业id',
  `miss` tinyint DEFAULT '0' COMMENT '是否缺考，0：否，1：是',
  `enable` tinyint DEFAULT '1' COMMENT '是否启用，0：停用，1：启用',
  `class_no` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '班级',
  `college_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '学院id',
  `create_id` bigint DEFAULT NULL COMMENT '创建人',
  `create_time` bigint DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=69488934916194439 DEFAULT CHARSET=utf8 COMMENT='考生信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_e_paper`
--

DROP TABLE IF EXISTS `t_e_paper`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_e_paper` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `exam_id` bigint NOT NULL COMMENT '考试批次id',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '名称',
  `code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '编码',
  `contribution_score` double(8,4) DEFAULT NULL COMMENT '赋分',
  `contribution` tinyint DEFAULT NULL COMMENT '是否赋分，0：不启用，1：启用',
  `total_score` double(8,4) NOT NULL COMMENT '试卷总分',
  `create_id` bigint DEFAULT NULL COMMENT '创建人',
  `create_time` bigint DEFAULT NULL COMMENT '创建时间',
  `pass_score` double(8,4) NOT NULL COMMENT '及格分',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=71545028391993345 DEFAULT CHARSET=utf8 COMMENT='试卷信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_e_paper_struct`
--

DROP TABLE IF EXISTS `t_e_paper_struct`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_e_paper_struct` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `paper_id` bigint NOT NULL COMMENT '试卷id',
  `main_number` int NOT NULL COMMENT '大题号',
  `sub_number` int NOT NULL COMMENT '小题号',
  `type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '类型',
  `score` double(8,4) NOT NULL COMMENT '题目分数',
  `rule` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '计分规则',
  `description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '规则说明',
  `knowledge` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '知识模块',
  `capability` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '能力模块',
  `create_id` bigint DEFAULT NULL COMMENT '创建人',
  `create_time` bigint DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=71545028396187668 DEFAULT CHARSET=utf8 COMMENT='试卷结构题型说明';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_e_student`
--

DROP TABLE IF EXISTS `t_e_student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_e_student` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `school_id` bigint NOT NULL COMMENT '学校id',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '姓名',
  `password` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `student_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '学号',
  `age` int DEFAULT NULL COMMENT '年龄',
  `gender` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '性别',
  `mobile_number` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '手机号',
  `base_photo_path` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '底照',
  `enable` tinyint DEFAULT '1' COMMENT '是否启用，0：停用，1：启用',
  `create_id` bigint DEFAULT NULL COMMENT '创建人',
  `create_time` bigint DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=69488934916194438 DEFAULT CHARSET=utf8 COMMENT='学生信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping routines for database 'wuda'
--
/*!50003 DROP FUNCTION IF EXISTS `currval` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `currval`(seq_name VARCHAR(50)) RETURNS int
    DETERMINISTIC
BEGIN
     DECLARE value INTEGER;
     SET value = 0;
     SELECT current_value INTO value
          FROM sequence
          WHERE name = seq_name;
     RETURN value;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `nextval` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `nextval`(seq_name VARCHAR(50)) RETURNS int
    DETERMINISTIC
BEGIN
     UPDATE sequence
          SET current_value = current_value + increment
          WHERE name = seq_name;
     RETURN currval(seq_name);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `setval` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `setval`(seq_name VARCHAR(50), value INTEGER) RETURNS int
    DETERMINISTIC
BEGIN
     UPDATE sequence
          SET current_value = value
          WHERE name = seq_name;
     RETURN currval(seq_name);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-12-16  9:07:16
