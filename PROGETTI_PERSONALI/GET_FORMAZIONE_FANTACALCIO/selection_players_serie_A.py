#!pip install --upgrade pandas
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
from joblib import dump, load 
from pathlib import Path
from sklearn.metrics import mean_squared_error
import requests
from bs4 import BeautifulSoup
import json
import smtplib
import tkinter as tk
from tkinter import messagebox
from tkinter import *
from tkinter import ttk
from tkinter.messagebox import showinfo
import requests
from bs4 import BeautifulSoup
import pandas as pd
import webbrowser

plt.style.use('fivethirtyeight')

import warnings
warnings.filterwarnings("ignore")


URL = "https://www.fantacalcio.it/probabili-formazioni-serie-a"
resp = requests.get(URL)
#print(resp.status_code)
# print(resp.content)

# creazione di un oggetto "soup"
data = BeautifulSoup(resp.content)

paragrafo = data.find('ul', attrs = {'class': 'match-list'})

def scramping(paragrafo,start_index,end_match):
  p1=paragrafo.split("\n")
  p_new=[]
  for x in p1 :
    if x.strip()!='' and x.strip()!="," :
      p_new.append(x.strip())

  start=p_new[p_new.index("-")+1:].index("-")+(p_new.index("-")+1)+1
  dati_p=p_new[0+start_index:start+start_index]
  giornata=dati_p[0].strip()
  info_=p_new[start+22+start_index:] # 22 giocatori in campo, da 9+29 in poi abbiamo informazioni sulla partita e sulle rose

  info_partita=info_[0]
  stadio=info_[1]
  s1=info_[2:info_.index(dati_p[2].strip())]
  nome_squadra=s1[0]
  modulo=s1[1]
  giocatori=[]
  percentuale_di_giocare=[]
  is_titolare=[]
  squadra={"Nome squadra":nome_squadra,"Giocatori":giocatori,"Percentuale di gioco del giocatore":percentuale_di_giocare,"Titolare":is_titolare,"Match":dati_p[1].strip()+" VS "+dati_p[2].strip(),"Stadio Partita":stadio,"Info Partita":info_partita,"Modulo Partita":modulo,"Giornata N":dati_p[0].strip()}

  gg=s1[2:]

  titolari=gg[:gg.index("Panchina")]
  panchina=gg[gg.index("Panchina")+1:]

  in_campo=True
  for g in range (0,len(titolari),2):
    
    squadra["Giocatori"].append(titolari[g])
    squadra["Percentuale di gioco del giocatore"].append(titolari[g+1].strip())
    squadra["Titolare"].append(in_campo)

  in_campo=False
  for g in range (0,len(panchina),2):
    if (panchina[g].split()[:2]!=['Ultimo','aggiornamento']):
      
      squadra["Giocatori"].append(panchina[g])
      squadra["Percentuale di gioco del giocatore"].append(panchina[g+1].strip())
      squadra["Titolare"].append(in_campo)

  df1=pd.DataFrame(squadra)


  s2=info_[info_.index(dati_p[2].strip()):info_.index("Presentazione squadre")]
  nome_squadra=s2[0]
  modulo=s2[1]
  giocatori=[]
  percentuale_di_giocare=[]
  is_titolare=[]
  squadra={"Nome squadra":nome_squadra,"Giocatori":giocatori,"Percentuale di gioco del giocatore":percentuale_di_giocare,"Titolare":is_titolare,"Match":dati_p[1].strip()+" VS "+dati_p[2].strip(),"Stadio Partita":stadio,"Info Partita":info_partita,"Modulo Partita":modulo,"Giornata N":dati_p[0].strip()}
  gg=s2[2:]
  # print(gg)
  titolari=gg[:gg.index("Panchina")]
  panchina=gg[gg.index("Panchina")+1:]

  # print(titolari,panchina)
  in_campo=True
  for g in range (0,len(titolari),2):
    
    squadra["Giocatori"].append(titolari[g])
    squadra["Percentuale di gioco del giocatore"].append(titolari[g+1].strip())
    squadra["Titolare"].append(in_campo)

  in_campo=False
  for g in range (0,len(panchina),2):
    if (panchina[g].split()[:2]!=['Ultimo','aggiornamento']):
        
      squadra["Giocatori"].append(panchina[g])
      squadra["Percentuale di gioco del giocatore"].append(panchina[g+1].strip())
      squadra["Titolare"].append(in_campo)

  df2=pd.DataFrame(squadra)
  
  # df=df1.append(df2)
  df = pd.concat([df1, df2])


  index_squalificati=info_.index("Squalificati")
  index_infortunati=info_.index("Infortunati")
  index_in_dubbio=info_.index("In dubbio")

  lista_infortunati=[]
  for x in info_[index_infortunati+1:index_in_dubbio]:
    if x!="Nessun calciatore":
      lista_infortunati.append(x)
  lista_squalificati=[]
  for x in info_[index_squalificati+1:index_infortunati]:
    if x!="Nessun calciatore":
      lista_squalificati.append(x)
  lista_indubbio=[]
  if end_match==True:
    limit_lista_indubbio=info_[index_in_dubbio+1:]
  else:
    limit_lista_indubbio=info_[index_in_dubbio+1:info_.index(giornata)]
  for x in limit_lista_indubbio:
    if x!="Nessun calciatore":
      lista_indubbio.append(x)
  # print(lista_indubbio,lista_infortunati,lista_squalificati)
  lista_infortunati_=[]
  lista_squalificati_=[]
  lista_indubbio_=[]


  for g in df["Giocatori"]:
    trovato_s=False
    for l in range (0,len(lista_squalificati),2):
      if g.strip()==lista_squalificati[l].strip():
        trovato_s=True
        lista_squalificati_.append(True)
        break
    if trovato_s==False:
      lista_squalificati_.append(False)
    else:
      trovato_s=False

  for g in df["Giocatori"]:
    trovato_i=False
    for l in range (0,len(lista_infortunati),2):
      if g.strip()==lista_infortunati[l].strip():
        trovato_i=True
        lista_infortunati_.append(str(lista_infortunati[l+1]).strip())
        break
    if trovato_i==False:
      lista_infortunati_.append(str("No"))
    else:
      trovato_i=False

  for g in df["Giocatori"]:
    trovato_d=False
    for l in range (0,len(lista_indubbio),2):
      if g.strip()==lista_indubbio[l].strip():
        trovato_d=True
        lista_indubbio_.append(str(lista_indubbio[l+1]).strip())
        break
    if trovato_d==False:
      lista_indubbio_.append(str("No"))
    else:
      trovato_d=False

  df["Squalificato"]=lista_squalificati_
  df["Infortunato"]=lista_infortunati_
  df["In dubbio"]=lista_indubbio_


  squalificati_infortunati_indubbio={"Nome squadra":'',"Giocatori":[],"Percentuale di gioco del giocatore":[],"Titolare":False,"Match":dati_p[1].strip()+" VS "+dati_p[2].strip(),"Stadio Partita":stadio,"Info Partita":info_partita,"Modulo Partita":modulo,"Giornata N":dati_p[0].strip(),
                "Squalificato":[], "Infortunato":[], "In dubbio":[]}

  for l in range (0,len(lista_squalificati),2):
    trovato_s=False
    for g in df["Giocatori"]:
      if g.strip()==lista_squalificati[l].strip():
        trovato_s=True
        break
    if trovato_s==False:
      squalificati_infortunati_indubbio['Giocatori'].append(lista_squalificati[l])
      squalificati_infortunati_indubbio['Percentuale di gioco del giocatore'].append(0)
      squalificati_infortunati_indubbio['Squalificato'].append(True)
      squalificati_infortunati_indubbio['Infortunato'].append("No")
      squalificati_infortunati_indubbio['In dubbio'].append("No")
  
  for l in range (0,len(lista_infortunati),2):
    trovato_i=False
    for g in df["Giocatori"]:
      if g.strip()==lista_infortunati[l].strip():
        trovato_i=True
        break
    if trovato_i==False:
      squalificati_infortunati_indubbio['Giocatori'].append(lista_infortunati[l])
      squalificati_infortunati_indubbio['Percentuale di gioco del giocatore'].append("0%")
      squalificati_infortunati_indubbio['Squalificato'].append("No")
      squalificati_infortunati_indubbio['Infortunato'].append(lista_infortunati[l+1])
      squalificati_infortunati_indubbio['In dubbio'].append("No")
    
  for l in range (0,len(lista_indubbio),2):
    trovato_d=False
    for g in df["Giocatori"]:
      if g.strip()==lista_indubbio[l].strip():
        trovato_d=True
        break
    if trovato_d==False:
      squalificati_infortunati_indubbio['Giocatori'].append(lista_indubbio[l])
      squalificati_infortunati_indubbio['Percentuale di gioco del giocatore'].append("0%")
      squalificati_infortunati_indubbio['Squalificato'].append("No")
      squalificati_infortunati_indubbio['Infortunato'].append("No")
      squalificati_infortunati_indubbio['In dubbio'].append(lista_indubbio[l+1])

  squalificati_infortunati_indubbio=pd.DataFrame(squalificati_infortunati_indubbio)
  # df=df.append(squalificati_infortunati_indubbio)
  df = pd.concat([df, squalificati_infortunati_indubbio])

  if end_match==True:
    indice=len(info_)+start+22+start_index
  else:
    indice=info_.index(giornata)+start+22+start_index
  
  return df,indice

