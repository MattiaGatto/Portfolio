# Progetto di Sintesi Testuale e Traduzione con OpenAI e Llama-2

## Descrizione

Questo progetto utilizza le API di OpenAI e il modello Llama-2 per effettuare la sintesi di testi e la traduzione. Il codice prevede l'installazione di librerie necessarie, il caricamento di dati, la loro analisi, la preparazione e la sintesi finale. È progettato per analizzare estratti di giudizi legali, estraendo concetti principali e fornendo riassunti dettagliati.

## Requisiti

- Python 3.x
- pip

## Installazione

1. Crea un ambiente virtuale e attivalo:

   ```bash
   python -m venv openai-env
   openai-env\Scripts\activate
   ```

2. Installa le librerie richieste:

   ```bash
   pip install --upgrade openai transformers translators nltk
   ```

3. Imposta la tua chiave API di OpenAI:

   ```bash
   setx OPENAI_API_KEY "tuachiaveapikey"
   ```

## Utilizzo

### Importazione delle librerie

Il codice inizia importando le librerie necessarie:

```python
import pandas as pd
import numpy as np
import os
import time
import nltk
from openai import OpenAI
import transformers
from transformers import pipeline
import translators as ts
```

### Funzioni principali

- **`wrap(x)`**: Funzione per il wrapping del testo.
- **`translate(text, from_lang, to_lang)`**: Funzione per tradurre il testo da una lingua a un'altra.
- **`split_text_in_periods(text, max_length)`**: Funzione per dividere il testo in segmenti di lunghezza massima specificata.
- **`generate_summary(text)`**: Funzione per generare un riassunto di un testo utilizzando l'API OpenAI.
- **`summary_generator_LLaMA2(text)`**: Funzione per generare riassunti utilizzando il modello Llama-2.

### Analisi dei dati

Il codice legge un file CSV contenente dati legali, pulisce e prepara il dataset per la sintesi. 

```python
df_sentence = pd.read_csv("data/dataset_topics_GA_10000.csv", sep=";;;;", encoding="utf-8")
```

### Esecuzione della sintesi

Una volta preparato il dataset, è possibile generare un riassunto utilizzando le funzioni definite:

```python
text = "Testo da sintetizzare."
summary = generate_summary(text)
print(summary)
```

## Note

- Assicurati di non condividere la tua chiave API di OpenAI.
- Puoi modificare il modello utilizzato per la sintesi cambiando il parametro del modello nella funzione `generate_summary`.

## Contribuzione

Se desideri contribuire a questo progetto, sentiti libero di aprire un'issue o inviare una pull request.

## Licenza

Questo progetto è concesso in licenza.