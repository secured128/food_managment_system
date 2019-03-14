Heroku commands :


sudo -s
heroku login
heroku container:login (pojhi9078!)
heroku container:push web -a parisbakery
heroku container:release web -a parisbakery
heroku open -a parisbakery

Local run :

docker run -p 8080:8080 parisbakery/parisbakery:latest



DB connection :

https://devcenter.heroku.com/articles/container-registry-and-runtime

https://devcenter.heroku.com/articles/heroku-postgresql
