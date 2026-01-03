# 教务管理系统 - 后端项目 (study-studentOA)

基于 **Spring Boot 2.1.0** 开发的教务办公自动化系统后端，集成了成绩管理、课程编排及学生档案维护等核心功能，并支持通过 EasyExcel 进行大数据量的批量导入导出。

---

## 🛠️ 技术栈

| 领域 | 选型 | 说明 |
| :--- | :--- | :--- |
| **核心框架** | Spring Boot 2.1.0.RELEASE | 基础 Web 容器及组件管理 |
| **持久层** | MyBatis Plus 3.4.3 | 简化 CRUD 操作与分页查询 |
| **数据访问** | Spring Data JPA | 辅助实体映射与数据库交互 |
| **数据库** | MySQL | 核心关系型数据库 |
| **Excel 处理** | Alibaba EasyExcel 3.3.2 | 高性能、低内存占用的 Excel 解析库 |
| **工具/协议** | Java 8 / Maven | 开发环境及依赖管理 |

## 🚀 快速启动

### 1. 环境要求
- JDK 1.8+
- MySQL 5.7+
- Maven 3.6+

### 2. 数据库配置
在 `src/main/resources/application.yml`（或 properties）中配置数据库连接：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/student_oa?useUnicode=true&characterEncoding=UTF-8
    username: root
    password: yourpassword

```

### 3. 构建与运行

```bash
mvn clean install
mvn spring-boot:run

```

## 📖 核心 API 概览

### 1. 成绩模块 (Grade)

* **保存/修改/删除单人成绩**：`/api/studentGrade/singleGrade` (POST/PUT/DELETE)
* **查询班级成绩**：`GET /api/studentGrade/singleClass`
* **多人成绩批量导入**：`POST /api/studentGrade/multiplyGrades` (支持 Excel 上传)
* **多人成绩批量删除**：`DELETE /api/studentGrade/multiplyGrades` (接收 JSON DTO)

### 2. 课程模块 (Course)

* **查询班级课表**：`GET /api/course/courses`
* **课程维护**：`POST/PUT/DELETE /api/course/courses` (课程信息的增删改)
* **班级关联管理**：`DELETE /api/course/classes` (从特定课程中移除班级)

### 3. 学生模块 (Student)

* **基础查询**：`GET /api/student/classes/{classId}` (获取名册)
* **班级列表**：`GET /api/student/classesList` (获取所有班级号)
* **批量导入**：`POST /api/student/multiplyStudents` (Excel 导入)
* **批量删除**：`DELETE /api/student/multiplyStudents` (通过 ID 数组)

## 📦 关键业务逻辑说明

### EasyExcel 集成

项目使用 `com.alibaba:easyexcel` 处理 `POST /api/studentGrade/multiplyGrades` 接口。

* **上传要求**：文件字段名必须为 `file`，内容类型为 `multipart/form-data`。
* **参数映射**：后端通过 `@RequestParam` 接收 `courseName`, `semester` 等业务上下文参数，并结合 Excel 行数据进行入库。

### 批量删除 DTO

批量删除成绩接口需要前端发送完整的 JSON 对象以确保业务准确性：

```json
{
  "studentGradeIds": ["ID1", "ID2"],
  "course": "语文1",
  "semester": "秋",
  "schoolYear": "2025-2026"
}

```

## 📜 依赖版本说明 (Excerpt from POM)

* **Spring Boot**: 2.1.0.RELEASE
* **MyBatis-Plus**: 3.4.3
* **EasyExcel**: 3.3.2
* **Java Version**: 1.8

