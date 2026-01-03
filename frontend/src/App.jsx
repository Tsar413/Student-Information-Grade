import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import { Layout, Menu, ConfigProvider } from 'antd';
import zhCN from 'antd/es/locale/zh_CN'; // 引入中文包
import Home from './Home';
import StudentManager from './StudentManager';
import CourseManager from './CourseManager';
import GradeManager from './GradeManager'; // 引入新页面

const { Header, Content } = Layout;

function App() {
  return (
    <ConfigProvider locale={zhCN}>
      <Router>
        <Layout style={{ minHeight: '100vh' }}>
          <Header style={{ display: 'flex', alignItems: 'center' }}>
            <div style={{ color: 'white', fontWeight: 'bold', marginRight: '20px' }}>教务系统</div>
            <Menu theme="dark" mode="horizontal" defaultSelectedKeys={['1']}>
              <Menu.Item key="1"><Link to="/">首页</Link></Menu.Item>
              <Menu.Item key="2"><Link to="/students">学生管理</Link></Menu.Item>
              <Menu.Item key="3"><Link to="/courses">课程管理</Link></Menu.Item>
              <Menu.Item key="4"><Link to="/grades">成绩管理</Link></Menu.Item>
            </Menu>
          </Header>
          <Content style={{ padding: '24px', background: '#f0f2f5' }}>
            <Routes>
              <Route path="/" element={<Home />} />
              <Route path="/students" element={<StudentManager />} />
              <Route path="/courses" element={<CourseManager />} />
              <Route path="/grades" element={<GradeManager />} />
            </Routes>
          </Content>
        </Layout>
      </Router>
    </ConfigProvider>
  );
}

export default App;