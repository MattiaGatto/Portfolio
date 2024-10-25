# Text Mining Use Cases

## ROADMAP

### Data Analysis
- **Exploration Datasets**: Analisi dei dataset Reuters ed Enron (Mattia Gatto, Giulia Natale, Giada Vespi)
- **Data Extraction (Reuters)**:
  - Da XML a CSV (Mattia Gatto)
  - Estrazione delle etichette da TXT a CSV (Mattia Gatto)
  - Creazione della variabile *Gender* a partire dal nome dell’autore (Mattia Gatto, Giulia Natale, Giada Vespi)
  - Creazione della colonna *Topic* dai file TXT estratti (Mattia Gatto, Giulia Natale, Giada Vespi)
  - Data Visualization (Mattia Gatto, Giulia Natale)
  - Estrazione dati multilingua: da XML a CSV (Mattia Gatto)

### Data Extraction (Mattia, Giulia)
- Eliminazione dei duplicati per righe uguali in ogni campo (Mattia Gatto, Giulia Natale)
- Check dei valori mancanti (Mattia Gatto, Giulia Natale)
- Estrazione del *Gender*: utilizzo della libreria `nltk.names` per l'estrazione del genere dal nome dell'autore (Mattia Gatto, Giulia Natale)
- Estrazione dei *Topics*: estrazione dei codici regione, industrie e topic tramite mapping tra i codici nei file XML e i relativi file di descrizione (Mattia Gatto)

### Data Visualization (Mattia, Giulia)
- **Genere**: Analisi sulla distribuzione del genere (Mattia Gatto, Giulia Natale)
- **Topic**: Analisi sulla distribuzione dei topic (Mattia Gatto, Giulia Natale)
- **Testi**: Analisi sulla distribuzione delle parole nei testi (Mattia Gatto, Giulia Natale)
- **Citazioni**: Analisi della distribuzione delle citazioni nel testo (Mattia Gatto, Giulia Natale)
- **Altre analisi** (Mattia Gatto, Giulia Natale)

### Data Preparation
- **Mattia**
  - Eliminazione dei valori null: testi o codici mancanti (Mattia Gatto)
  - Aggregazione delle etichette per radice: rimozione delle etichette con esempi pari a zero e aggregazione dei codici per radice (Mattia Gatto)
  - Pulizia del testo (tokenizzazione, rimozione della punteggiatura, stopping) utilizzando la libreria `nltk` (Mattia Gatto)

### Features Engineering
- **Mattia**
  - Applicazione di TF-IDF Vectorizer: vettorizzazione dei testi tramite questa libreria di `nltk` (Mattia Gatto)
  - Applicazione dell'algoritmo di PCA: tecniche per ridurre la complessità dei dati (Mattia Gatto)
  - Applicazione di tecniche di embedding tokenizer (BERT, RoBERTa) (Mattia Gatto)
  - Split in Train, Validation e Test set (80-20-10): creazione dei tre input (Mattia Gatto)

### Model Training
- **Mattia**
  - Modelli di Classificazione (Adaboost, alberi decisionali, ecc.) (Mattia Gatto)
  - Modelli di embeddings (RoBERTa, BERT) (Mattia Gatto)
  - Studio della tecnica Bert-MUSE (Mattia Gatto)

### Model Verification
- **Mattia**
  - K-Fold Cross Validation (Mattia Gatto)
  - Metrics:
    - Classification Report
    - AUC

### Valutazione Plus
- **Mattia**
  - Applicazione di Muse per la valutazione dei modelli di embedding riaddestrati per il linguaggio universale con uguali parametri (Mattia Gatto)

### Vertex AI
- **Mattia**
  - Ingegnerizzazione del caso d’uso Text Mining - Topic Extraction su Vertex AI:
    - Creazione di Bucket e inserimento dei dati pre-laborati per la costruzione del modello (Mattia Gatto)
    - Applicazione di AutoML in Vertex AI (Mattia Gatto)
    - Applicazione di modelli di BigQueryML (Mattia Gatto)
    - Creazione di un ambiente JupyterLAB in Vertex AI (Mattia Gatto)
    - Creazione di script per la creazione di modelli custom in Vertex AI (Mattia Gatto)

### Note Importanti
- **Dati di Input e Modelli**: Alcuni dati di input e modelli non sono inclusi in questo repository a causa della loro elevata dimensione, che ne ha impedito il caricamento su Git. Per ulteriori informazioni, contattami direttamente.
