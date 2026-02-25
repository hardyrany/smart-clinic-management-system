FROM nginx:alpine

# Remove arquivos default do Nginx
RUN rm -rf /usr/share/nginx/html/*

# Copia configuração customizada do Nginx
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Copia arquivos do frontend
COPY . /usr/share/nginx/html

# Ajusta permissões (muito importante no Alpine)
RUN chmod -R 755 /usr/share/nginx/html