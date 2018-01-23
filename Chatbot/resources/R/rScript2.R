#Remove hashtags to install the needed packages

install.packages("RPostgreSQL",repos = "http://cran.r-project.org")
install.packages("DBI",repos = "http://cran.r-project.org")
install.packages("ROCR" ,repos = "http://cran.r-project.org")
install.packages("gplots" ,repos = "http://cran.r-project.org")

#Create a connection to the database

library("ROCR")
library("RPostgreSQL")
library("gplots")
library("DBI")
pg = dbDriver("PostgreSQL")

con = dbConnect(pg, user="postgres", password="postgres", host="188.166.120.211", port=5432, dbname="bigmovie")

#Create multiple variables for the barplot

var1 <- dbGetQuery(con, "SELECT count(original_title)
                   FROM titles
                   WHERE start_year BETWEEN 1900 AND 1910 AND title_type = 'movie' AND original_title IN (
                   SELECT title
                   FROM akas
                   WHERE types IS NULL AND is_original_title = false AND region = 'NL'
                   )")

var2 <- dbGetQuery(con, "SELECT count(original_title)
                   FROM titles
                   WHERE start_year BETWEEN 1910 AND 1920 AND title_type = 'movie' AND original_title IN (
                   SELECT title
                   FROM akas
                   WHERE types IS NULL AND is_original_title = false AND region = 'NL'
                   )")
var3 <- dbGetQuery(con, "SELECT count(original_title)
                   FROM titles
                   WHERE start_year BETWEEN 1920 AND 1930 AND title_type = 'movie' AND original_title IN (
                   SELECT title
                   FROM akas
                   WHERE types IS NULL AND is_original_title = false AND region = 'NL'
                   )")
var4 <- dbGetQuery(con, "SELECT count(original_title)
                   FROM titles
                   WHERE start_year BETWEEN 1930 AND 1940 AND title_type = 'movie' AND original_title IN (
                   SELECT title
                   FROM akas
                   WHERE types IS NULL AND is_original_title = false AND region = 'NL'
                   )")
var5 <- dbGetQuery(con, "SELECT count(original_title)
                   FROM titles
                   WHERE start_year BETWEEN 1940 AND 1950 AND title_type = 'movie' AND original_title IN (
                   SELECT title
                   FROM akas
                   WHERE types IS NULL AND is_original_title = false AND region = 'NL'
                   )")
var6 <- dbGetQuery(con, "SELECT count(original_title)
                   FROM titles
                   WHERE start_year BETWEEN 1950 AND 1960 AND title_type = 'movie' AND original_title IN (
                   SELECT title
                   FROM akas
                   WHERE types IS NULL AND is_original_title = false AND region = 'NL'
                   )")
var7 <- dbGetQuery(con, "SELECT count(original_title)
                   FROM titles
                   WHERE start_year BETWEEN 1960 AND 1970 AND title_type = 'movie' AND original_title IN (
                   SELECT title
                   FROM akas
                   WHERE types IS NULL AND is_original_title = false AND region = 'NL'
                   )")
var8 <- dbGetQuery(con, "SELECT count(original_title)
                   FROM titles
                   WHERE start_year BETWEEN 1970 AND 1980 AND title_type = 'movie' AND original_title IN (
                   SELECT title
                   FROM akas
                   WHERE types IS NULL AND is_original_title = false AND region = 'NL'
                   )")
var9 <- dbGetQuery(con, "SELECT count(original_title)
                   FROM titles
                   WHERE start_year BETWEEN 1980 AND 1990 AND title_type = 'movie' AND original_title IN (
                   SELECT title
                   FROM akas
                   WHERE types IS NULL AND is_original_title = false AND region = 'NL'
                   )")
var10 <- dbGetQuery(con, "SELECT count(original_title)
                    FROM titles
                    WHERE start_year BETWEEN 1990 AND 2000 AND title_type = 'movie' AND original_title IN (
                    SELECT title
                    FROM akas
                    WHERE types IS NULL AND is_original_title = false AND region = 'NL'
                    )")
var11 <- dbGetQuery(con, "SELECT count(original_title)
                    FROM titles
                    WHERE start_year BETWEEN 2000 AND 2010 AND title_type = 'movie' AND original_title IN (
                    SELECT title
                    FROM akas
                    WHERE types IS NULL AND is_original_title = false AND region = 'NL'
                    )")
var12 <- dbGetQuery(con, "SELECT count(original_title)
                    FROM titles
                    WHERE start_year BETWEEN 2010 AND 2020 AND title_type = 'movie' AND original_title IN (
                    SELECT title
                    FROM akas
                    WHERE types IS NULL AND is_original_title = false AND region = 'NL'
                    )")

#Create model

#Data for the chart
H <- c(var1$count, var2$count, var3$count, var4$count, var5$count, var6$count, var7$count, var8$count, var9$count, var10$count, var11$count, var12$count)

jpeg(file = "barchart2.jpg", width = 800, height = 600)

barplot(H, col=rainbow(11), main='Aantal films uit nederland', ylab = "Aantal", xlab = "Jaartal, legenda voor meer informatie.", family= "serif", space= 0)

legend("topleft", c("1900-1910","1910-1920","1920-1930","1930-1940","1940-1950", "1950-1960", "1960-1970", "1970-1980", "1980-1990", "1990-2000", "2000-2010", "2010-2020"), cex=2, 
       bty="n", fill=rainbow(11));

dev.off()
