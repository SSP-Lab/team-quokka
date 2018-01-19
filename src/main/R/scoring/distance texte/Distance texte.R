rm(list = ls())
options(stringsAsFactors = F)

suppressPackageStartupMessages(library(stringdist))

args <- commandArgs(trailingOnly = TRUE)
cat(args, sep = "\n")
base_dir <- args[1]

setwd(base_dir)


fichier <- read.csv2("in-data.csv",sep = ";",
                     colClasses = "character",as.is = T)


test <- fichier[,c("RS_X", "Denomination")]

# test$dist_lv <- stringdist(fichier$RS_X,fichier$Denomination,method="lv")
# test$dist_osa <- stringdist(fichier$RS_X,fichier$Denomination,method="osa")
# test$dist_dl <- stringdist(fichier$RS_X,fichier$Denomination,method="dl")
# test$dist_hamming <- stringdist(fichier$RS_X,fichier$Denomination,method="hamming")
# test$dist_lcs <- stringdist(fichier$RS_X,fichier$Denomination,method="lcs")
# test$dist_qgram <- stringdist(fichier$RS_X,fichier$Denomination,method="qgram")
# test$dist_cosine <- stringdist(fichier$RS_X,fichier$Denomination,method="cosine")
# test$dist_jaccard <- stringdist(fichier$RS_X,fichier$Denomination,method="jaccard")
# test$dist_jw <- stringdist(fichier$RS_X,fichier$Denomination,method="jw")
# test$dist_soundex <- stringdist(fichier$RS_X,fichier$Denomination,method="soundex")


long_str <- function(v, w) {
  long <- apply(data.frame(v,w), c(1,2), FUN = length)
  return(long)
}

long_str(fichier$RS_X,fichier$Denomination)

length(fichier$RS_X[1])

test$dist_dl_norm <- 1 - stringdist(fichier$RS_X,fichier$Denomination,method="dl") / 
  apply(data.frame(nchar(fichier$RS_X),nchar(fichier$Denomination)), 1, FUN = max)

