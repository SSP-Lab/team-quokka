library(dplyr)
library(readr)



rm(list = ls())
options(stringsAsFactors = F)

data_path <- "process/scoring/dist_geo"


# 
# data <- read.csv(file.path(data_path,"in-data.csv"), 
#                   colClasses = "character")


in_data <- read_delim("process/scoring/dist_geo/in_data.csv", ";", escape_double = FALSE, trim_ws = TRUE)



data$dist_geo <- 1/(1 + sqrt((data$x - data$siren_x)^2 + (data$y - data$siren_y)^2))
  



write.csv2(data,
           file = file.path(data_path,"out-data.csv"),row.names = F)


