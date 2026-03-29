# =========================
# CONFIG
# =========================
DOCKER_COMPOSE = docker-compose -f infrastructure/monitoring/docker-compose.yml

# =========================
# TESTES
# =========================

test: test-unit test-alerts

test-unit:
	@echo "🧪 Rodando testes unitários..."
	mvn test

test-alerts:
	@echo "🚨 Rodando testes de alertas Prometheus..."
	$(DOCKER_COMPOSE) run --rm promtool

test-int:
	@echo "🔗 Rodando testes de integração..."
	mvn verify

# =========================
# MONITORING
# =========================

promtool-shell:
	$(DOCKER_COMPOSE) run --rm promtool sh

# =========================
# DEV
# =========================

up-monitoring:
	@echo "📊 Subindo Prometheus + Grafana..."
	docker-compose -f infrastructure/monitoring/docker-compose.yml up -d

down-monitoring:
	@echo "🛑 Parando monitoring..."
	docker-compose -f infrastructure/monitoring/docker-compose.yml down

# =========================
# CLEAN
# =========================

clean:
	@echo "🧹 Limpando projeto..."
	mvn clean