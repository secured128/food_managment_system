Heroku commands :


sudo -s
heroku login
heroku container:login (pojhi9078!)
heroku container:push web -a culinarymanager
heroku container:release web -a culinarymanager
heroku open -a culinarymanager

Local run :

docker run -p 8080:8080 com.elalex/culinarymanager:latest



DB connection :

https://devcenter.heroku.com/articles/container-registry-and-runtime

https://devcenter.heroku.com/articles/heroku-postgresql





