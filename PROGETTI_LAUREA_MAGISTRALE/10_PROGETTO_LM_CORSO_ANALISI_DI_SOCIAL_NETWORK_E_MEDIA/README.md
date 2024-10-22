# Progetto di Analisi di Social Network e Sentiment Analysis su TikTok

## Introduzione
Il progetto riguarda l'analisi dei post pubblicati sulla piattaforma TikTok. Lo scopo è l'estrazione dei dati attraverso librerie Python e la successiva analisi dei testi e delle emoji presenti nei post, utilizzando tecniche di Natural Language Processing (NLP) e Sentiment Analysis.

## Obiettivi del Progetto
1. Estrarre informazioni dai post TikTok utilizzando librerie Python.
2. Effettuare l'analisi del sentiment sui testi e sulle emoji.
3. Utilizzare modelli NLP, tra cui RoBERTa, per la classificazione del sentiment.

## Estrazione dei Dati
L'estrazione dei dati dalla piattaforma TikTok è stata effettuata utilizzando una libreria non ufficiale chiamata `TikTokApi`, un wrapper Python per l'API di TikTok. Sono stati presi in considerazione 711 utenti appartenenti alla **top 1000** in termini di popolarità, raccogliendo informazioni relative agli ultimi sei post pubblicati.

### Preparazione dei Dati
I dati raccolti sono stati organizzati in un dataset tabellare contenente le informazioni dell'utente e dei post, con una colonna specifica per il testo dei post. Le emoji presenti nei testi sono state codificate in forma testuale per poter essere elaborate dal modello NLP.

## Natural Language Processing (NLP)
Per l'analisi dei testi è stato utilizzato il modello **RoBERTa**, una variante di BERT ottimizzata per prestazioni superiori. La pipeline NLP ha incluso le seguenti fasi:

1. **Tokenizzazione**
2. **Rimozione della punteggiatura**
3. **Rimozione delle stop words**
4. **Stemming e Lemmatization**

### Modello NLP e Sentiment Analysis
L'algoritmo RoBERTa è stato addestrato sui testi dei post, e successivamente è stata effettuata una valutazione del modello utilizzando metriche come `precision`, `recall`, e `f1-score`.

### Risultati del Modello
- **Accuracy**: 0.98
- **Macro avg**: 0.97
- **Weighted avg**: 0.98

## Analisi del Sentiment delle Emoji
Oltre ai testi, è stato condotto un ulteriore processamento delle emoji utilizzando la libreria `emosent-py` per determinare il sentiment medio delle emoji in ciascun post.

### Risultati dell'Analisi delle Emoji
- **Accuracy**: 0.97
- **Macro avg**: 0.83
- **Weighted avg**: 0.97

## Modello senza Preprocessing del Testo
Un ulteriore esperimento è stato condotto senza applicare il preprocessing testuale. In questo caso, sono stati ottenuti risultati differenti, con un'accuratezza leggermente inferiore rispetto al modello con preprocessing.

## Conclusione
L'utilizzo di modelli NLP avanzati come RoBERTa ha permesso di ottenere ottimi risultati nell'analisi del sentiment sui testi di TikTok. L'integrazione dell'analisi delle emoji ha ulteriormente arricchito la comprensione del sentiment espresso nei post.

## Riferimenti
- [TikTokApi](https://github.com/davidteather/TikTok-Api)
- [Hypeauditor - Top Instagram](https://hypeauditor.com/top-instagram/?p=1)

