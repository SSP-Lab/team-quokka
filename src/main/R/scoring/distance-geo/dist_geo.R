
rm(list = ls())
options(stringsAsFactors = F)

suppressPackageStartupMessages(library(dplyr))
suppressPackageStartupMessages(library(readr))

args <- commandArgs(trailingOnly = TRUE)
cat(args, sep = "\n")
base_dir <- args[1]

setwd(base_dir)

data <- read.csv2("in-data.csv",sep = ";",
                                colClasses = "character",as.is = T)



data$dist_geo <- 1/(1 + 0.0001*sqrt((as.numeric(data$x) - as.numeric(data$SIRENE_X))^2 + 
                               (as.numeric(data$y) - as.numeric(data$SIRENE_Y))^2))
  
data$dist_geo[is.na(data$dist_geo)] <- 0


write.csv2(data,
           file = file.path(data_path,"out-data.csv"),row.names = F)


