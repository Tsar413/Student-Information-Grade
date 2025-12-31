---
title: 默认模块
language_tabs:
  - shell: Shell
  - http: HTTP
  - javascript: JavaScript
  - ruby: Ruby
  - python: Python
  - php: PHP
  - java: Java
  - go: Go
toc_footers: []
includes: []
search: true
code_clipboard: true
highlight_theme: darkula
headingLevel: 2
generator: "@tarslib/widdershins v4.0.30"

---

# 默认模块

Base URLs:

# Authentication

# 成绩

## POST 保存单人成绩

POST /api/studentGrade/singleGrade

> Body 请求参数

```json
{
  "studentGradeId": null,
  "studentId": "2504110103",
  "course": "语文1",
  "grade": 80.5,
  "credit": null,
  "type": null,
  "resitGrade": null,
  "semester": "秋",
  "schoolYear": "2025-2026"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|object| 否 |none|
|» studentGradeId|body|null| 是 |none|
|» studentId|body|string| 是 |none|
|» course|body|string| 是 |none|
|» grade|body|number| 是 |none|
|» credit|body|null| 是 |none|
|» type|body|null| 是 |none|
|» resitGrade|body|null| 是 |none|
|» semester|body|string| 是 |none|
|» schoolYear|body|string| 是 |none|

> 返回示例

> 200 Response

```json
200
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|number|

## PUT 修改单人成绩

PUT /api/studentGrade/singleGrade

> Body 请求参数

```json
{
  "studentGradeId": null,
  "studentId": "2504110103",
  "course": "语文1",
  "grade": 80,
  "credit": null,
  "type": null,
  "resitGrade": null,
  "semester": "秋",
  "schoolYear": "2025-2026"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|object| 否 |none|
|» studentGradeId|body|null| 是 |none|
|» studentId|body|string| 是 |none|
|» course|body|string| 是 |none|
|» grade|body|integer| 是 |none|
|» credit|body|null| 是 |none|
|» type|body|null| 是 |none|
|» resitGrade|body|null| 是 |none|
|» semester|body|string| 是 |none|
|» schoolYear|body|string| 是 |none|

> 返回示例

> 200 Response

```json
200
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|number|

## DELETE 删除单人成绩

DELETE /api/studentGrade/singleGrade

> Body 请求参数

```json
{
  "studentGradeId": null,
  "studentId": "2504110101",
  "course": "鉴赏",
  "grade": 80.5,
  "credit": null,
  "type": null,
  "resitGrade": null,
  "semester": null,
  "schoolYear": null
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|object| 否 |none|
|» studentGradeId|body|null| 是 |none|
|» studentId|body|string| 是 |none|
|» course|body|string| 是 |none|
|» grade|body|number| 是 |none|
|» credit|body|null| 是 |none|
|» type|body|null| 是 |none|
|» resitGrade|body|null| 是 |none|
|» semester|body|null| 是 |none|
|» schoolYear|body|null| 是 |none|

> 返回示例

> 200 Response

```json
200
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|number|

## GET 查询全班成绩

GET /api/studentGrade/singleClass

> Body 请求参数

```json
{
  "classId": "2501",
  "schoolYear": "2025-2026",
  "semester": "秋",
  "courseOrder": [
    "语文",
    "鉴赏"
  ]
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|object| 否 |none|
|» classId|body|string| 是 |none|
|» schoolYear|body|string| 是 |none|
|» semester|body|string| 是 |none|
|» courseOrder|body|[string]| 是 |none|

> 返回示例

> 200 Response

```json
[
  {
    "studentGradeId": "2504110101语文A",
    "studentId": "2504110101",
    "course": "语文",
    "grade": 85,
    "credit": 2,
    "type": "A",
    "resitGrade": null
  },
  {
    "studentGradeId": "2504110101鉴赏A",
    "studentId": "2504110101",
    "course": "鉴赏",
    "grade": 80,
    "credit": 1,
    "type": "A",
    "resitGrade": null
  },
  {
    "studentGradeId": "2504110102语文B",
    "studentId": "2504110102",
    "course": "语文",
    "grade": 59.9,
    "credit": null,
    "type": "B",
    "resitGrade": null
  }
]
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» studentGradeId|string|true|none||none|
|» studentId|string|true|none||none|
|» course|string|true|none||none|
|» grade|integer|true|none||none|
|» credit|integer¦null|true|none||none|
|» type|string|true|none||none|
|» resitGrade|null|true|none||none|

