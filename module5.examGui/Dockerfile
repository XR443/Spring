FROM node:16-alpine as build-step

LABEL source="https://github.com/HardLOLMaster/SpringExam" \
      docker="https://hub.docker.com/repository/docker/hardlolmaster/property.insurance.test.angular"

RUN mkdir -p /app

WORKDIR /app
COPY package.json /app
RUN npm install
COPY . /app
RUN npm run build --prod


FROM nginx:1.19.10
COPY --from=build-step /app/dist/SpringExam /usr/share/nginx/html
