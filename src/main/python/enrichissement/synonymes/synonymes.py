import pandas as pd


# -- Chargement du fichier
BASE_DIR = "../../../../../process/enrichissement/synonymes/"
IN_DATA = "in-data.csv"
OUT_DATA = "out-data.csv"

# Liste des variables du fichier source pour lesquelles on veut lancer
# une opération de recherche de synonymie
SYN_VARS = ["ACTET_X"]

df = pd.read_csv(BASE_DIR + IN_DATA, sep=";", index_col=False)

# -- Gestion des synonymes
# Pour chaque variable "source" on applique une fonction d'enrichissement
# des synonymes
# ex : ajout de libellés d'activité alternatifs

# FIXME dummy implementation =)
df["SynonymeActivite"] = df.apply(lambda row: "ASSURANCE" if row["ACTET_X"] == "ASSUREURS" else "NA", axis=1)

df.to_csv(BASE_DIR + OUT_DATA, sep=";")