def tkinput(text) -> str:
  
  # root window
  root = tk.Tk()
  root.geometry('400x310')
  root.eval('tk::PlaceWindow . center')
  # root.configure(bg='#007fff')
  root.resizable(False, False)
  root.title('Benvenuto, seleziona la rosa da calcolare')

  selected = tk.StringVar()
  team = (('Katzen Leverkusen', 'mattia gatto'),
            ('Vertigo FC', 'vincenzo gatto'),
            ('A.C. Ughina', 'mattia pellegrino'),
            ('Real Colisti', 'matteo turco'),
            ('FC Kendrick la Var', 'michele vitale'),
            ('Pio Lisco', 'pio lisco'))

  # label
  label = ttk.Label(text=text)
  label.pack(fill='x', padx=5, pady=5)

  # radio buttons
  for n in team:
      r = ttk.Radiobutton(
          root,
          text=n[0],
          value=n[1],
          variable=selected
      )
      r.pack(fill='x', padx=5, pady=5)
      if n[0]=='Katzen Leverkusen':
        r.pack(anchor='w')
        r.invoke()

  # Separator line
  separator = ttk.Separator(root, orient='horizontal')
  separator.pack(fill='x', padx=5, pady=10)  # Linea di separazione con poco spazio

  # Checkbox
  check_value = tk.BooleanVar()  # Variabile per la checkbox
  checkbox = ttk.Checkbutton(
      root,
      text="Includere statistiche extra?",
      variable=check_value
  )
  checkbox.pack(fill='x', padx=5, pady=5)

  # button
  button = ttk.Button(
      root,
      text="Invia",
      command=root.destroy
  )
  button.pack(fill='x', padx=5, pady=5)

  root.mainloop()

  # Restituisce il team selezionato e lo stato della checkbox
  return str(selected.get()).lower(), check_value.get()

