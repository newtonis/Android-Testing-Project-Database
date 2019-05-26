import requests
import json
from bs4 import BeautifulSoup

base = "http://visualizador_encuestas.exactas.uba.ar/"
schools = []

class Subject:
    def __init__(self):
        self.name = ""
        self.comments = set()
    def __eq__(self, other):
        return self.name == other.name
    def __repr__(self):
        return str({
            "name": self.name,
            "comments": list(self.comments)
        })

class School:
    def __init__(self):
        self.name = ""
        self.subjects = []
    def __eq__(self, other):
        return self.name == other.name
    def __repr__(self):
        return str({
            "name": self.name,
            "subjects": self.subjects
        })

def getPage(url):
    return BeautifulSoup(requests.get(url).content, 'html.parser')

def processPeriods(soup):
    for td in soup.select("td"):
        href = td.a.attrs["href"]
        period = href[:href.find("/")]
        periodSoup = getPage(base + href)
        processDepartments(periodSoup, period)

def processDepartments(soup, period):
    basePeriod = base + period + "/"
    for td in soup.select("td"):
        if td.a:
            href = td.a.attrs["href"]
            department = href[:href.find("/")]
            departmentSoup = getPage(basePeriod + href)
            school = School()
            school.name = td.a.string
            found = False
            for sch in schools:
                if sch == school:
                    school = sch
                    found = True
            if not found:
                schools.append(school)
            processSubject(departmentSoup, period, department, school)

def processSubject(soup, period, department, school):
    baseDepartment = base + period + "/" + department + "/"
    for td in soup.select("td"):
        subject = Subject()
        subject.title = td.a.string
        found = False
        for subj in school.subjects:
            if subj == subject:
                subject = subj
                found = True
        if not found:
            school.subjects.append(subject)
        href = td.a.attrs["href"]
        commision = href[:href.find("/")]
        commisionSoup = getPage(baseDepartment + href)
        processCommision(commisionSoup, period, department, commision, subject)


def processCommision(soup, period, department, commision, subject):
    baseSubject = base + period + "/" + department + "/" + commision + "/"
    for td in soup.select("td"):
        href = td.a.attrs["href"]
        info = getPage(baseSubject + href[:href.rfind("/")+1] + "comentarios_course.html")
        processComments(info, subject)


def processComments(soup, subject):
    comments = soup.find("div", attrs={"class": "comments"})
    for comment in comments.find_all("p"):
        if comment.string:
            subject.comments.add(comment.string)

periodos = getPage(base)
processPeriods(periodos)
jsonStr = str(schools).replace("\'","\"")
decoded = json.loads(jsonStr)
print(decoded)