rm(list = ls())
options(stringsAsFactors = F)
#install.packages("dplyr")
suppressPackageStartupMessages(library(dplyr))

args <- commandArgs(trailingOnly = TRUE)
cat(args, sep = "\n")
base_dir <- args[1]

setwd(base_dir)

fichier <- read.csv2("in-data.csv",sep = ";",
                     colClasses = "character",as.is = T)
param <- read.csv2("in-param.csv",sep = ";",
                   colClasses = "character",as.is = T)

fichier <- fichier %>%
  select(param$Variables)

write.csv2(fichier,"out-data.csv",row.names = F)

