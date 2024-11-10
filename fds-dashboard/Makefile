hello:
	echo "Hello, ${WORLD}"

docker-build-dev:
	docker build -t fds-fe:${FDS_FE_TAG} . && docker tag fds-fe:${FDS_FE_TAG} devharbor.yokke.co.id/fds/fds-fe:${FDS_FE_TAG}

docker-tag-dev:
	docker tag  fds-fe:${FDS_FE_TAG} devharbor.yokke.co.id/fds/fds-fe:${FDS_FE_TAG}
docker-run-dev:
	docker run -p 3000:3000 fds-fe:${FDS_FE_TAG}

docker-push-dev:
	docker push devharbor.yokke.co.id/fds/fds-fe:${FDS_FE_TAG}

docker-build-push-dev: docker-build-dev docker-push-dev
