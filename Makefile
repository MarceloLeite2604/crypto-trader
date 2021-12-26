.PHONY: clean build docker-compose all

clean:
	./mvnw clean

build: clean
	./mvnw package

docker-image: build
	docker image build -t crypto-trader:0.1 .

docker-compose: docker-image
	@docker-compose up --force-recreate