def N_giocatore(giocatore):
  if giocatore=="mattia gatto":
     return "MATTIA_G" 
  elif giocatore=="matteo turco":
    return "MATTEO_T" 
  elif giocatore=="mattia pellegrino":
    return "MATTIA_P" 
  elif giocatore=="vincenzo gatto":
    return "VINCENZO_G"  
  elif giocatore=="michele vitale":
    return "MICHELE_V"  
  elif giocatore=="pio lisco":
    return "PIO_L" 

def rosa_df(df,Rosa_fantacalcio):
  res=df.loc[df['Giocatori'] == Rosa_fantacalcio[0]]
  for g in range (1,len(Rosa_fantacalcio)):
    # res=res.append(df.loc[df['Giocatori'] == Rosa_fantacalcio[g]])
    res = pd.concat([res, df.loc[df['Giocatori'] == Rosa_fantacalcio[g]]])
  res.reset_index(drop=True)
  res.index = np.arange(1, len(res) + 1)
  return res

def plt_bar_roles(ruolo,titl,col):
  g=rosa.loc[rosa['Ruolo Fanta']==ruolo ]
  g=g.loc[rosa['Titolare']==True ]
  
  val=g['Percentuale di gioco del giocatore'].values
  for v in range (len(val)):
    val[v]=int (val[v][:len(val[v])-1])

  ris={'Giocatori':g['Giocatori'],'Percentuale di gioco':val}

  plt.figure(figsize=(8, 4), dpi=80)
  plt.plot(g['Giocatori'],val,color = col)
  # plt.pie(val,labels=g['Giocatori'])
  plt.title("Probabilità di gioco "+titl)
  plt.show()

