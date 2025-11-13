#!/bin/bash

# 青云平台部署脚本
# 使用方法: ./deploy.sh [start|stop|restart|status|logs|update]

set -e

# 颜色输出
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# 项目目录
PROJECT_DIR="/opt/qingyun-platform"
cd "$PROJECT_DIR" || exit 1

# 函数：打印信息
info() {
    echo -e "${GREEN}[INFO]${NC} $1"
}

warn() {
    echo -e "${YELLOW}[WARN]${NC} $1"
}

error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# 函数：检查Docker和Docker Compose
check_docker() {
    if ! command -v docker &> /dev/null; then
        error "Docker未安装，请先安装Docker"
        exit 1
    fi
    
    if ! command -v docker-compose &> /dev/null; then
        error "Docker Compose未安装，请先安装Docker Compose"
        exit 1
    fi
    
    info "Docker环境检查通过"
}

# 函数：创建必要目录
create_directories() {
    info "创建必要的目录结构..."
    mkdir -p data/{mysql,redis,nacos/{logs,data}}
    mkdir -p logs/{gateway,auth,user-service,question-service,record-service,admin-service,stat-service,web-admin,web-user}
    mkdir -p config/{mysql,redis}
    info "目录创建完成"
}

# 函数：启动服务
start_services() {
    info "启动所有服务..."
    docker-compose up -d
    info "等待服务启动..."
    sleep 10
    docker-compose ps
    info "服务启动完成"
}

# 函数：停止服务
stop_services() {
    info "停止所有服务..."
    docker-compose down
    info "服务已停止"
}

# 函数：重启服务
restart_services() {
    info "重启所有服务..."
    docker-compose restart
    info "服务重启完成"
}

# 函数：查看状态
show_status() {
    info "查看服务状态..."
    docker-compose ps
    echo ""
    info "查看资源使用情况..."
    docker stats --no-stream
}

# 函数：查看日志
show_logs() {
    if [ -z "$2" ]; then
        info "查看所有服务日志（最近100行）..."
        docker-compose logs --tail=100
    else
        info "查看 $2 服务日志..."
        docker-compose logs -f "$2"
    fi
}

# 函数：更新服务
update_services() {
    info "更新服务..."
    git pull || warn "Git拉取失败，继续使用当前代码"
    info "重新构建镜像..."
    docker-compose build
    info "重启服务..."
    docker-compose up -d
    info "更新完成"
}

# 函数：备份数据
backup_data() {
    BACKUP_DIR="backup/$(date +%Y%m%d_%H%M%S)"
    mkdir -p "$BACKUP_DIR"
    
    info "备份MySQL数据..."
    docker exec qingyun-mysql mysqldump -uroot -p"${MYSQL_ROOT_PASSWORD:-qingyun123456}" qingyun_db > "$BACKUP_DIR/mysql_backup.sql" || warn "MySQL备份失败"
    
    info "备份Redis数据..."
    docker exec qingyun-redis redis-cli SAVE
    docker cp qingyun-redis:/data/dump.rdb "$BACKUP_DIR/redis_backup.rdb" || warn "Redis备份失败"
    
    info "备份完成，备份目录: $BACKUP_DIR"
}

# 主函数
main() {
    case "$1" in
        start)
            check_docker
            create_directories
            start_services
            ;;
        stop)
            check_docker
            stop_services
            ;;
        restart)
            check_docker
            restart_services
            ;;
        status)
            check_docker
            show_status
            ;;
        logs)
            check_docker
            show_logs "$@"
            ;;
        update)
            check_docker
            update_services
            ;;
        backup)
            check_docker
            backup_data
            ;;
        *)
            echo "使用方法: $0 {start|stop|restart|status|logs [service]|update|backup}"
            echo ""
            echo "命令说明:"
            echo "  start    - 启动所有服务"
            echo "  stop     - 停止所有服务"
            echo "  restart  - 重启所有服务"
            echo "  status   - 查看服务状态"
            echo "  logs     - 查看日志（可指定服务名）"
            echo "  update   - 更新并重启服务"
            echo "  backup   - 备份数据"
            exit 1
            ;;
    esac
}

main "$@"

