# Valutatore di Espressioni Aritmetiche

Questo progetto implementa un valutatore di espressioni aritmetiche sia in modalità interattiva da console, sia tramite un'interfaccia grafica (GUI) utilizzando Java. Il sistema permette di inserire espressioni aritmetiche e ottenere il risultato con un semplice click, oppure di resettare l'espressione per un nuovo calcolo. Il progetto gestisce anche errori nelle espressioni malformate tramite espressioni regolari.

## Funzionalità

- **Modalità Console**:
  - L'utente inserisce espressioni aritmetiche intere attraverso un prompt `>>` e il programma calcola il risultato.
  - Il programma gestisce eccezioni per espressioni malformate e notifica l'utente.
  - La sessione interattiva termina inserendo un punto `.`.
  
- **Modalità GUI**:
  - Interfaccia grafica user-friendly che consente di inserire espressioni aritmetiche e visualizzare il risultato.
  - Pulsanti per valutare l'espressione e resettare i campi.
  - Gestione delle espressioni malformate tramite un messaggio di errore visibile all'utente.
  
## Utilizzo

### Modalità Console

1. Avviare il programma in modalità console.
2. L'utente può inserire espressioni come `(2 + 3) * 4`.
3. Il programma calcolerà il risultato o mostrerà un messaggio di errore in caso di espressioni malformate.
4. Per uscire dal ciclo, inserire `.`.

### Modalità GUI

1. Avviare il programma in modalità grafica.
2. Inserire un'espressione aritmetica nel campo "ESPRESSIONE".
3. Cliccare su `VALUTA` per ottenere il risultato.
4. Cliccare su `RESETTA` per cancellare l'espressione corrente e iniziare un nuovo calcolo.
5. In caso di espressioni malformate, verrà visualizzato un messaggio di errore.

## Struttura del Codice

- **Main**: Il metodo principale avvia il programma in modalità GUI o Console.
- **FinestraValuta (GUI)**: Classe che estende `JFrame` e implementa `ActionListener`. Gestisce l'interfaccia grafica e l'interazione con l'utente.
- **Espressione**: Classe che valuta l'espressione aritmetica e gestisce le eccezioni per espressioni non valide.

## Requisiti

- Java SDK 8 o superiore
- Librerie `javax.swing` per l'interfaccia grafica.

## Istruzioni per l'Esecuzione

1. Compilare il progetto con il comando `javac`.
2. Eseguire il file `.class` con il comando `java`.

Esempio:

```bash
javac Main.java
java Main
```

## Autore

- **Mattia Gatto** - Creatore del progetto e implementazione.
