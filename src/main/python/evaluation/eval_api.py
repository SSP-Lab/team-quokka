import requests

BASE_URL = "http://api.hackathon.insee.eu/api/"
UPLOAD_URL = "upload"

TARGET_URL = BASE_URL + UPLOAD_URL

HEADERS = {"token": "quokka"}

files = {'file': ("out-data.csv", open('out-data.csv', 'rb'), "text/csv")}

# -- Upload

print("Upload URL: ", TARGET_URL)

resp = requests.post(TARGET_URL, headers=HEADERS, files=files)

print(resp.status_code)
print(resp.request.headers)
print(resp.request.url)