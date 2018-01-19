rm(list = ls())
options(stringsAsFactors = F)

suppressPackageStartupMessages(library(tidyr))
suppressPackageStartupMessages(library(dplyr))
args <- commandArgs(trailingOnly = TRUE)
cat(args, sep = "\n")
base_dir <- args[1]
setwd(base_dir)


fichier <- read.csv2("in-data.csv",sep = ";",as.is = T)

#param <- read.csv2("in-param.csv",sep = ";",
#                   colClasses = "character",as.is = T)
#fichier$dist_geo <- as.numeric(fichier$dist_geo)
#fichier$dist_text <- as.numeric(fichier$dist_text)
fichier$Score <- fichier$dist_geo*fichier$dist_dl_norm

fichier <- fichier %>%
  group_by(CABBI) %>%
  arrange(CABBI,desc(Score))

fichier <- fichier[!duplicated(fichier$CABBI),]


fichier$SIRET <- ifelse(fichier$Score>0.1,fichier$Siret,"")
fichier$CATJURIDIQUE <- ""
fichier$TRANCHEEFF <- ""
fichier$NAF <- ""

#fichier de sortie
out <- fichier[,c("CABBI","SIRET","NAF","CATJURIDIQUE","TRANCHEEFF")]

write.csv2(out,"out-data.csv",row.names = F, quote=F)