def aggiungi(ruolo, g, rosa, qnt):
    indexNames = []
    for n in g['Giocatori'].values:
        i = rosa[rosa['Giocatori'] == n].index
        if not i.empty:
            indexNames.append(i[0])
    rosa = rosa.copy().drop(index=indexNames)
    
    # Convertire le percentuali in numeri interi, eliminando il carattere '%'
    rosa['Percentuale di gioco del giocatore'] = rosa['Percentuale di gioco del giocatore'].str.rstrip('%').astype(int)
    
    # Creare il nuovo DataFrame con i giocatori filtrati
    ris = {
        'Giocatori': rosa['Giocatori'],
        'Ruolo': rosa['Ruolo Fanta'],
        'Percentuale di gioco': rosa['Percentuale di gioco del giocatore'],
        'FVM': rosa['FVM']
    }
    r = pd.DataFrame(ris)
    # Calcolare la colonna 'Sort' basata sulla media di 'FVM' e 'Percentuale di gioco'
    r['Sort'] = (r['FVM'] + r['Percentuale di gioco']) / 2
    r = r.sort_values(['Sort'], ascending=False)
    
    # Filtrare per ruolo e selezionare il numero di giocatori richiesto
    r = r.loc[r['Ruolo'] == ruolo]
    # print('-'*100)
    # print(r.head(qnt))
    # print('-'*100)
    g = pd.concat([g, r.head(qnt)])
    return g

def controlla(formazione,rosa):
  if len(formazione.loc[formazione['Ruolo'].str.strip() == 'A'])<(int(schieramento[2])+2):
    titolari_a=len(formazione.loc[formazione['Ruolo'].str.strip() == 'A'])
    necessari=int(schieramento[2])+2-titolari_a
    formazione=aggiungi('A',formazione,rosa,necessari)
  if len(formazione.loc[formazione['Ruolo'].str.strip() == 'C'])<(int(schieramento[1])+2):
    titolari_c=len(formazione.loc[formazione['Ruolo'].str.strip() == 'C'])
    necessari=int(schieramento[1])+2-titolari_c
    formazione=aggiungi('C',formazione,rosa,necessari)
  if len(formazione.loc[formazione['Ruolo'].str.strip() == 'D'])<(int(schieramento[0])+2):
    titolari_d=len(formazione.loc[formazione['Ruolo'].str.strip() == 'D'])
    necessari=int(schieramento[0])+2-titolari_d
    formazione=aggiungi('D',formazione,rosa,necessari)
  if len(formazione.loc[formazione['Ruolo'].str.strip() == 'P' ])<2:
    titolari_p=len(formazione.loc[formazione['Ruolo'].str.strip() == 'P'])
    necessari=2-titolari_p
    formazione=aggiungi('P',formazione,rosa,necessari)
  return formazione

 # Funzione per calcolare il punteggio basato sulla posizione

