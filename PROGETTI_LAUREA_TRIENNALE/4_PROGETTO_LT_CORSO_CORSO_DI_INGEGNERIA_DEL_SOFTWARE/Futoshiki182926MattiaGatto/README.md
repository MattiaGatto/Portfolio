# Progetto Futoshiki
La relazione descrive l'uso del **Template Method Design Pattern**, un pattern comportamentale orientato alle classi, in cui la struttura di un algoritmo viene definita in una classe astratta, mentre i dettagli specifici sono implementati nelle sottoclassi. L'obiettivo del pattern è promuovere il riutilizzo del codice e facilitare la modifica di parti specifiche di un algoritmo senza alterarne la struttura generale.

### Template Method nel contesto del progetto
L'algoritmo del **backtracking** è stato scelto per risolvere il gioco. La classe astratta contiene il metodo `risolvi()`, che definisce il flusso principale dell'algoritmo, mentre le sottoclassi ridefiniscono i dettagli concreti attraverso metodi specifici.

#### Struttura del Template Method:
- **AbstractTemplate**: Definisce il metodo templateMethod(), che utilizza i metodi primitivi `primitiveOperation1()` e `primitiveOperation2()`.
- **ConcreteTemplate**: Estende `AbstractTemplate` e ridefinisce i metodi, conformandoli al metodo risolvi().

### Caso d'uso del programma
L'interfaccia grafica prevede vari scenari interattivi per l'utente:
1. **Inserimento parametri**: L'utente può selezionare il numero di celle del gioco e il livello di difficoltà (facile, medio, difficile).
2. **Interazione con il gioco**: Una volta avviato, l'utente può:
   - Selezionare una cella e inserire un valore.
   - Verificare la soluzione o chiedere al sistema di risolverla.
3. **Interfaccia delle soluzioni**: Permette di navigare tra le varie soluzioni e accedere a opzioni come "Esci" o "Nuova partita".

### Classi principali
- **Futoshiki**: La classe principale che implementa il metodo di risoluzione del problema. Utilizza una matrice di celle (`GameTable`), con una lista di possibili soluzioni. Ogni cella ha relazioni specifiche con le celle adiacenti (es. maggiore o minore) e il valore assegnato può essere modificato.
  
  #### Metodo risolvi()
  Il metodo implementa l'algoritmo di backtracking per esplorare le possibili soluzioni. In ogni iterazione:
  - Si seleziona un punto di scelta iniziale.
  - Si verifica se il valore può essere assegnato alla cella corrente.
  - Se la soluzione è valida, si memorizza; altrimenti, si esegue il backtracking.

### Altre classi rilevanti:
- **Cell**: Rappresenta una cella del gioco con riga e colonna.
- **GameTable**: Modella la griglia di gioco, con metodi per gestire le celle e verificarne i valori.
- **GameCell**: Ogni cella del gioco ha un valore numerico e può avere relazioni con le celle adiacenti (nord, sud, est, ovest). Le relazioni sono definite dall'enumerazione **Relation** (LESS, NONE, GREATER).

### Logica dei controlli:
Ogni cella del gioco deve rispettare una serie di vincoli:
- **Relazioni di maggiore/minore** con le celle vicine.
- **Valori unici** su righe e colonne.

### Implementazione finale
L'applicazione permette all'utente di giocare a Futoshiki, con la possibilità di verificare le soluzioni e ricevere assistenza nel risolvere il puzzle. La logica del gioco è implementata con il supporto del pattern Template Method e di classi che modellano ogni componente del gioco.