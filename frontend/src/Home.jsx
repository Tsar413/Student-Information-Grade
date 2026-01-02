import { Card, Col, Row, Typography } from 'antd';
import { LineChartOutlined, BookOutlined, UserOutlined } from '@ant-design/icons';

const { Title } = Typography;

const Home = () => {
  return (
    <div style={{ padding: '30px' }}>
      <Title level={2} style={{ textAlign: 'center' }}>教务管理系统 - 测试版</Title>
      <Row gutter={16} justify="center" style={{ marginTop: '40px' }}>
        <Col span={6}>
          <Card hoverable title="成绩管理" icon={<LineChartOutlined />}>录入与查询成绩</Card>
        </Col>
        <Col span={6}>
          <Card hoverable title="课程管理" icon={<BookOutlined />}>排课与课表查询</Card>
        </Col>
        <Col span={6}>
          <Card hoverable title="学生管理" icon={<UserOutlined />}>学生档案与班级</Card>
        </Col>
      </Row>
    </div>
  );
};

export default Home;