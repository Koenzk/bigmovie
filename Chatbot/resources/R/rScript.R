#install the needed packages 

install.packages("RPostgreSQL",repos = "http://cran.r-project.org")
install.packages("DBI",repos = "http://cran.r-project.org")
install.packages("ROCR",repos = "http://cran.r-project.org")
install.packages("gplots",repos = "http://cran.r-project.org")
library("ROCR")
library("RPostgreSQL")
library("gplots")
library("DBI")

#Create a connection to the database

pg = dbDriver("PostgreSQL")
con = dbConnect(pg, user="postgres", password="postgres", host="188.166.120.211", port=5432, dbname="bigmovie")

#Create multiple variables for the barplot

var1 <-dbGetQuery(con, "SELECT AVG(average_rating) FROM ratings INNER JOIN titles ON ratings.tconst=titles.tconst WHERE titles.runtime_minutes > 1 AND titles.runtime_minutes < 30 AND title_type = 'movie'")
var2 <-dbGetQuery(con, "SELECT AVG(average_rating) FROM ratings INNER JOIN titles ON ratings.tconst=titles.tconst WHERE titles.runtime_minutes > 30 AND titles.runtime_minutes < 60 AND title_type = 'movie' ")
var3 <-dbGetQuery(con, "SELECT AVG(average_rating) FROM ratings INNER JOIN titles ON ratings.tconst=titles.tconst WHERE titles.runtime_minutes > 60 AND titles.runtime_minutes < 90 AND title_type = 'movie'")
var4 <-dbGetQuery(con, "SELECT AVG(average_rating) FROM ratings INNER JOIN titles ON ratings.tconst=titles.tconst WHERE titles.runtime_minutes > 90 AND titles.runtime_minutes < 120 AND title_type = 'movie' ")
var5 <-dbGetQuery(con, "SELECT AVG(average_rating) FROM ratings INNER JOIN titles ON ratings.tconst=titles.tconst WHERE titles.runtime_minutes > 120 AND titles.runtime_minutes < 150 AND title_type = 'movie' ")
var6 <-dbGetQuery(con, "SELECT AVG(average_rating) FROM ratings INNER JOIN titles ON ratings.tconst=titles.tconst WHERE titles.runtime_minutes > 150 AND titles.runtime_minutes < 180 AND title_type = 'movie' ")
var7 <-dbGetQuery(con, "SELECT AVG(average_rating) FROM ratings INNER JOIN titles ON ratings.tconst=titles.tconst WHERE titles.runtime_minutes > 180 AND titles.runtime_minutes < 210 AND title_type = 'movie' ")


#Data for the chart
H <- c(var1$avg, var2$avg, var3$avg, var4$avg, var5$avg, var6$avg, var7$avg)
M <- c("0-30", "30-60", "60-90", "90-120", "120-150", "150-180", "180-210")

#Create a png
jpeg(file = "barchart.jpg")


#Create the barplot
barplot(H, col=rainbow(11), ylim=c(6 ,7.5), main='Verband tussen de gemiddelde rating en de lengte van de film', family= "serif", space= 0)

legend("topleft", c("0-30", "30-60", "60-90", "90-120", "120-150", "150-180", "180-210"), cex=1.5, 
       bty="n", fill=rainbow(11));

dev.off()
