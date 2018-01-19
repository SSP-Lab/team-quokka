
rm(list = ls())
options(stringsAsFactors = F)

suppressPackageStartupMessages(library(dplyr))
suppressPackageStartupMessages(library(readr))

args <- commandArgs(trailingOnly = TRUE)
cat(args, sep = "\n")
base_dir <- args[1]

setwd(base_dir)


in_data <- read_delim("in-data.csv", ";", escape_double = FALSE, trim_ws = TRUE)



data$dist_geo <- 1/(1 + sqrt((data$x - data$siren_x)^2 + (data$y - data$siren_y)^2))
  



write.csv2(data,
           file = file.path(data_path,"out-data.csv"),row.names = F)


