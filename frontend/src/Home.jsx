import React from 'react';
import { Card, Col, Row, Typography } from 'antd';
import { LineChartOutlined, BookOutlined, UserOutlined } from '@ant-design/icons';
import { useNavigate } from 'react-router-dom'; // 1. 引入跳转钩子

const { Title, Paragraph } = Typography;

const Home = () => {
  const navigate = useNavigate(); // 2. 初始化导航函数

  // 定义卡片配置，方便维护
  const menuItems = [
    {
      title: '成绩管理',
      icon: <LineChartOutlined style={{ fontSize: '24px', color: '#1890ff' }} />,
      description: '录入与查询成绩',
      path: '/grades', // 对应路由配置的路径
    },
    {
      title: '课程管理',
      icon: <BookOutlined style={{ fontSize: '24px', color: '#52c41a' }} />,
      description: '排课与课表查询',
      path: '/courses',
    },
    {
      title: '学生管理',
      icon: <UserOutlined style={{ fontSize: '24px', color: '#722ed1' }} />,
      description: '学生档案与班级',
      path: '/students',
    },
  ];

  return (
    <div style={{ padding: '50px 30px', maxWidth: '1200px', margin: '0 auto' }}>
      <Title level={2} style={{ textAlign: 'center', marginBottom: '60px' }}>
        教务管理系统 - 测试版
      </Title>
      
      <Row gutter={[24, 24]} justify="center">
        {menuItems.map((item) => (
          <Col xs={24} sm={12} md={8} key={item.path}>
            <Card
              hoverable
              style={{ borderRadius: '12px', textAlign: 'center' }}
              // 3. 绑定点击事件
              onClick={() => navigate(item.path)}
            >
              <div style={{ marginBottom: '16px' }}>{item.icon}</div>
              <Title level={4}>{item.title}</Title>
              <Paragraph type="secondary">{item.description}</Paragraph>
            </Card>
          </Col>
        ))}
      </Row>
    </div>
  );
};

export default Home;