def calculate_score(position):
    try:
        pos = int(position)
        # Il punteggio diminuisce man mano che la posizione aumenta
        # Ad esempio, puoi assegnare 100 punti per la 1° posizione, 99 per la 2°, e così via
        return max(0, 100 - pos + 1)
    except ValueError:
        return 0

def add_score_team(list_squadra, list_squadraAvv):
    url = "https://sport.sky.it/calcio/serie-a/classifica"

    response = requests.get(url)
    response.raise_for_status()  # Controlla se la richiesta ha avuto successo
    soup = BeautifulSoup(response.text, 'html.parser')

    # Trova la tabella contenente i dati
    table = soup.find('tbody', class_='ftbl__competition-ranking__body')
    rows = table.find_all('tr')
    data = {}
    for row in rows:
        cells = row.find_all('td')    
        if len(cells) < 4:  # Controlla che ci siano abbastanza celle nella riga
            continue
        
        position = cells[0].get_text(strip=True)
        team = cells[2].find('a').get_text(strip=True)
        points = cells[3].get_text(strip=True)
        data[team]= {
            'Position': position,
            'Points': points,
            'Score':calculate_score(position)
        }
    numero_squadre=len(data)
    # print(numero_squadre)
    lista_res=[]
    for s in range (len(list_squadra)):
        # Crea un DataFrame Pandas dai dati estratti
        p_squadra=0
        p_squadra_avv=0
        for team in data.keys():
            if team ==list_squadra[s]:
                p_squadra=data[team]['Score']
                # print(p_squadra,data[team]['Position'])
            elif team ==list_squadraAvv[s]:
                p_squadra_avv=data[team]['Score']
                # print(p_squadra_avv,data[team]['Position'])
        lista_res.append(((numero_squadre-numero_squadre)+
                         (p_squadra-p_squadra_avv))/2)
    return lista_res

def titolari_panchina(formazione,Ruolo,n_tit,n_panc):
  titolari_top=formazione.loc[formazione['Ruolo']==Ruolo ]
  # Check if there are enough players for the requested number of starters and bench
  if len(titolari_top) >= n_tit + n_panc:
    titolari = titolari_top.head(n_tit)
    # print("titolari",titolari)
    titolari_top = titolari_top.drop(index=titolari.index)
    panchina = titolari_top.head(n_panc)
    # print("panchinari",panchina)
  else:
    # Handle cases with insufficient players (e.g., return empty DataFrames)
    titolari = pd.DataFrame()
    panchina = pd.DataFrame()
  return titolari,panchina

p = paragrafo.text
match_1,end=scramping(p,0, False)
match_2,end2=scramping(p,end, False)
match_3,end3=scramping(p,end2, False)
match_4,end4=scramping(p,end3, False)
match_5,end5=scramping(p,end4, False)
match_6,end6=scramping(p,end5, False)
match_7,end7=scramping(p,end6, False)
match_8,end8=scramping(p,end7, False)
match_9,end9=scramping(p,end8, False)
match_10,end10=scramping(p,end9, True)
# df=match_1.append(match_2.append(match_3.append(match_4.append(match_5.append(match_6.append(match_7.append(match_8.append(match_9.append(match_10))))))))).rename_axis('Giocatore in rosa').reset_index()
df = pd.concat([match_1, match_2, match_3, match_4, match_5, match_6, match_7, match_8, match_9, match_10]).rename_axis('Giocatore in rosa').reset_index()

squadre_json=json.load(open("Squadre.json"))

