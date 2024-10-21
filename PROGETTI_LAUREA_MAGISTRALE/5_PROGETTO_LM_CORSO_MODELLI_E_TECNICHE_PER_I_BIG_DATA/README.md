```markdown
# README

## Project Title: Analisi delle Catastrofi Sandy e Joplin attraverso il Framework SPARK

### Studenti
- **Mattia Gatto**
- **Francesco Maria Granata** 

### Professori
- **Prof. Paolo Trunfio**
- **Prof. Fabrizio Marozzo**

### Anno Accademico: 2020/2021

---

## Indice

1. [Introduzione](#introduzione)
2. [Preelaborazione dei dati](#preelaborazione-dei-dati)
3. [Framework e servizi utilizzati](#framework-e-servizi-utilizzati)
   - [Apache Spark](#apache-spark)
   - [Apache Livy](#apache-livy)
4. [Grafica](#grafica)

---

## Introduzione

Negli ultimi anni, le piattaforme di microblogging sono diventate strumenti importanti per condividere informazioni sul Web, specialmente durante eventi critici come disastri naturali. Twitter è stato utilizzato per diffondere notizie su vittime, danni, donazioni, avvisi e informazioni multimediali.

In questo progetto, viene effettuata un’analisi preliminare su un discreto numero di Tweet generati durante due differenti disastri naturali:

- **Joplin 2011**: 206.764 tweet raccolti durante il tornado che ha colpito Joplin, Missouri (USA) il 22 maggio 2011, utilizzando l'hashtag “#joplin”.
- **Sandy 2012**: 140.000 tweet raccolti durante l'uragano Sandy il 29 ottobre 2012, utilizzando gli hashtag “#sandy”, “#nyc”.

### Classificazione dei Tweet

I messaggi generati durante un disastro sono estremamente vari. Questo progetto si concentra principalmente sui Tweet di tipo informativo, che si dividono in:

- **Diretti**: scritti da una persona testimone oculare.
- **Indiretti**: messaggi che ripetono informazioni da altre fonti.
- **Altro**: messaggi non correlati al disastro.

---

## Preelaborazione dei dati

Inizialmente, abbiamo visualizzato i dataset in formato tabellare utilizzando la libreria `pandas` in Python, eliminando le colonne non utili. Questo ci ha fornito una visione più chiara delle potenzialità dei dataset.

---

## Framework e servizi utilizzati

### Apache Spark

Utilizziamo Apache Spark, un framework open-source per il processamento di dati su larga scala, per elaborare i dataset tramite il linguaggio di programmazione Scala.

Abbiamo creato diversi oggetti che operano su specifici dataset, implementando due famiglie di query:

- **Query statistiche**: restituiscono statistiche di tipo count sulle classi di Tweet.
- **Query sui Tweet**: restituiscono i Tweet di una certa classe o gli autori.

### Apache Livy

Apache Livy facilita l'interazione tra Spark e le applicazioni client, permettendo di inviare processi Spark e gestire il contesto Spark tramite una semplice interfaccia REST. 

Abbiamo configurato Livy per:

- Creare un’API REST utilizzabile dal client.
- Gestire i job Spark tramite richieste POST.
- Restituire l’ID della risposta in formato JSON.

---

## Grafica

Per la componente grafica, abbiamo utilizzato il modulo `TKinter` di Python, creando un'interfaccia che gestisce tre tipi di query:

- Esclusivamente per la catastrofe Sandy.
- Esclusivamente per la catastrofe Joplin.
- Confronto tra le due catastrofi.

La visualizzazione dei risultati avviene tramite:

- Un campo di testo per visualizzare i Tweet o gli utenti.
- Un oggetto plot del modulo `matplotlib` per i DataFrame pandas.

---

## Conclusioni

Questo progetto ha dimostrato come i dati generati durante disastri naturali possano essere analizzati efficacemente utilizzando tecniche di big data, contribuendo così a una migliore comprensione e gestione delle emergenze future.
```