## GET 查询单人成绩

GET /api/studentGrade/singleStudent

> Body 请求参数

```json
{
  "studentId": "2504110101",
  "schoolYear": "2025-2026",
  "semester": "秋",
  "courseOrder": [
    "语文",
    "鉴赏"
  ]
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|object| 是 |none|
|» studentId|body|string| 是 |none|
|» schoolYear|body|string| 是 |none|
|» semester|body|string| 是 |none|
|» courseOrder|body|[string]| 是 |none|

> 返回示例

> 200 Response

```json
[
  {
    "studentGradeId": "2504110101语文A",
    "studentId": "2504110101",
    "course": "语文",
    "grade": 80.5,
    "credit": 2,
    "type": "A",
    "resitGrade": null
  },
  {
    "studentGradeId": "2504110101鉴赏B",
    "studentId": "2504110101",
    "course": "鉴赏",
    "grade": 40,
    "credit": 0,
    "type": "B",
    "resitGrade": null
  }
]
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» studentGradeId|string|true|none||none|
|» studentId|string|true|none||none|
|» course|string|true|none||none|
|» grade|number|true|none||none|
|» credit|integer|true|none||none|
|» type|string|true|none||none|
|» resitGrade|null|true|none||none|

## POST 多人成绩保存

POST /api/studentGrade/multiplyGrades

> Body 请求参数

```yaml
file: file://C:\Users\YinlongLi\Desktop\新建 Microsoft Excel 工作表.xlsx
courseName: 语文1
semester: 秋
schoolYear: 2025-2026

```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|object| 是 |none|
|» file|body|string(binary)| 是 |none|
|» courseName|body|string| 是 |none|
|» semester|body|string| 是 |none|
|» schoolYear|body|string| 是 |none|

> 返回示例

> 200 Response

