# Progetto Campo Minato - Fondamenti di Informatica

## Descrizione

Questo progetto è una versione semplificata del celebre gioco **Campo Minato (Minesweeper)**, sviluppato in Java come parte del corso di **Fondamenti di Informatica e Laboratorio (2°)**. L'obiettivo del gioco è scoprire tutte le caselle di un campo di gioco senza far esplodere le mine nascoste. Il giocatore può selezionare una casella alla volta e se una mina si trova nella casella selezionata, il gioco termina.

## Struttura del Progetto

La struttura del progetto è organizzata come segue:

```
/
├── bin/                       # Directory che contiene i file compilati (bytecode)
├── src/                       # Directory contenente i file sorgente del progetto
│   ├── Campo.java             # Classe che gestisce il campo di gioco
│   ├── CampoMinato.java       # Contiene la logica principale del gioco
│   ├── Casella.java           # Classe che rappresenta una singola casella del campo
│   └── Gioco.java             # Classe principale che avvia il gioco e gestisce l'interfaccia con l'utente
├── .classpath                 # File di configurazione dell'ambiente di sviluppo (Eclipse/IDE)
├── .project                   # File di progetto Eclipse/IDE
├── Mina.png                   # Icona o immagine utilizzata nel gioco (presumibilmente per rappresentare una mina)
└── README.md                  # Documentazione del progetto
```

### Classi Principali

1. **Campo.java**:
   - Gestisce la creazione e la rappresentazione del campo di gioco. Il campo è composto da un insieme di oggetti `Casella`, ognuno dei quali può contenere o meno una mina.
   
2. **CampoMinato.java**:
   - Contiene la logica principale del gioco, come le azioni del giocatore, il controllo delle vittorie e delle sconfitte, e le interazioni con il campo.

3. **Casella.java**:
   - Definisce una singola casella del campo. Ogni casella può essere scoperta o coperta, e può contenere una mina o essere vuota. Gestisce inoltre il conteggio delle mine nelle vicinanze.

4. **Gioco.java**:
   - Classe principale che avvia il gioco e gestisce l'interfaccia con l'utente. 

## Come Eseguire il Progetto

### Prerequisiti

- **JDK (Java Development Kit)**: È necessario installare il JDK per compilare ed eseguire il progetto. Assicurati di avere almeno la versione 8 di Java installata.
- **IDE (facoltativo)**: Consigliato l'uso di un ambiente di sviluppo integrato come **Eclipse** o **IntelliJ IDEA**, ma il progetto può essere eseguito anche da riga di comando.

### Compilazione ed Esecuzione

1. **Compilazione da Terminale**:
   - Naviga nella directory del progetto:
     ```bash
     cd /percorso/del/progetto
     ```
   - Compila tutti i file Java presenti nella directory `src`:
     ```bash
     javac -d bin src/*.java
     ```
   - Esegui il gioco:
     ```bash
     java -cp bin CampoMinato
     ```

2. **Esecuzione da IDE**:
   - Importa il progetto nel tuo IDE.
   - Compila ed esegui il file `CampoMinato.java` come classe principale.

## Funzionalità del Gioco

- **Generazione di un campo minato**: All'inizio della partita, viene generato un campo di dimensioni predefinite con un numero di mine sparse casualmente.
- **Scelta delle caselle**: Il giocatore può selezionare una casella alla volta. Se la casella contiene una mina, il gioco termina.
- **Scoperta delle caselle**: Se una casella non contiene una mina, viene scoperta e può rivelare il numero di mine presenti nelle caselle adiacenti.
- **Vittoria**: Il giocatore vince quando tutte le caselle senza mine sono state scoperte.
- **Perdita**: Il giocatore perde se seleziona una casella contenente una mina.

## Screenshot

![Campo Minato](Mina.png)

## Autori

- **Mattia** - Sviluppatore principale.

## Licenza

Questo progetto è realizzato per scopi educativi e non è soggetto a nessuna licenza.