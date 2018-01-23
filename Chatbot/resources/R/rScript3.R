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

var1 <- dbGetQuery(con, "SELECT sum(case when (lower(primary_title) LIKE '%1%' OR lower(primary_title) LIKE '%one%' OR lower(primary_title) LIKE '%first%' OR lower(primary_title) LIKE '%ten%' OR lower(primary_title) LIKE '%eleven%' OR lower(primary_title) LIKE '%twelve%' OR lower(primary_title) LIKE '%thirteen%' OR lower(primary_title) LIKE '%twelfth%' OR lower(primary_title) LIKE '%fourteen%' OR lower(primary_title) LIKE '%fifteen%' OR lower(primary_title) LIKE '%sixteen%' OR lower(primary_title) LIKE '%seventeen%' OR lower(primary_title) LIKE '%eighteen%' OR lower(primary_title) LIKE '%nineteen%') then 1 end) AS no1,
                   sum(case when (lower(primary_title) LIKE '%2%' OR lower(primary_title) LIKE '%two%' OR lower(primary_title) LIKE '%second' OR lower(primary_title) LIKE '%twelve%' OR lower(primary_title) LIKE '%twenty%' OR lower(primary_title) LIKE '%twentieth%') then 1 end) AS no2,
                   sum(case when (lower(primary_title) LIKE '%3%' OR lower(primary_title) LIKE '%three%' OR lower(primary_title) LIKE '%third' OR lower(primary_title) LIKE '%thirteen%' OR lower(primary_title) LIKE '%thirty%' OR lower(primary_title) LIKE '%thirtieth%') then 1 end) AS no3,
                   sum(case when (lower(primary_title) LIKE '%4%' OR lower(primary_title) LIKE '%four%' OR lower(primary_title) LIKE '%forty%' OR lower(primary_title) LIKE '%fortieth%') then 1 end) AS no4,
                   sum(case when (lower(primary_title) LIKE '%5%' OR lower(primary_title) LIKE '%five%' OR lower(primary_title) LIKE '%fifth' OR lower(primary_title) LIKE '%fifty%' OR lower(primary_title) LIKE '%fiftieth%') then 1 end) AS no5,
                   sum(case when (lower(primary_title) LIKE '%6%' OR lower(primary_title) LIKE '%six%') then 1 end) AS no6,
                   sum(case when (lower(primary_title) LIKE '%7%' OR lower(primary_title) LIKE '%seven%') then 1 end) AS no7,
                   sum(case when (lower(primary_title) LIKE '%8%' OR lower(primary_title) LIKE '%eight') then 1 end) AS no8,
                   sum(case when (lower(primary_title) LIKE '%9%' OR lower(primary_title) LIKE '%nine%' OR lower(primary_title) LIKE '%ninth') then 1 end) AS no9,
                   sum(case when (lower(primary_title) LIKE '%0%' OR lower(primary_title) LIKE '%zero%' OR lower(primary_title) LIKE '%ten%' OR lower(primary_title) LIKE '%twenty%' OR lower(primary_title) LIKE '%thirty%' OR lower(primary_title) LIKE '%forty%' OR lower(primary_title) LIKE '%fifty%' OR lower(primary_title) LIKE '%sixty%' OR lower(primary_title) LIKE '%seventy%' OR lower(primary_title) LIKE '%eighty%' OR lower(primary_title) LIKE '%ninety%' OR lower(primary_title) LIKE '%hundred%' OR lower(primary_title) LIKE '%thousand%' OR lower(primary_title) LIKE '%million%' OR lower(primary_title) LIKE '%billion%' OR lower(primary_title) LIKE '%trillion%' OR lower(primary_title) LIKE '%zillion%') then 1 end) AS no0
                   FROM titles
                   WHERE title_type = 'movie'")

#Create model
benlaw <- function(d) log10(1 + 1 / d)
digits <- 1:10
digits[10] = 0

#Data for the chart
H <- c(var1$no1, var1$no2, var1$no3, var1$no4, var1$no5, var1$no6, var1$no7, var1$no8, var1$no9, var1$no0)

jpeg(file = "barchart3.jpg", width = 800, height = 600)

bar <- barplot(H, col=rainbow(10), main='Aantal films met getallen in de titel', names.arg = c("1", "2", "3", "4", "5", "6", "7", "8", "9", "0"), ylab = 'Aantal films waar getal in voorkomt', xlab="Het getal dat voorkomt", space=0)
lines(x = bar[,1], y = (sum(H) * benlaw(digits)), col="gray", lwd=2,type="b", pch=23, cex=1.5,bg="gray")
legend("topright", "Wet van Benford-curve", cex=1.5, bty="n", fill="gray")

dev.off()
