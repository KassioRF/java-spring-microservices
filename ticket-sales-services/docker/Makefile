# Compose build, up and down

ENVFILE=.env.example
COMPOSE_FILE=compose-db.yml

compose-up:
	docker-compose -f $(COMPOSE_FILE) --env-file $(ENVFILE) up -d

compose-down:
	docker-compose -f $(COMPOSE_FILE) --env-file $(ENVFILE) down

compose-build:
	docker-compose -f $(COMPOSE_FILE) --env-file $(ENVFILE) build --no-cache

compose-logs:
	docker-compose -f $(COMPOSE_FILE) --env-file $(ENVFILE) logs -f