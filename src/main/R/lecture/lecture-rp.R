rm(list = ls())
options(stringsAsFactors = F)
install.packages("dplyr")
install.packages("argparse")
library(dplyr)
library(argparse)

parser <- ArgumentParser()

setwd("C:/Users/chloe.bertin/Desktop/Hackathon-2018/données/Lecture/")

fichier <- read.csv2("in-data.csv",sep = ";",
                     colClasses = "character",as.is = T)
param <- read.csv2("in-param.csv",sep = ";",
                   colClasses = "character",as.is = T)

fichier <- fichier %>%
  select(param$Variables)

write.csv2(fichier,"out-data.csv",row.names = F)

