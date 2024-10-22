# Analisi della Pandemia COVID-19: Modelli di Regressione

## Informazioni sul Progetto

**Progetto per il Corso di Data Mining 2019/2020**  
**Docenti:** Sergio Greco, Antonio Caliò  
**Studente:** Mattia Gatto  
**Matricola:** 216649  

## Contesto

La COVID-19, causata dal virus SARS-CoV-2, è una malattia respiratoria acuta che ha avuto origine durante la pandemia del 2019-2020. Fino ad oggi, sono stati registrati oltre 11 milioni di casi confermati e 526.465 decessi a livello mondiale.

## Motivazioni alla Base dello Studio

Dal 22 gennaio 2020, sono stati registrati record giornalieri per ogni paese riguardo ai nuovi casi, decessi e recuperi. I dati utilizzati sono raccolti quotidianamente e includono:

- Nuovi casi
- Morti
- Guariti
- Ricoverati

### Obiettivo

L'obiettivo del progetto è studiare il dataset per determinare i valori che permettono di prevedere il numero relativo ai nuovi casi in un sottoinsieme di paesi. In particolare, vengono analizzati i 10 paesi con il maggior numero di casi confermati fino al 23 giugno 2020.

## Descrizione del Dataset

Il dataset contiene:

- **28798 righe**
- **10 colonne**
  - 7 colonne di tipo intero (Confirmed, Active, Deaths, Recovered, New Cases, New Deaths, New Recovered)
  - 1 colonna di tipo data (il singolo giorno)
  - 2 colonne di tipo oggetto (Country/Region e WHO Region)

## Preprocessing: Data Cleaning

### Fasi di Preprocessing

1. **Fase 1:** Riformulazione del dataset creando un DataFrame che utilizza la data come indice.
2. **Fase 2:** Selezione delle colonne originali escludendo quelle relative alle regioni.
3. **Fase 3:** Eliminazione di dati nulli, mancanti o duplicati.
4. **Fase 4:** Integrazione di una nuova proprietà per il lockdown adottato in ogni paese (Totale, Parziale, Nullo).

## Visualizzazione dei Dati

Dopo le modifiche di preprocessing, vengono effettuate visualizzazioni sui dati, tra cui:

- Grafico dell'andamento totale dei casi confermati.
- Analisi della matrice di correlazione tra i nuovi casi nei vari paesi.

## Trasformazioni e Modelli

Vengono costruiti 8 DataFrame, ognuno per ogni `Delta[i]` che rappresenta il numero di giorni da osservare per la predizione. I dati vengono normalizzati e utilizzati per creare set di addestramento e test per i modelli di regressione.

## Metriche e Risultati

Le metriche di valutazione per ogni modello di regressione vengono calcolate e visualizzate in tabelle per confrontare le prestazioni dei diversi regressori.

## Installazione e Esecuzione

- Assicurati di avere installato Python e le librerie necessarie (ad esempio, pandas, numpy, matplotlib).
- Clona il repository e naviga nella directory del progetto.
- Esegui il notebook Jupyter per vedere le analisi e i risultati.

```bash
git clone [URL del repository]
cd [nome del progetto]
jupyter notebook
```

## Contributi

Se desideri contribuire a questo progetto, ti preghiamo di inviare una richiesta di pull con le tue modifiche.

## Licenza

Questo progetto è distribuito sotto la [Nome della Licenza].

## Contatti

Per ulteriori informazioni, contattare:
- Mattia Gatto: [mattia.gatto1997@libero.it]