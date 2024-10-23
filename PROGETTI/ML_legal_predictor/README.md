# Progetto di Vettorizzazione Legale

## Panoramica

Questo progetto dimostra un metodo per vettorizzare documenti legali per trasformare i dati testuali in un formato adatto agli algoritmi di machine learning. L'obiettivo è preprocessare e vettorizzare il testo dei documenti legali per l'uso in compiti di elaborazione del linguaggio naturale (NLP), come la classificazione dei documenti, il topic modeling o l'analisi del testo legale.

## Prerequisiti

Per eseguire questo progetto, è necessario avere i seguenti requisiti:

- **Python 3.x**: Assicurati di avere Python 3 installato sul tuo computer.
- **Jupyter Notebook**: Il progetto è strutturato come un Jupyter Notebook (`Legal-Vectorization-Project.ipynb`), quindi Jupyter deve essere installato.
- **Librerie Richieste**:
  - `numpy`
  - `pandas`
  - `sklearn`
  - `nltk`

Per installare le librerie richieste, utilizza:

```bash
pip install numpy pandas scikit-learn nltk
```

## Dataset

Il dataset utilizzato in questo progetto include documenti legali che devono essere elaborati. Dovrebbe essere in formato testo semplice, con ciascun documento che rappresenta un caso legale distinto o un documento correlato.

Potrebbe essere necessario fornire il proprio dataset modificando la sezione di caricamento dei dati del notebook.

## Come Funziona

Il progetto si compone dei seguenti passaggi:

1. **Preprocessing del Testo**: I documenti legali vengono prima puliti rimuovendo punteggiatura, stop word e applicando la tokenizzazione. Il passaggio di preprocessing assicura che i dati testuali siano in un formato adeguato per la vettorizzazione.

2. **Vettorizzazione**: Dopo il preprocessing, i dati testuali vengono vettorizzati utilizzando il `CountVectorizer` o il `TfidfVectorizer` di `sklearn`. Questo converte i dati testuali in una matrice di conteggi dei token o valori TF-IDF, che possono poi essere inseriti in un modello di machine learning.

3. **Modellazione (Opzionale)**: Una volta vettorizzati, i dati testuali sono pronti per la classificazione, il clustering o altri compiti legati all'NLP. Sebbene questo progetto si concentri sul passaggio di vettorizzazione, è una parte cruciale di qualsiasi pipeline di analisi del testo legale.

## Panoramica del Codice

I componenti principali del notebook includono:

### 1. Preprocessing

```python
import nltk
import re
from nltk.corpus import stopwords

nltk.download('stopwords')

def preprocess_text(text):
    text = text.lower()
    text = re.sub(r'[^\w\s]', '', text)
    tokens = text.split()
    tokens = [word for word in tokens if word not in stopwords.words('english')]
    return ' '.join(tokens)
```

Questa funzione pulisce il testo di input eseguendo:
- La conversione del testo in minuscolo
- La rimozione di punteggiatura e caratteri speciali
- La tokenizzazione e la rimozione delle stop word

### 2. Vettorizzazione

```python
from sklearn.feature_extraction.text import CountVectorizer, TfidfVectorizer

vectorizer = TfidfVectorizer(max_features=1000)  # oppure CountVectorizer(max_features=1000)
X = vectorizer.fit_transform(preprocessed_texts)
```

Questa parte del codice utilizza `TfidfVectorizer` o `CountVectorizer` per trasformare i testi legali elaborati in una forma vettorizzata, con un massimo di 1000 caratteristiche.

### 3. Modello di Machine Learning Opzionale

Sebbene il notebook si concentri principalmente sulla vettorizzazione, puoi estenderlo implementando un modello di classificazione o clustering.

```python
from sklearn.linear_model import LogisticRegression
from sklearn.model_selection import train_test_split

X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.3, random_state=42)

model = LogisticRegression()
model.fit(X_train, y_train)
```

Puoi addestrare una regressione logistica o qualsiasi altro modello utilizzando le caratteristiche vettorizzate per ulteriori analisi.

## Come Eseguire il Progetto

1. Clona il repository o scarica il file del notebook.
2. Assicurati di aver installato le dipendenze necessarie come descritto nella sezione "Prerequisiti".
3. Carica il tuo dataset o modifica la sezione di caricamento dei dati per adattarlo ai tuoi documenti legali.
4. Esegui il notebook per preprocessare e vettorizzare i testi legali.

