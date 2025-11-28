import requests

def fetch(url):
    r = requests.get(url)
    if r.status_code == 200:
        return r.text
    return "Error"

print(fetch("http://example.com"))
