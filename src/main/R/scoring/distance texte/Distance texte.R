rm(list = ls())
options(stringsAsFactors = F)
library(stringdist)

setwd("C:/Users/chloe.bertin/Desktop/Hackathon-2018/données/Filtre/Distance texte/")

fichier <- read.csv2("in-data.csv",sep = ";",
                     colClasses = "character",as.is = T)


fichier$dist_text <- stringdist(fichier$RS_X,fichier$Denomination,method="lv")

