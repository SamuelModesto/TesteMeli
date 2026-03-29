#!/bin/bash

echo "🧪 Rodando testes unitários..."
mvn test

echo "🚨 Rodando testes de alertas..."
docker-compose -f infrastructure/monitoring/docker-compose.yml run --rm promtool