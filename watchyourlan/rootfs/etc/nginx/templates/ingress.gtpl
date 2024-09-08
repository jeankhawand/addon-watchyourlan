server {
    listen 8099 default_server;

    include /etc/nginx/includes/server_params.conf;
    include /etc/nginx/includes/proxy_params.conf;

    allow   172.30.32.2;
    allow   127.0.0.1;
    deny    all;

   location / {
        absolute_redirect off;
        proxy_redirect off;
        proxy_buffering off;
        sub_filter_once off;
        sub_filter_types *;
        sub_filter '/api/all' '{{ .entry }}/api/all';
        sub_filter 'src="/' 'src="{{ .entry }}/';
        sub_filter 'href="/' 'href="{{ .entry }}/';
        sub_filter 'action="/' 'action="{{ .entry }}/';
        proxy_pass http://backend;
    }
}