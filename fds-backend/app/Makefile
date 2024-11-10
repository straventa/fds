hello:
	echo "Hello, ${WORLD}"

docker-build-dev:
	docker build -t fds-be:${FDS_BE_TAG} . && docker tag fds-be:${FDS_BE_TAG} devharbor.yokke.co.id/fds/fds-be:${FDS_BE_TAG}

docker-run-dev:
	docker run -p 2001:2001 devharbor.yokke.co.id/fds/fds-be:${FDS_BE_TAG}

docker-push-dev:
	docker push devharbor.yokke.co.id/fds/fds-be:${FDS_BE_TAG}

docker-build-push-dev: docker-build-dev docker-push-dev
