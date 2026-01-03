# 教务管理系统 - 前端项目 (Student Management System FE)

基于 **React 18** 和 **Ant Design 5.x** 构建的教务管理系统前端界面，主要功能涵盖成绩录入、批量操作及学生数据可视化。

## 🚀 核心功能

* **成绩管理**：支持单条成绩录入、修改与删除。
* **多人批量处理**：
* **批量上传**：支持 Excel 文件 (@RequestPart) 配合业务参数 (@RequestParam) 的异步上传。
* **批量删除**：支持同科目下的多人成绩一键清理。


* **多维度查询**：提供班级模式与个人模式切换，支持多科目自定义排序。
* **响应式布局**：适配不同分辨率的教务办公环境。

## 🛠️ 技术栈

* **框架**: React 18 (Hooks)
* **UI 组件库**: Ant Design 5
* **状态管理/路由**: React Router v6
* **网络请求**: Axios
* **构建工具**: Vite / Webpack (请根据你实际使用的工具修改)
* **图标**: @ant-design/icons

## 📦 快速开始

### 1. 克隆项目

```bash
git clone [你的项目地址]
cd [项目文件夹名称]

```

### 2. 安装依赖

```bash
npm install
# 或者使用 yarn
yarn install

```

### 3. 配置开发环境

在项目根目录创建 `.env.development` 文件，配置后端接口地址：

```env
VITE_API_BASE_URL = http://localhost:8080

```

### 4. 启动开发服务器

```bash
npm run dev

```

## 📂 项目结构

```text
src/
├── api/            # Axios 接口定义 (如 grade.js)
├── components/     # 公用组件
├── pages/          # 页面级组件 (Home.jsx, GradeManager.jsx)
├── assets/         # 静态资源 (图片、样式)
├── utils/          # 工具函数
└── App.jsx         # 路由配置与应用入口

```

## 接口规范注意事项

本项目在处理批量操作时遵循以下特殊约定：

* **文件上传**：使用 `multipart/form-data`，文件流通过 Body 传递，业务字段通过 URL Query 传递。
* **批量删除**：使用 `DELETE` 方法，通过 `data` 属性传递包含 `studentGradeIds` 等字段的 JSON 对象。

## 🤝 参与开发

1. 开启新的 Feature 分支 (`git checkout -b feature/AmazingFeature`)
2. 提交你的修改 (`git commit -m 'Add some AmazingFeature'`)
3. 推送到分支 (`git push origin feature/AmazingFeature`)
4. 开启一个 Pull Request

