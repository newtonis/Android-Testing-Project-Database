import webbrowser
from selenium import webdriver


#chrome_path = "C:/Program Files (x86)/Google/Chrome/Application/chrome.exe %s"

#browser = webbrowser.get(chrome_path)

browser = webdriver.Chrome()

browser.get("h")
linkElem = browser.find_element_by_link_text('id78')

print(linkElem)