giocatore,plot_statisticks=tkinput("Inserisci Il nome Del team che vuole schierare la sua formazione!")
giocatore=str(giocatore).lower()
# print('-'*100,plot_statisticks)
giocatore= N_giocatore(giocatore)
Rosa_fantacalcio=squadre_json[giocatore]

rosa=rosa_df(df,Rosa_fantacalcio)

URL = "https://www.fantacalcio.it/quotazioni-fantacalcio"
resp1 = requests.get(URL)

# creazione di un oggetto "soup"
data2 = BeautifulSoup(resp1.content)
paragrafo2 = data2.find('header', attrs = {'class': 'mb-3 d-flex align-items-center'})

link=str(paragrafo2).split("href=")[1]
link="https://www.fantacalcio.it"+link.split("\" ")[0][1:]
annata="2024_25"
dati = pd.read_excel('Quotazioni/Quotazioni_Fantacalcio_Stagione_'+annata+'_ds.xlsx',header=1)

ind=[]
ruolo=[]
ruolo_fanta=[]
squadra=[]
fvm=[]
for r in rosa['Giocatori']:
  for g in dati['Nome']:
    if r==g:
      player=dati.loc[dati['Nome']==g]      
      ruolo.append(player['RM'].values[0])#.values[0])
      ruolo_fanta.append(player['R'].values[0])
      squadra.append(player['Squadra'].values[0])
      fvm.append(player['FVM'].values[0])
  
rosa["Ruolo"]=ruolo
rosa["Ruolo Fanta"]=ruolo_fanta
rosa["Nome squadra"]=squadra
rosa["FVM"]=fvm

# plot statistiche
if plot_statisticks:
  plt_bar_roles('P','Portieri','y')
  plt_bar_roles('D','Difensori','g')
  plt_bar_roles('C','Centrocampisti','b')
  plt_bar_roles('A','Attaccanti','r')

