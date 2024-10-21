# Progetto di Intelligenza Artificiale e Rappresentazione della Conoscenza

## Descrizione del Progetto
Questo progetto si propone di esplorare l'applicazione di tecniche di intelligenza artificiale per la rappresentazione della conoscenza attraverso un algoritmo evolutivo. L'obiettivo principale è sviluppare un sistema in grado di generare e ottimizzare soluzioni attraverso fasi di selezione, crossover e mutazione.

## Struttura dell'Algoritmo

### 1. Selezione
Nella fase di selezione, le soluzioni migliori vengono scelte per la generazione di nuove entità, basandosi sulle loro prestazioni in precedenti iterazioni dell'algoritmo.

### 2. Crossover
- **Obiettivo**: Generare nuove possibili soluzioni (nuove entità) partendo da quelle scelte nella fase di selezione.
- **Tecnica Utilizzata**: Crossover a 2 punti
  - Viene effettuata la selezione di due soluzioni (array dei pesi delle euristiche) e vengono identificati due punti per dividere l'array in tre sezioni: testa, coda e parte centrale.
  
#### Formazione delle Nuove Soluzioni:
1. **Prima Nuova Soluzione**: Combinazione di testa e coda della prima soluzione con la parte centrale della seconda.
2. **Seconda Nuova Soluzione**: Combinazione di testa e coda della seconda soluzione con la parte centrale della prima.

> **Nota**: I geni corrispondenti ai pesi sono rappresentati come float, non come bit.

### 3. Mutazione
- **Obiettivo**: Introduzione di variabilità selezionando casualmente un gene nella lista e modificandolo.
- **Esecuzione**: Questa fase non si verifica sempre; viene fissata una frequenza tramite un valore generato da `Math.random`.

## Sperimentazione
Una volta che l'algoritmo converge, esso viene interrotto e viene estratto il giocatore con il maggior numero di vittorie. Non è stato fissato un numero di iterazioni o un tempo specifico; l'algoritmo si interrompe quando si osserva che il giocatore vincente rimane costante.

Dopo aver generato un numero sufficiente di entità, viene organizzato un **torneo all'italiana** per determinare il miglior giocatore. Durante questa fase, è emerso che alcuni giocatori si comportavano meglio con le pedine bianche e altri con le nere. Questa differenza di prestazione si riflette nei pesi variabili assegnati al **ConcretePlayer**.

## Conclusioni
Il progetto ha dimostrato come le tecniche evolutive possano essere utilizzate efficacemente per l'ottimizzazione delle strategie di gioco attraverso l'analisi delle prestazioni dei giocatori in diverse configurazioni.
