install.packages("tidyverse")
install.packages("sp")

library(tidyverse)

in_data <- read.csv("in_data.csv", sep = ",")


data <- in_data

write.csv(data, file = "out_data.csv", sep = ",")
