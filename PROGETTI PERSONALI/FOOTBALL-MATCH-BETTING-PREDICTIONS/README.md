# Football Match Betting Predictions

## Informazioni sulle chiavi del Dataset

### Chiavi per i dati dei risultati:

- **Div**: Divisione (League)
- **Date**: Data della partita (gg/mm/aa)
- **Time**: Orario del calcio d'inizio
- **HomeTeam**: Squadra di casa
- **AwayTeam**: Squadra ospite
- **FTHG** e **HG**: Gol segnati dalla squadra di casa a fine partita
- **FTAG** e **AG**: Gol segnati dalla squadra ospite a fine partita
- **FTR** e **Res**: Risultato a fine partita (H = vittoria in casa, D = pareggio, A = vittoria in trasferta)
- **HTHG**: Gol segnati dalla squadra di casa a metà tempo
- **HTAG**: Gol segnati dalla squadra ospite a metà tempo
- **HTR**: Risultato a metà partita (H = vittoria in casa, D = pareggio, A = vittoria in trasferta)

### Statistiche delle partite (ove disponibili):

- **Attendance**: Numero di spettatori
- **Referee**: Arbitro della partita
- **HS**: Tiri della squadra di casa
- **AS**: Tiri della squadra ospite
- **HST**: Tiri in porta della squadra di casa
- **AST**: Tiri in porta della squadra ospite
- **HHW**: Legni colpiti dalla squadra di casa
- **AHW**: Legni colpiti dalla squadra ospite
- **HC**: Calci d'angolo della squadra di casa
- **AC**: Calci d'angolo della squadra ospite
- **HF**: Falli commessi dalla squadra di casa
- **AF**: Falli commessi dalla squadra ospite
- **HFKC**: Calci di punizione concessi alla squadra di casa
- **AFKC**: Calci di punizione concessi alla squadra ospite
- **HO**: Fuorigioco della squadra di casa
- **AO**: Fuorigioco della squadra ospite
- **HY**: Cartellini gialli della squadra di casa
- **AY**: Cartellini gialli della squadra ospite
- **HR**: Cartellini rossi della squadra di casa
- **AR**: Cartellini rossi della squadra ospite
- **HBP**: Punti disciplina della squadra di casa (10 = giallo, 25 = rosso)
- **ABP**: Punti disciplina della squadra ospite (10 = giallo, 25 = rosso)

### Note:
- I calci di punizione concessi includono falli, fuorigioco e altre infrazioni e sono sempre uguali o superiori al numero di falli.
- I cartellini gialli nelle partite inglesi e scozzesi non includono il primo cartellino giallo quando un secondo viene convertito in rosso, ma nelle partite europee viene considerato come giallo (più rosso).

---

## Chiavi per i dati sulle quote di scommessa (1X2 - Partita):

- **B365H**: Quote Bet365 per vittoria in casa
- **B365D**: Quote Bet365 per pareggio
- **B365A**: Quote Bet365 per vittoria in trasferta
- **BSH**, **BSD**, **BSA**: Quote Blue Square per vittoria in casa, pareggio, vittoria in trasferta
- **BWH**, **BWD**, **BWA**: Quote Bet&Win per vittoria in casa, pareggio, vittoria in trasferta
- **GBH**, **GBD**, **GBA**: Quote Gamebookers per vittoria in casa, pareggio, vittoria in trasferta
- **IWH**, **IWD**, **IWA**: Quote Interwetten per vittoria in casa, pareggio, vittoria in trasferta
- **LBH**, **LBD**, **LBA**: Quote Ladbrokes per vittoria in casa, pareggio, vittoria in trasferta
- **PSH**, **PSD**, **PSA**: Quote Pinnacle per vittoria in casa, pareggio, vittoria in trasferta
- **SOH**, **SOD**, **SOA**: Quote Sporting Odds per vittoria in casa, pareggio, vittoria in trasferta
- **SBH**, **SBD**, **SBA**: Quote Sportingbet per vittoria in casa, pareggio, vittoria in trasferta
- **SJH**, **SJD**, **SJA**: Quote Stan James per vittoria in casa, pareggio, vittoria in trasferta
- **SYH**, **SYD**, **SYA**: Quote Stanleybet per vittoria in casa, pareggio, vittoria in trasferta
- **VCH**, **VCD**, **VCA**: Quote VC Bet per vittoria in casa, pareggio, vittoria in trasferta
- **WHH**, **WHD**, **WHA**: Quote William Hill per vittoria in casa, pareggio, vittoria in trasferta

