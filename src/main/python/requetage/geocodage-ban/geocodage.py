from urllib.parse import urlencode
import requests
import pandas as pd
import numpy as np

VARS = ["ComplementAdresseEtablissement", "NumeroVoieEtablissement", "IndiceRepetitionEtablissement", 
"TypeVoieEtablissement", "LibelleVoieEtablissement", "CodePostalEtablissement", "LibelleCommuneEtablissement",
"LibelleCommuneEtrangerEtablissement", "DistributionSpecialeEtablissement", "CodeCommuneEtablissement"]

BAN_URL = "http://api-ban.hackathon.insee.eu/search?"

df = pd.read_csv(
    "../../../../../process/requetage/geocodage-ban/in-data.csv", 
    sep=";",
    index_col=False,
    dtype={
        "Siret": np.object_,
        "NumeroVoieEtablissement": np.object_,
        "IndiceRepetitionEtablissement": np.object_,
        "CodePostalEtablissement": np.object_,
        "LibelleVoieEtablissement": np.object_,
        "DistributionSpecialeEtablissement": np.object_,
        "CodeCommuneEtablissement": np.object_
    })

def to_ban_query(adresse):
    return urlencode({"q": adresse})

def ban_requete(q):
    resp = requests.get(BAN_URL + q)
    if resp.status_code == 200:
        data = resp.json()
        # on prend les coordonnees de la premiere feature, a priori celle qui a le plus haut score
        bon_resultat = data["features"][0]
        coords = bon_resultat["geometry"]["coordinates"]
        x = bon_resultat["properties"]["x"]
        y = bon_resultat["properties"]["y"]
        return (x, y)
    else:
        #print("req not ok")
        return ("NA", "NA")

def none_to_empty(cell):
    if cell == "None":
        return ""
    else:
        return cell

def make_adress(row): # FAIL
    jj = str.join(" ", str(row))
    print(jj)
    return jj


sub_df = df[VARS]
sub_df = sub_df.applymap(none_to_empty)
sub_df["AdressePourBan"] = sub_df["ComplementAdresseEtablissement"] + " " + \
 sub_df["NumeroVoieEtablissement"] + " " + \
 sub_df["IndiceRepetitionEtablissement"] + " " + \
 sub_df["TypeVoieEtablissement"] + " " + \
 sub_df["LibelleVoieEtablissement"]+ " " + \
 sub_df["CodePostalEtablissement"]+ " " + \
 sub_df["LibelleCommuneEtablissement"]+ " " + \
 sub_df["LibelleCommuneEtrangerEtablissement"]+ " " + \
 sub_df["DistributionSpecialeEtablissement"]+ " " + \
 sub_df["CodeCommuneEtablissement"]

# -- Better solution
#zs = []
#for i, row in sub_df.iterrows():
#    print(row)
#    v = row.squeeze().str.cat(sep=" ")
#    zs.append(v)

#sub_df["TEST"] = zs
#print(sub_df["TEST"])
# -- 

xs = []
ys = []

for row in sub_df["AdressePourBan"]:
    print(row)
    if pd.isnull(row):
        print("pas de requete")
        xs.append("NA")
        ys.append("NA")
    else:
        print("requete")
        x_y = ban_requete(to_ban_query(row))
        xs.append(x_y[0])
        ys.append(x_y[1])

df["SIRENE_X"] = xs
df["SIRENE_Y"] = ys

print(df.head)

df.to_csv("../../../../../process/requetage/geocodage-ban/out-data.csv", sep=";")
