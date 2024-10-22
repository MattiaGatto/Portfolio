# Progetto di Fantacalcio

## Descrizione
Questo progetto ha come obiettivo la gestione e l'analisi delle formazioni per il Fantacalcio. Attraverso una serie di funzioni, permette di selezionare i giocatori in base a ruoli specifici, valutare le statistiche e ottimizzare le formazioni.

## Funzioni Principali

### `titolari_panchina(formazione, Ruolo, n_tit, n_panc)`
Questa funzione prende in input un DataFrame di formazione e restituisce i giocatori titolari e panchinari in base al ruolo specificato.

#### Parametri
- `formazione`: DataFrame contenente i giocatori e le loro statistiche.
- `Ruolo`: Ruolo del giocatore (ad esempio 'P', 'D', 'C', 'A').
- `n_tit`: Numero di titolari richiesti.
- `n_panc`: Numero di panchinari richiesti.

#### Restituisce
- `titolari`: DataFrame dei giocatori titolari.
- `panchina`: DataFrame dei giocatori panchinari.

### Processo di Selezione dei Giocatori
Il seguente codice gestisce l'estrazione dei dati dei giocatori e il loro utilizzo per costruire la rosa:

```python
p = paragrafo.text
match_1, end = scramping(p, 0, False)
match_2, end2 = scramping(p, end, False)
...
df = pd.concat([match_1, match_2, match_3, match_4, match_5, match_6, match_7, match_8, match_9, match_10]).rename_axis('Giocatore in rosa').reset_index()
```

### Raccolta Dati dal Web
I dati vengono estratti da fonti esterne, tra cui un file Excel e un sito web dedicato al Fantacalcio.

### Analisi delle Formazioni
Dopo aver estratto e selezionato i giocatori, il progetto analizza le varie formazioni e determina la migliore configurazione basata su moduli predefiniti.

```python
Moduli = list([[3, 5, 2], [3, 4, 3], [4, 5, 1], [4, 4, 2], [4, 3, 3], [5, 3, 2], [5, 4, 1]])
for modulo in Moduli:
    schieramento = modulo
    ...
```

### Salvataggio dei Risultati
Il file finale delle formazioni viene salvato in formato Excel e viene anche generata una rappresentazione grafica delle formazioni.

## Requisiti
- Python 3.x
- pandas
- requests
- BeautifulSoup
- matplotlib

## Installazione
1. Clona questo repository.
2. Installa le dipendenze utilizzando pip

## Utilizzo
1. Esegui il file principale.
2. Inserisci il nome del team quando richiesto.
3. I risultati saranno salvati nella directory specificata.

## Contribuzione
Le pull request sono benvenute. Per favore, apri un'issue prima di apportare modifiche significative.

## Licenza
Questo progetto è sotto la licenza MIT. Vedi il file LICENSE per ulteriori dettagli.