### Quote Medie e Massime di Mercato:

- **Bb1X2**: Numero di bookmaker utilizzati per calcolare le medie e i massimi
- **BbMxH**, **BbAvH**: Massimo e media delle quote per vittoria in casa
- **BbMxD**, **BbAvD**: Massimo e media delle quote per pareggio
- **BbMxA**, **BbAvA**: Massimo e media delle quote per vittoria in trasferta
- **MaxH**, **MaxD**, **MaxA**: Massimo delle quote di mercato per vittoria in casa, pareggio, vittoria in trasferta
- **AvgH**, **AvgD**, **AvgA**: Media delle quote di mercato per vittoria in casa, pareggio, vittoria in trasferta

---

## Chiavi per le quote totali sugli obiettivi:

- **BbOU**: Numero di bookmaker utilizzati per medie e massimi per over/under 2.5 goal
- **BbMx>2.5**, **BbAv>2.5**: Massimo e media per over 2.5 goal
- **BbMx<2.5**, **BbAv<2.5**: Massimo e media per under 2.5 goal
- **GB>2.5**, **GB<2.5**: Quote Gamebookers per over/under 2.5 goal
- **B365>2.5**, **B365<2.5**: Quote Bet365 per over/under 2.5 goal
- **P>2.5**, **P<2.5**: Quote Pinnacle per over/under 2.5 goal
- **Max>2.5**, **Max<2.5**: Massimo delle quote di mercato per over/under 2.5 goal
- **Avg>2.5**, **Avg<2.5**: Media delle quote di mercato per over/under 2.5 goal

---

## Chiavi per le quote di scommesse con handicap asiatico:

- **BbAH**: Numero di bookmaker utilizzati per medie e massimi per handicap asiatico
- **BbAHh**, **AHh**: Dimensione dell'handicap (squadra di casa)
- **BbMxAHH**, **BbAvAHH**: Massimo e media delle quote per handicap asiatico (squadra di casa)
- **BbMxAHA**, **BbAvAHA**: Massimo e media delle quote per handicap asiatico (squadra ospite)
- **GBAHH**, **GBAHA**: Quote Gamebookers per handicap asiatico (squadra di casa, squadra ospite)
- **LBAHH**, **LBAHA**: Quote Ladbrokes per handicap asiatico (squadra di casa, squadra ospite)
- **B365AHH**, **B365AHA**: Quote Bet365 per handicap asiatico (squadra di casa, squadra ospite)
- **PAHH**, **PAHA**: Quote Pinnacle per handicap asiatico (squadra di casa, squadra ospite)
- **MaxAHH**, **MaxAHA**: Massimo delle quote di mercato per handicap asiatico (squadra di casa, squadra ospite)
- **AvgAHH**, **AvgAHA**: Media delle quote di mercato per handicap asiatico (squadra di casa, squadra ospite)

---

## Quote di chiusura (Ultime quote prima dell'inizio della partita):

Come sopra, ma con il suffisso **C** aggiunto (es. **B365HC**, **MaxHC**).

---

## Fonti:

- **Risultati**: [XScores](http://www.xscores.com)
- **Statistiche delle partite**: BBC, ESPN Soccer, Bundesliga.de, Gazzetta.it, Football.fr
- **Quote scommesse**: Raccolte dai bookmaker individuali il venerdì pomeriggio per le partite del weekend, il martedì pomeriggio per quelle infrasettimanali.

---
