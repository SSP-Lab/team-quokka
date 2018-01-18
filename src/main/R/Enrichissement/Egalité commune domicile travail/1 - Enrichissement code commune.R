rm(list = ls())
options(stringsAsFactors = F)
install.packages(dplyr)
library(dplyr)

setwd("C:/Users/chloe.bertin/Desktop/Hackathon-2018/données/Enrichissement/")

fichier <- read.csv2("in-data.csv",sep = ";",
                     colClasses = "character",as.is = T)

#param <- read.csv2("in-param.csv",sep = ";",
#                   colClasses = "character",as.is = T)

fichier$DEP_COM_CORR <- ifelse(fichier$ILT_X=="1",fichier$DEP_COM_CODE,fichier$CLT_C_C)

write.csv(fichier,"out-data.csv",row.names = F)