Moduli=list([[3,5,2],[3,4,3],[4,5,1],[4,4,2],[4,3,3],[5,3,2],[5,4,1]])
ROSE_FINALI=[]
for modulo in Moduli:

  schieramento=modulo
  # print(schieramento)
  g=rosa.loc[rosa['Titolare']==True ]
  val=g['Percentuale di gioco del giocatore'].values
  fvm_top=g['FVM'].values

  for v in range (len(val)):
    val[v]=int (val[v][:len(val[v])-1])

  ris={'Giocatori':g['Giocatori'],'Ruolo':g['Ruolo Fanta'],'Squadra':g['Nome squadra'],'Squadra Avv.':list(x.split(' VS ')[1] for x in g['Match']),
        'Percentuale di gioco':val,"FVM":fvm_top}
  formazione=pd.DataFrame(ris)
  formazione=controlla(formazione,rosa).reset_index(drop=True)

  formazione['Sort']=(formazione['FVM']+formazione['Percentuale di gioco']+add_score_team(formazione['Squadra'], formazione['Squadra Avv.']))/2

  formazione.loc[formazione['Percentuale di gioco']<=65,'Sort']=formazione['Percentuale di gioco']/2+(formazione['Percentuale di gioco']/100)
  formazione.loc[formazione['Percentuale di gioco']<=55,'Sort']=formazione['Percentuale di gioco']/2+(formazione['Percentuale di gioco']/100)
  formazione.loc[formazione['Percentuale di gioco']<=45,'Sort']=formazione['Percentuale di gioco']/2+(formazione['Percentuale di gioco']/100)
  formazione.loc[formazione['Percentuale di gioco']<=35,'Sort']=formazione['Percentuale di gioco']/2+(formazione['Percentuale di gioco']/100)
  formazione.loc[formazione['Percentuale di gioco']<=25,'Sort']=formazione['Percentuale di gioco']/2+(formazione['Percentuale di gioco']/100)
  formazione.loc[formazione['Percentuale di gioco']<=15,'Sort']=formazione['Percentuale di gioco']/2+(formazione['Percentuale di gioco']/100)
  formazione.loc[formazione['Percentuale di gioco']<= 5,'Sort']=formazione['Percentuale di gioco']/2+(formazione['Percentuale di gioco']/100)
  formazione.loc[formazione['Percentuale di gioco']== 0,'Sort']=formazione['Percentuale di gioco']
  formazione['Sort']=formazione['Sort'].astype(float)
  # print(formazione)
  # formazione = formazione.sort_values(['FVM','Percentuale di gioco'], ascending=False)
  formazione = formazione.sort_values(['Sort'], ascending=False)
  formazione

  attaccanti_titolari,attaccanti_panchinari= titolari_panchina(formazione,'A',(modulo[2]),2)
  centrocampisti_titolari,centrocampisti_panchinari= titolari_panchina(formazione,'C',modulo[1],2)
  difensori_titolari,difensori_panchinari= titolari_panchina(formazione,'D',modulo[0],2)
  portieri_titolari,portieri_panchinari= titolari_panchina(formazione,'P',1,1)

  # formazione_titolare=portieri_titolari.append(difensori_titolari.append(centrocampisti_titolari.append(attaccanti_titolari))).reset_index(drop=True)
  formazione_titolare = pd.concat([portieri_titolari,difensori_titolari,centrocampisti_titolari,attaccanti_titolari]).reset_index(drop=True)
  formazione_titolare.index = np.arange(1, len(formazione_titolare) + 1)
  formazione_titolare['Giocatore in']='campo'
  formazione_titolare

  # formazione_panchina=portieri_panchinari.append(difensori_panchinari.append(centrocampisti_panchinari.append(attaccanti_panchinari))).reset_index(drop=True)
  formazione_panchina = pd.concat([portieri_panchinari,difensori_panchinari,centrocampisti_panchinari,attaccanti_panchinari]).reset_index(drop=True)
  formazione_panchina.index = np.arange(1, len(formazione_panchina) + 1)
  formazione_panchina['Giocatore in']='panchina'
  formazione_panchina
  
  # formazione_finale=formazione_titolare.append(formazione_panchina)
  formazione_finale=pd.concat([formazione_titolare,formazione_panchina])
  formazione_finale.index = np.arange(1, len(formazione_finale) + 1)
  # print(len(formazione_finale.loc[formazione_finale['Ruolo']=='A' ])==(int(schieramento[2])+2))
  # print(len(formazione_finale.loc[formazione_finale['Ruolo']=='C' ])==(int(schieramento[1])+2))
  # print(len(formazione_finale.loc[formazione_finale['Ruolo']=='D' ])==(int(schieramento[0])+2))
  formazione_finale.fillna('-', inplace=True)
  ROSE_FINALI.append(formazione_finale)

s=[]
for i in ROSE_FINALI:
  s.append(sum(i[i['Giocatore in']=='campo']['Sort']))
mass=max(s)
index = s.index(mass)
Modulo_scelto=str(Moduli[index][0])+'-'+str(Moduli[index][1])+'-'+str(Moduli[index][2])
formazione_finale=ROSE_FINALI[index]


webbrowser.open('https://leghe.fantacalcio.it/iacucci-al-governo/area-gioco/inserisci-formazione')

formazione_finale.to_excel("formazione_finale_da schierare.xlsx")

fig, ax = plt.subplots()
fig.set_size_inches(10, 10)
# hide axes
fig.suptitle("Giornata N°"+df['Giornata N'][0]+", Modulo scelto: "+Modulo_scelto)
fig.patch.set_visible(False)
ax.axis('off')
ax.axis('tight')
ax.table(cellText=formazione_finale.values, colLabels=formazione_finale.columns, loc='center')
fig.tight_layout()
plt.savefig('formazione_finale_da schierare.png', dpi=300)
plt.show()
