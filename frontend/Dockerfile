# Use the official Node.js image for ARM64 architecture
FROM node:14-buster-slim AS builder

# WORKDIR /src

# # Copy package.json and package-lock.json separately to take advantage of Docker's layer caching
# COPY package.json package.json
# COPY package-lock.json package-lock.json

# # Install dependencies and build your application
# RUN npm cache clean --force
# RUN rm -r -f node_modules
# RUN rm -r -f dist
# RUN rm -f package-lock.json
# RUN rm -f node_modules/@angular/compiler-cli/ngcc/__ngcc_lock_file__
RUN mkdir /build
ADD . /build

RUN cd /build && npm install --verbose && node --max_old_space_size=4096 ./node_modules/@angular/cli/bin/ng build --configuration production
# RUN node --max_old_space_size=4096 ./node_modules/@angular/cli/bin/ng build --configuration production
# RUN npm run production

# Stage 2: Use a lightweight Nginx image
FROM nginx

# Copy Nginx configuration files
COPY nginx.conf /etc/nginx/nginx.conf
COPY default.conf /etc/nginx/conf.d/default.conf

# Copy the built application from the builder stage to the Nginx document root
COPY --from=builder /build/dist /usr/share/nginx/html

#CMD ["nginx", "-g", "daemon off;"]