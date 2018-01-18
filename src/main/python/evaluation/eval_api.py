import requests
from inspect import getsourcefile
from os.path import abspath

print(abspath(getsourcefile(lambda:0)))

# -- Constantes
BASE_URL = "http://api.hackathon.insee.eu/api/"
UPLOAD_URL = "upload"
HEADERS = {"token": "quokka"}

# -- Lecture du fichier résultats
chemin_fichier = "../../../../process/evaluation/soumission-api/in-data.csv"
fichier_resultat = open(chemin_fichier, 'rb')

files = {'file': ("in-data.csv", fichier_resultat, "text/csv")}

# -- Upload
TARGET_URL = BASE_URL + UPLOAD_URL
print("Versement du fichier à l'URL: ", TARGET_URL)
resp = requests.post(TARGET_URL, headers=HEADERS, files=files)
print("Status code : ", resp.status_code)

fichier_resultat.close()