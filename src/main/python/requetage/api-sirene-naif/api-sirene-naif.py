from urllib.parse import urlencode
import pandas as pd
import requests
import numpy as np

url = "https://prototype.api-sirene.insee.fr/ws/siret/"
inFile = "../../../../../process/requetage/api-sirene-naif/in-data.csv"
outFile = '../../../../../process/requetage/api-sirene-naif/out-data.csv'

df = pd.read_csv(inFile,sep=';', dtype={
        "DEP_COM_CORR": np.object_})

with open(inFile, 'r') as fr:
    with open(outFile, 'w') as fw:
        i=-1    
        for ligne in fr:
            if(i==-1):
                fw.write('Siret;Denomination;CodeCommuneEtablissement;ComplementAdresseEtablissement;NumeroVoieEtablissement;IndiceRepetitionEtablissement;TypeVoieEtablissement;LibelleVoieEtablissement;CodePostalEtablissement;LibelleCommuneEtablissement;LibelleCommuneEtrangerEtablissement;DistributionSpecialeEtablissement;CedexEtablissement;' + ligne)
            if(i>-1):
                print(i)
                liste = ligne.rstrip().split(';')
                # Variable RS_X
                raison_sociale = df['RS_X'][i].replace(" ","+").replace("(","").replace(")","")
                # Variable DEP_COM_CORR
                code=str(df['DEP_COM_CORR'][i])
                q_param_value = 'Denomination:'+raison_sociale+' AND CodeCommuneEtablissement:'+code
                requete_params = {'q':q_param_value}
                requete_params=urlencode(requete_params)
                requete = requests.get(url, params=requete_params)
                print(requete.url)
                if(requete.status_code!=404):
                    requete.raise_for_status()
                    data_raw = requete.json()
                    etabs = data_raw["Etablissements"]
                    k=0
                    for etab in etabs:
                        if(k<5):
                            Siret=etab["Siren"]+etab["Nic"]
                            Denomination=etab["UniteLegale"]["Denomination"]
                            CodeCommuneEtablissement=str(etab["Adresse"]["CodeCommuneEtablissement"])
                            ComplementAdresseEtablissement=str(etab["Adresse"]["ComplementAdresseEtablissement"])
                            NumeroVoieEtablissement=str(etab["Adresse"]["NumeroVoieEtablissement"])
                            IndiceRepetitionEtablissement=str(etab["Adresse"]["IndiceRepetitionEtablissement"])
                            TypeVoieEtablissement=str(etab["Adresse"]["TypeVoieEtablissement"])
                            LibelleVoieEtablissement=str(etab["Adresse"]["LibelleVoieEtablissement"])
                            CodePostalEtablissement=str(etab["Adresse"]["CodePostalEtablissement"])
                            LibelleCommuneEtablissement=str(etab["Adresse"]["LibelleCommuneEtablissement"])
                            LibelleCommuneEtrangerEtablissement=str(etab["Adresse"]["LibelleCommuneEtrangerEtablissement"])
                            DistributionSpecialeEtablissement=str(etab["Adresse"]["DistributionSpecialeEtablissement"])
                            CedexEtablissement=str(etab["Adresse"]["CedexEtablissement"])
                            fw.write(Siret + ';' + Denomination + ';' + CodeCommuneEtablissement +';' + ComplementAdresseEtablissement + ';' +NumeroVoieEtablissement + ';' +IndiceRepetitionEtablissement + ';' +TypeVoieEtablissement + ';' +LibelleVoieEtablissement + ';' +CodePostalEtablissement + ';' +LibelleCommuneEtablissement + ';' +LibelleCommuneEtrangerEtablissement + ';' +DistributionSpecialeEtablissement + ';' +CedexEtablissement + ';' +ligne) 
                            k=k+1
                else:
                    Siret=""
                    Denomination=""
                    CodeCommuneEtablissement=""
                    ComplementAdresseEtablissement=""
                    NumeroVoieEtablissement=""
                    IndiceRepetitionEtablissement=""
                    TypeVoieEtablissement=""
                    LibelleVoieEtablissement=""
                    CodePostalEtablissement=""
                    LibelleCommuneEtablissement=""
                    LibelleCommuneEtrangerEtablissement=""
                    DistributionSpecialeEtablissement=""
                    CedexEtablissement=""
                    fw.write(Siret + ';' + Denomination + ';' + CodeCommuneEtablissement +';' + ComplementAdresseEtablissement + ';' +NumeroVoieEtablissement + ';' +IndiceRepetitionEtablissement + ';' +TypeVoieEtablissement + ';' +LibelleVoieEtablissement + ';' +CodePostalEtablissement + ';' +LibelleCommuneEtablissement + ';' +LibelleCommuneEtrangerEtablissement + ';' +DistributionSpecialeEtablissement + ';' +CedexEtablissement + ';' +ligne) 
            i=i+1

