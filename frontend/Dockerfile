FROM node:16 AS build-step
WORKDIR /app

RUN mkdir -p /opt/node_modules

COPY package.json /app/
COPY package-lock.json /app/

RUN npm ci

COPY ./ /app/

RUN npm run build

FROM nginx:1.23.1-alpine

COPY nginx.conf /etc/nginx/nginx.conf

COPY --from=build-step /app/dist /usr/share/nginx/html
