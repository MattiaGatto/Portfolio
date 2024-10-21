```markdown
# Progetto di Machine e Deep Learning

## Dipartimento di Ingegneria Informatica, Modellistica, Elettronica e Sistemistica (DIMES)
### Corso di Laurea Magistrale in Ingegneria Informatica

## Relazione del Progetto
**Titolo:** Classificazione e Anomaly Detection di:
- Immagini
- Testi

**Studente:** Mattia Gatto, 216649  
**Docenti:** Fabrizio Angiulli, Fabio Fassetti  
**Esercitatore:** Luca Ferragina

---

## Sommario
1. [Immagini](#1-immmagini)  
   1.1. [Preprocessing](#11-preprocessing)  
   1.2. [Classificazione](#12-classificazione)  
      1.2.1. [AdaBoost](#121-adaboost)  
      1.2.2. [SVM](#122-svm)  
      1.2.3. [Reti Neurali](#123-reti-neurali)  
      1.2.4. [Stima di densità](#124-stima-di-densità)  
   1.3. [Anomaly Detection](#13-anomaly-detection)  
2. [Dati Sequenziali testuali](#2-dati-sequenziali-testuali)  
   2.1. [Preprocessing](#21-preprocessing)  
   2.2. [Classificazione](#22-classificazione)  

---

## 1. Immagini

Nel realizzare un sistema di apprendimento automatico per la classificazione di immagini, mediante l'utilizzo del linguaggio di programmazione Python, ho preferito partire da un’analisi iniziale del dataset. 

### 1.1. Preprocessing
Ho utilizzato la libreria “opencv” per leggere le immagini e trasformarle in matrici. Ogni elemento della matrice rappresenta l’intensità del pixel normalizzato da 0 a 1. Ho anche convertito le immagini in scala di grigi.

Per quanto riguarda le label, ho sviluppato un algoritmo per leggere il path delle immagini e inserire un indicatore per la categoria:
- 0 per “handbags”
- 1 per “sports-shoes”
- 2 per “tops”
- 3 per “trousers”

### 1.2. Classificazione
Ho sviluppato modelli per le tecniche richieste dalla traccia del progetto e addestrati su un trainset di dataset e label, valutati successivamente su un testset.

#### 1.2.1. AdaBoost
Ho preparato il trainset in formato “2D” e ho utilizzato il classificatore OneVsRestClassifier per la multi-classificazione.

#### 1.2.2. SVM
Ho analizzato l’algoritmo di support vector machine con un kernel polinomiale, evidenziando miglioramenti in termini di accuratezza.

#### 1.2.3. Reti Neurali
Ho definito sia un modello denso che convoluzionale, osservando che il modello denso ha mostrato una migliore accuratezza media.

#### 1.2.4. Stima di densità
Ho utilizzato Gaussian Naive Bayes e Nearest Neighbor, evidenziando le differenze nei risultati in base agli iperparametri.

### 1.3. Anomaly Detection
Ho implementato un modello di autoencoder basato su rete neurale densa, utilizzando la funzione di attivazione sigmoidea per l’output.

---

## 2. Dati Sequenziali Testuali

Per la classificazione di dati sequenziali di tipo testuale, ho utilizzato la libreria “nltk” per il preprocessing, includendo tokenizzazione, rimozione della punteggiatura, e stemming.

### 2.1. Preprocessing
Ho utilizzato TfidfVectorizer per normalizzare i valori e trasformare il dataset in una matrice sparsa.

### 2.2. Classificazione
Ho definito un trainset e un testset per le label e il dataset, e ho proceduto con l'addestramento dei modelli di classificazione.
- **Modelli Sviluppati**: I modelli sono stati addestrati su un dataset di testi e valutati utilizzando un test set e la validazione incrociata (K-fold con \( k=10 \)).
- **Confronto dei Risultati**: I risultati dei modelli vengono plottati per facilitare il confronto.
#### 2.2.1. **AdaBoost**:
   - Utilizza l'algoritmo `OneVsRestClassifier` di `sklearn` per la multi-classificazione.
   - Diverse configurazioni di parametri vengono testate.

#### 2.2.2. **SVM (Support Vector Machine)**:
   - Analisi condotta con un kernel polinomiale, notando miglioramenti significativi rispetto al kernel lineare.
   - L'accuratezza migliora con il grado del polinomio fino a un certo punto, quindi si stabilizza.

#### 2.2.3. **Reti Neurali**:
   - Struttura del modello: layer densi con diverse configurazioni di neuroni e funzioni di attivazione (`relu` per i layer intermedi e `sigmoid` per l'output).
   - Preprocessing per ridurre le dimensioni del dataset utilizzando `TfidfVectorizer`.

#### 2.2.4. **Stima di Densità**:
   - Utilizzo di `nearest neighbor`, con analisi sull'impatto del numero di vicini sull'accuratezza.

#### 2.3 Rilevamento di Anomalie
- **Modello Autoencoder**: Costruito con una rete neurale densa, include un encoder e un decoder.
- Preprocessing per determinare le label di inlier (0) e outlier (1).
- Addestramento effettuato con l'ottimizzazione "adam" e valutato tramite la curva ROC e il valore di AUC.

### 3. Funzionamento dello Script di Esecuzione
- **Modulo**: Il modulo principale è `MAIN.py`.
- **Parametri**: Al lancio, il primo parametro indica il tipo di input (immagini o testi), e il secondo è il percorso del file.
- **Esempi di Esecuzione**:
  - `python MAIN.py -i immagini-3`
  - `python MAIN.py -t test-3.xlsx`

---

## Conclusioni
Il progetto ha fornito un'analisi dettagliata delle tecniche di Machine Learning applicate alla classificazione di immagini e testi, con un focus su prestazioni e accuratezza.

## Contatti
Per ulteriori informazioni, contattare:
- **Mattia Gatto**
```
