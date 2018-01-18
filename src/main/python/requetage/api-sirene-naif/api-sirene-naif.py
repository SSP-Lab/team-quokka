import pandas as pd
import requests

url = "https://prototype.api-sirene.insee.fr/ws/siret/"

inFile = '../../../../process/requetage/api-sirene-naif/in-data.csv'
outFile = '../../../../process/requetage/api-sirene-naif/out-data.csv'

with open(inFile, 'r') as fr:
    with open(outFile, 'w') as fw:
        i=0
        for ligne in fr:
            if(i==0):
                fw.write('Siret;Denomination;CodeCommuneEtablissement;ComplementAdresseEtablissement;NumeroVoieEtablissement;IndiceRepetitionEtablissement;TypeVoieEtablissement;LibelleVoieEtablissement;CodePostalEtablissement;LibelleCommuneEtablissement;LibelleCommuneEtrangerEtablissement;DistributionSpecialeEtablissement;CedexEtablissement;' + ligne)
                i=i+1
            else:
                i=i+1
                liste = ligne.rstrip().split(';')
                q_param_value = 'Denomination:"'+liste[0]+'"'
                requete_params = {'q':q_param_value}
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
      


