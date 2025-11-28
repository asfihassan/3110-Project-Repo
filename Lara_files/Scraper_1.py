import requests

def fetch(url):
    r = requests.get(url)
    return r.text

print(fetch("http://example.com"))
