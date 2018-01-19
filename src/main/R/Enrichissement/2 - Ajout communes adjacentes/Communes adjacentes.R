rm(list = ls())
options(stringsAsFactors = F)

suppressPackageStartupMessages(library(tidyr))

args <- commandArgs(trailingOnly = TRUE)
cat(args, sep = "\n")
base_dir <- args[1]

setwd(base_dir)

fichier <- read.csv2("in-data.csv",sep = ";",
                     colClasses = "character",as.is = T)

adj <- read.csv("supp-data.csv",sep = ",",
                     colClasses = "character",as.is = T)

for (i in 1:29) {
  adj <- cbind(adj,substr(adj$insee_voisins,6*i-5,6*i-1))
  names(adj)[6+i] <- paste0("com_adj",i)
  }

adj <- adj %>%
  select(-nom,-nb_voisins,-insee_voisins,-noms_voisins,-cap_voisins)


adj2 <- gather(data=adj,key=COM1,value=COM_ADJ,-insee)
adj2 <- adj2[,c("insee","COM_ADJ")]
adj2 <- adj2[adj2$COM_ADJ!="",]

fichier1 <- fichier
fichier1$INDIC_ORIGINE <- "1"


fichier2 <- merge(fichier,adj2,by.x="DEP_COM_CORR",by.y="insee",all.x=T,all.y=F)
fichier2$INDIC_ORIGINE <- "0"
fichier2 <- fichier2 %>% 
  select(-DEP_COM_CORR)
fichier2$DEP_COM_CORR <- fichier2$COM_ADJ
fichier2 <- fichier2 %>% 
  select(-COM_ADJ)


fichier_final <- fichier1 %>%
  bind_rows(fichier2)
fichier_final <- fichier_final %>%
  arrange(CABBI)

write.csv2(fichier_final,"out-data.csv",row.names = F)