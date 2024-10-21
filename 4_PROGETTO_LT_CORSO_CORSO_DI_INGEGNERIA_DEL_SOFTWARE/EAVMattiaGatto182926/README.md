```markdown
# Progetto di Valutazione Espressioni Aritmetiche

Questo progetto implementa un sistema per la valutazione di espressioni aritmetiche tramite un'interfaccia grafica. Durante la realizzazione, sono stati utilizzati i seguenti design pattern:

## Pattern utilizzati

### Interpreter
Il pattern **Interpreter** è un pattern comportamentale che permette di definire una grammatica per linguaggi semplici, rappresentare proposizioni in tale linguaggio e interpretare queste proposizioni. 

Struttura del pattern:
- **AbstractExpression**: Definisce il metodo astratto `Interpret` comune a tutti i nodi dell'albero sintattico.
- **TerminalExpression**: Implementa il metodo `Interpret` per i simboli terminali della grammatica.
- **NonTerminalExpression**: Implementa `Interpret` per i simboli non terminali e chiama ricorsivamente se stesso.
- **Context**: Contiene le informazioni comuni all’interprete e la stringa da analizzare.
- **Client**: Costruisce l’albero sintattico astratto.

### Composite
Il pattern **Composite** è un pattern strutturale che permette di comporre oggetti in strutture ad albero per rappresentare gerarchie "parte-tutto" e consente ai client di trattare oggetti singoli e composizioni in modo uniforme.

## Funzionalità principali

L'interfaccia grafica consente agli utenti di:
1. Inserire un'espressione aritmetica intera in una casella di testo e valutare il risultato tramite il tasto "Valuta".
2. Caricare un'espressione salvata in un file `expr.txt` tramite il tasto "Carica".
3. Salvare l'espressione e il risultato corrente in un file tramite il tasto "Salva".
4. Accedere a un menù con le seguenti opzioni:
   - **Esci**: Chiude l’applicazione.
   - **Contesto**: Permette di salvare o caricare variabili di contesto da un file `context.txt`.
   - **Rimpiazza**: Sostituisce variabili all'interno delle espressioni.

## Struttura del Codice

Il sistema è strutturato con le seguenti classi:
- **Analizzatore**: Analizza l'espressione aritmetica e riconosce i simboli (operatori, costanti, variabili, parentesi).
- **Parser**: Interpreta l'espressione secondo le regole della grammatica, risolvendo termini e fattori con operatori aritmetici.

### Esempio di Codice

#### Classe `Analizzatore`

```java
public class Analizzatore {
    // Definizione dei simboli e analisi dell'espressione
}
```

#### Classe `Parser`

```java
public class Parser {
    // Parsing e interpretazione dell'espressione aritmetica
}
```

## Interfaccia Utente

L'interfaccia presenta le seguenti funzionalità:
- Inserimento e valutazione di espressioni aritmetiche.
- Caricamento e salvataggio di espressioni da/verso file.
- Gestione di variabili di contesto.

## Diagramma delle Classi

La struttura delle classi è organizzata in modo che la classe `Parser` coordini i vari componenti e risolva l'espressione in base ai simboli presenti, come addizione, sottrazione, moltiplicazione, divisione, e parentesi.

```
Parser --> Analizzatore --> Espressione
```

## Conclusione

Il progetto dimostra l'utilizzo efficace dei pattern **Interpreter** e **Composite** per la valutazione e gestione di espressioni aritmetiche, con un'interfaccia grafica intuitiva per l'utente finale.
```