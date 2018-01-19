import pandas as pd
import requests
from urllib.parse import urlencode
import numpy as np

url = "https://prototype.api-sirene.insee.fr/ws/siret/"
inFile = "../../../../../process/requetage/api-sirene-phonetique/in-data.csv"
outFile = '../../../../../process/requetage/api-sirene-phonetique/out-data.csv'

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
                code=df['DEP_COM_CORR'][i]
                q_param_value = 'Denomination.phonetisation:'+raison_sociale
                param_value_2 = "Score,Denomination,Siren"
                param_value_3 = 20
                requete_params = {'q':q_param_value,'champs':param_value_2,'nombre':param_value_3}
                requete_params=urlencode(requete_params)
                requete = requests.get(url, params=requete_params)
                print(requete.url)
                if(requete.status_code!=404):
                    requete.raise_for_status()
                    data_raw = requete.json()
                    etabs = data_raw["Etablissements"]
                    for etab in etabs:
                        print(etab["UniteLegale"]["Denomination"])
                        siren = etab["Siren"]
                        print(siren)
                        q_param_value = 'Siren:'+str(siren)+' AND CodeCommuneEtablissement:'+str(code)
                        requete_params = {'q':q_param_value}
                        requete_params=urlencode(requete_params)
                        requete = requests.get(url, params=requete_params)
                        if(requete.status_code!=404):
                            requete.raise_for_status()
                            data_raw = requete.json()
                            etabs_2 = data_raw["Etablissements"]
                            k=0
                            for etab_2 in etabs_2:
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
