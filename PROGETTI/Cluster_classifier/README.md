# Progetto di Clustering Documenti

## Panoramica

Il progetto è realizzato utilizzando il notebook `Document-cluster-Project.ipynb` e si propone di eseguire un clustering di documenti attraverso un processo di pulizia e vettorizzazione dei dati. L'obiettivo è identificare relazioni tra documenti simili e fornire una rappresentazione utile per analisi successive.

## Prerequisiti

Per eseguire questo progetto, assicurati di avere i seguenti requisiti:

- **Python 3.x**: Assicurati di avere Python 3 installato sul tuo computer.
- **Jupyter Notebook**: Il progetto è strutturato come un Jupyter Notebook, quindi Jupyter deve essere installato.
- **Librerie Richieste**:
  - `numpy`
  - `pandas`
  - `sklearn`
  - `nltk`
  - `scipy`
  - `matplotlib`

Puoi installare le librerie necessarie usando:

```bash
pip install numpy pandas scikit-learn nltk scipy matplotlib
```

## Dataset

Il progetto utilizza un dataset di documenti che deve essere elaborato. I dati di input e le informazioni necessarie per la pulizia provengono dai seguenti file:

- **Stop word italiane**
- **Nomi di persone**
- **Nomi di comuni e città** (da `Comuni.txt`)
- **Abbreviazioni e acronimi** (da `acronimi.txt`)
- **Avverbi** (da `avverbi_italiani.txt`)
- **Codifiche di tag e accenti** (da `codifiche_accenti.txt`)
- **Altre stop words** (da `otherStopwords.txt` o tramite nuovi file aggiunti nella cartella `exception`)

## Come Funziona

Il progetto si articola nei seguenti passaggi:

1. **Pulizia dei Dati**: Viene eseguita una fase di pulizia per rimuovere le stop words e altre parole non pertinenti dai documenti, basandosi sui file sopra elencati.

2. **Vettorizzazione**: I dati puliti vengono vettorizzati utilizzando `TfidfVectorizer`, addestrando il modello con il 90% dei dati e testando il modello con il 10% restante.

3. **Clustering**: Utilizzando l'algoritmo K-means, sono stati creati cluster da 1 a 20. Il miglior modello è risultato essere K-means con 5 cluster, come mostrato nei grafici nel notebook e nelle immagini nella cartella `Plot`.

4. **Similitudine Coseno**: È stata adottata la tecnica della cosin-similarity per calcolare la correlazione tra un documento di input e i documenti nel cluster assegnato. Viene stampata una lista di documenti con una correlazione maggiore o uguale al 90%, e il risultato viene salvato in un file CSV.

## Struttura del Progetto

Il progetto è organizzato come segue:

- **Notebook**: 
  - `Document-cluster-Project.ipynb`
  
- **Script Python**: 
  - `Document-cluster-Project.py`
  - `Document-cluster-Project-SCRIPT.ipynb` (copia del notebook in formato Jupyter)

- **Cartella dei Modelli**: `model`
  - `k-means`: contiene 20 modelli addestrati
  - File `*.pickle`: operazioni di salvataggio dei vettori PCA e TF-IDF per velocizzare l'esecuzione dello script

- **Cartella dei Dati**: `data`
  - `dataset_topics_GA_10000.csv`: dataset grezzo
  - `outputfile_sentence_processed.csv`: dati elaborati dopo la fase di pre-processing
  - `risultato.csv`: risultati finali dopo l'esecuzione dello script
  - `word-cluster.txt`: prime 100 parole per ciascuno dei cluster identificati

- **Cartella dei Plot**: `PLOT`: contiene i grafici dei 20 K-means

- **Cartella Flask**: `mg-flask`: contenente il file Flask, con istruzioni per l'esecuzione.


## Licenza

Questo progetto è concesso in licenza sotto la Licenza MIT - vedi il file LICENSE per dettagli.