```json
0
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|number|

## DELETE 多人成绩删除

DELETE /api/studentGrade/multiplyGrades

> Body 请求参数

```json
{
  "studentGradeIds": [
    "2504110107语文1A",
    "2504110109语文1B"
  ],
  "course": "语文1",
  "semester": "秋",
  "schoolYear": "2025-2026"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|object| 是 |none|
|» studentGradeIds|body|[string]| 是 |none|
|» course|body|string| 是 |none|
|» semester|body|string| 是 |none|
|» schoolYear|body|string| 是 |none|

> 返回示例

> 200 Response

```json
200
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|number|

# 课程

## GET 查询班级课表

GET /api/course/courses

> Body 请求参数

```json
{
  "courseId": null,
  "courseName": null,
  "schoolYear": "2025-2026",
  "semester": "秋",
  "type": null,
  "credit": null,
  "classes": "2501"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|object| 是 |none|
|» courseId|body|null| 是 |none|
|» courseName|body|null| 是 |none|
|» schoolYear|body|string| 是 |none|
|» semester|body|string| 是 |none|
|» type|body|null| 是 |none|
|» credit|body|null| 是 |none|
|» classes|body|string| 是 |none|

> 返回示例

> 200 Response

```json
[
  {
    "courseId": "1",
    "courseName": "语文",
    "schoolYear": "2025-2026",
    "semester": "秋",
    "type": "必修课",
    "credit": 2,
    "classes": "2501|2502|2503|2504|2505|"
  },
  {
    "courseId": "2",
    "courseName": "鉴赏",
    "schoolYear": "2025-2026",
    "semester": "秋",
    "type": "选修课",
    "credit": 1,
    "classes": "2501|2401|"
  },
  {
    "courseId": "3",
    "courseName": "数学",
    "schoolYear": "2025-2026",
    "semester": "秋",
    "type": "必修课",
    "credit": 2,
    "classes": "2501|2502|2503|2504|2505|"
  }
]
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» courseId|string|true|none||none|
|» courseName|string|true|none||none|
|» schoolYear|string|true|none||none|
|» semester|string|true|none||none|
|» type|string|true|none||none|
|» credit|integer|true|none||none|
|» classes|string|true|none||none|

## POST 保存课程内容

POST /api/course/courses

> Body 请求参数

```json
{
  "courseId": null,
  "courseName": "语文2",
  "schoolYear": "2025-2026",
  "semester": "春",
  "type": "必修课",
  "credit": 2,
  "classes": "2502"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|object| 是 |none|
|» courseId|body|null| 是 |none|
|» courseName|body|string| 是 |none|
|» schoolYear|body|string| 是 |none|
|» semester|body|string| 是 |none|
|» type|body|string| 是 |none|
|» credit|body|integer| 是 |none|
|» classes|body|string| 是 |none|

> 返回示例

> 200 Response

```json
200
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|number|

## PUT 修改课程信息

PUT /api/course/courses

> Body 请求参数

```json
{
  "courseId": 1,
  "courseName": "语文1",
  "schoolYear": "2025-2026",
  "semester": "秋",
  "type": "必修课",
  "credit": 2,
  "classes": "2501"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|object| 是 |none|
|» courseId|body|integer| 是 |none|
|» courseName|body|string| 是 |none|
|» schoolYear|body|string| 是 |none|
|» semester|body|string| 是 |none|
|» type|body|string| 是 |none|
|» credit|body|integer| 是 |none|
|» classes|body|string| 是 |none|

> 返回示例

> 200 Response

```json
0
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|number|

## DELETE 删除课程

DELETE /api/course/courses

> Body 请求参数

```json
{
  "courseId": 1,
  "courseName": "语文5",
  "schoolYear": "2025-2026",
  "semester": "春",
  "type": "必修课",
  "credit": 2,
  "classes": "2505"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|object| 是 |none|
|» courseId|body|integer| 是 |none|
|» courseName|body|string| 是 |none|
|» schoolYear|body|string| 是 |none|
|» semester|body|string| 是 |none|
|» type|body|string| 是 |none|
|» credit|body|integer| 是 |none|
|» classes|body|string| 是 |none|

> 返回示例

> 200 Response

```json
200
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|number|

## DELETE 删除课程中的班级

DELETE /api/course/classes

> Body 请求参数

```json
{
  "courseId": 1,
  "courseName": "语文1",
  "schoolYear": "2025-2026",
  "semester": "秋",
  "type": "必修课",
  "credit": 2,
  "classes": "2505"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|object| 是 |none|
|» courseId|body|integer| 是 |none|
|» courseName|body|string| 是 |none|
|» schoolYear|body|string| 是 |none|
|» semester|body|string| 是 |none|
|» type|body|string| 是 |none|
|» credit|body|integer| 是 |none|
|» classes|body|string| 是 |none|

> 返回示例

> 200 Response

```json
200
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|number|

# 学生

## GET 获取指定班级学生信息

GET /api/student/classes/{classId}

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|classId|path|string| 是 |none|

> 返回示例

> 200 Response

```json
[
  {
    "studentId": "2504110101",
    "studentName": "江宏伟",
    "studentClass": 2501
  },
  {
    "studentId": "2504110102",
    "studentName": "彭梓涵",
    "studentClass": 2501
  },
  {
    "studentId": "2504110103",
    "studentName": "贺硕硕",
    "studentClass": 2501
  },
  {
    "studentId": "2504110104",
    "studentName": "周涵宇",
    "studentClass": 2501
  },
  {
    "studentId": "2504110105",
    "studentName": "毕文昊",
    "studentClass": 2501
  },
  {
    "studentId": "2504110106",
    "studentName": "刘禹航",
    "studentClass": 2501
  },
  {
    "studentId": "2504110107",
    "studentName": "杨皓",
    "studentClass": 2501
  },
  {
    "studentId": "2504110108",
    "studentName": "徐涛",
    "studentClass": 2501
  },
  {
    "studentId": "2504110109",
    "studentName": "张董平",
    "studentClass": 2501
  },
  {
    "studentId": "2504110110",
    "studentName": "单军浩",
    "studentClass": 2501
  },
  {
    "studentId": "2504110111",
    "studentName": "王阳阳",
    "studentClass": 2501
  },
  {
    "studentId": "2504110112",
    "studentName": "刘雍杰",
    "studentClass": 2501
  },
  {
    "studentId": "2504110113",
    "studentName": "凌炜",
    "studentClass": 2501
  },
  {
    "studentId": "2504110114",
    "studentName": "蔡杨宇",
    "studentClass": 2501
  },
  {
    "studentId": "2504110115",
    "studentName": "胡轩寅",
    "studentClass": 2501
  },
  {
    "studentId": "2504110116",
    "studentName": "邹镇泽",
    "studentClass": 2501
  },
  {
    "studentId": "2504110117",
    "studentName": "王茂成",
    "studentClass": 2501
  },
  {
    "studentId": "2504110118",
    "studentName": "朱皓轩",
    "studentClass": 2501
  },
  {
    "studentId": "2504110119",
    "studentName": "陈笑凌",
    "studentClass": 2501
  },
  {
    "studentId": "2504110120",
    "studentName": "张强柱",
    "studentClass": 2501
  },
  {
    "studentId": "2504110121",
    "studentName": "刘若雪",
    "studentClass": 2501
  },
  {
    "studentId": "2504110122",
    "studentName": "钱雨芯",
    "studentClass": 2501
  },
  {
    "studentId": "2504110123",
    "studentName": "张陶然",
    "studentClass": 2501
  },
  {
    "studentId": "2504110124",
    "studentName": "马小冉",
    "studentClass": 2501
  },
  {
    "studentId": "2504110125",
    "studentName": "周欣悦",
    "studentClass": 2501
  },
  {
    "studentId": "2504110126",
    "studentName": "高涵",
    "studentClass": 2501
  },
  {
    "studentId": "2504110127",
    "studentName": "严清怡",
    "studentClass": 2501
  },
  {
    "studentId": "2504110128",
    "studentName": "顾欣月",
    "studentClass": 2501
  },
  {
    "studentId": "2504110129",
    "studentName": "周子涵",
    "studentClass": 2501
  },
  {
    "studentId": "2504110130",
    "studentName": "王紫灵",
    "studentClass": 2501
  },
  {
    "studentId": "2504110131",
    "studentName": "董自萍",
    "studentClass": 2501
  },
  {
    "studentId": "2504110132",
    "studentName": "杜紫嫣",
    "studentClass": 2501
  },
  {
    "studentId": "2504110133",
    "studentName": "徐淼",
    "studentClass": 2501
  },
  {
    "studentId": "2504110134",
    "studentName": "张嘉绮",
    "studentClass": 2501
  },
  {
    "studentId": "2504110135",
    "studentName": "顾馨妍",
    "studentClass": 2501
  }
]
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» studentId|string|true|none||none|
|» studentName|string|true|none||none|
|» studentClass|integer|true|none||none|

## GET 获取班级号

GET /api/student/classesList

> 返回示例

> 200 Response

```json
[
  "2501",
  "2505"
]
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

## POST 批量导入学生

POST /api/student/multiplyStudents

> Body 请求参数

```yaml
file: file://C:\Users\YinlongLi\Desktop\新建 Microsoft Excel 工作表.xlsx
classId: "2503"

```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|object| 是 |none|
|» file|body|string(binary)| 是 |none|
|» classId|body|string| 是 |none|

> 返回示例

> 200 Response

```json
200
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|number|

## DELETE 批量删除学生

DELETE /api/student/multiplyStudents

> Body 请求参数

```json
{
  "studentIds": [
    "2504108101",
    "2504108102",
    "2504108103",
    "2504108104",
    "2504108105",
    "2504108106"
  ]
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|object| 是 |none|
|» studentIds|body|[string]| 是 |none|

> 返回示例

> 200 Response

```json
200
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|number|

## PUT 修改单人信息

PUT /api/student/singleStudent

> Body 请求参数

```json
{
  "studentId": "2504108101",
  "studentName": "aa",
  "studentClass": 2503
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|object| 是 |none|
|» studentId|body|string| 是 |none|
|» studentName|body|string| 是 |none|
|» studentClass|body|integer| 是 |none|

> 返回示例

> 200 Response

```json
200
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|number|

## POST 保存单人信息

POST /api/student/singleStudent

> Body 请求参数

```json
{
  "studentId": "2504108106",
  "studentName": "bb",
  "studentClass": 2503
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|object| 是 |none|
|» studentId|body|string| 是 |none|
|» studentName|body|string| 是 |none|
|» studentClass|body|integer| 是 |none|

> 返回示例

> 200 Response

```json
200
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|number|

# 数据模型

