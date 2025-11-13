#!/bin/bash

# 快速修复部署脚本
# 用于修复项目目录结构问题

set -e

PROJECT_DIR="/opt/qingyun-platform"
cd "$PROJECT_DIR" || exit 1

echo "检查项目结构..."

if [ -d "QingyunInterview" ]; then
    echo "检测到项目在 QingyunInterview 子目录中"
    echo "正在移动文件到根目录..."
    
    # 移动所有文件
    mv QingyunInterview/* . 2>/dev/null || true
    mv QingyunInterview/.git* . 2>/dev/null || true
    mv QingyunInterview/.dockerignore . 2>/dev/null || true
    mv QingyunInterview/.gitignore . 2>/dev/null || true
    
    # 删除空目录
    rmdir QingyunInterview 2>/dev/null || rm -rf QingyunInterview
    
    echo "文件移动完成"
fi

# 检查必要文件
if [ ! -f "docker-compose.yml" ]; then
    echo "错误: 找不到 docker-compose.yml 文件"
    exit 1
fi

# 创建必要目录
echo "创建必要的目录结构..."
mkdir -p logs/{gateway,auth,user-service,question-service,record-service,admin-service,stat-service,web-admin,web-user}
mkdir -p data/{mysql,redis,nacos/{logs,data}}
mkdir -p config/{mysql,redis}

# 检查 .env 文件
if [ ! -f ".env" ]; then
    echo "创建 .env 文件..."
    cat > .env << 'EOF'
# 数据库配置
MYSQL_ROOT_PASSWORD=123456
MYSQL_DATABASE=qingyun_db
MYSQL_USER=qingyun
MYSQL_PASSWORD=123456

# Redis配置
REDIS_PASSWORD=123456

# Nacos配置
NACOS_SERVER=localhost:8848
NACOS_USERNAME=nacos
NACOS_PASSWORD=123456

# 服务端口配置
GATEWAY_PORT=8080
AUTH_PORT=8000
USER_SERVICE_PORT=8001
QUESTION_SERVICE_PORT=8002
RECORD_SERVICE_PORT=8003
ADMIN_SERVICE_PORT=8004
STAT_SERVICE_PORT=8005
WEB_ADMIN_PORT=9001
WEB_USER_PORT=9002

# JVM参数
JVM_OPTS=-Xms512m -Xmx1024m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=256m
EOF
    echo ".env 文件创建完成"
else
    echo ".env 文件已存在"
fi

echo ""
echo "✅ 修复完成！现在可以执行以下命令："
echo "  docker-compose build"
echo "  docker-compose up -d"

