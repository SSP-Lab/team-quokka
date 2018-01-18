rm(list = ls())
options(stringsAsFactors = F)

setwd("C:/Users/chloe.bertin/Desktop/Hackathon-2018/données/Filtre/")

fichier <- read.csv2("in-data.csv",sep = ";",
                     colClasses = "character",as.is = T)

#param <- read.csv2("in-param.csv",sep = ";",
#                   colClasses = "character",as.is = T)
fichier$dist_geo <- as.numeric(fichier$dist_geo)
fichier$dist_text <- as.numeric(fichier$dist_text)
fichier$Score <- fichier$dist_geo*fichier$dist_text

fichier <- fichier %>%
  group_by(CABBI) %>%
  arrange(CABBI,desc(Score))

fichier <- fichier[!duplicated(fichier$CABBI),]


fichier$SIRET <- ifelse(fichier$Score>0.6,fichier$Siret,"")
fichier$CATJURIDIQUE <- ""
fichier$TRANCHEEFF <- ""

#fichier de sortie
out <- fichier[,c("CABBI","SIRET","CATJURIDIQUE","TRANCHEEFF")]

write.csv2(out,"out-data.csv",row.names = F)