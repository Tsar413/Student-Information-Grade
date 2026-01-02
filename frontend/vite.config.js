import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
      // 将所有 /api 开头的请求转发到后端服务器
      '/api': {
        target: 'http://localhost:8097', // 这里换成你实际的后端 API 地址
        changeOrigin: true,
        // rewrite: (path) => path.replace(/^\/api/, '') // 如果后端接口本身不带 /api，请取消此行注释
      }
    }
  }
})