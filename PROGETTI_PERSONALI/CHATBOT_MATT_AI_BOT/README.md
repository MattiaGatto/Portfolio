# MATT_AI_BOT

## Descrizione
MATT_AI_BOT è un chatbot sviluppato in Python utilizzando la libreria ChatterBot e Google Translate. Il bot è progettato per interagire con gli utenti in lingua italiana e inglese, fornendo risposte a domande comuni e generali.

## Funzionalità Principali
- Risponde a domande di base su età, identità e stati d'animo.
- Gestisce conversazioni in due lingue: italiano e inglese, grazie all'integrazione di Google Translate.
- Può eseguire valutazioni matematiche e rispondere in modo intelligente grazie all'uso di adattatori logici.

## Requisiti
Assicurati di avere installato Python 3.x e le seguenti librerie:

- `chatterbot==1.0.4`
- `chatterbot_corpus`
- `googletrans==3.1.0a0`

Puoi installare le librerie richieste utilizzando pip:

```bash
pip install chatterbot==1.0.4
pip install chatterbot_corpus
pip install googletrans==3.1.0a0
```

## Installazione
1. Clona questo repository.
2. Installa le dipendenze con il comando sopra.

## Utilizzo
1. Importa le librerie necessarie nel tuo script.
2. Chiama la funzione `MATT_AI_ChatBot()` per inizializzare il chatbot.
3. Avvia il bot chiamando `MATT_AI_BOT(MATT_AI_bot)`.

### Esempio di Codice

```python
translator = Translator()
my_bot = MATT_AI_ChatBot()
MATT_AI_BOT(my_bot)
```

## Interazione con il ChatBot
- Per avviare una conversazione, basta scrivere un messaggio e premere Invio.
- Per uscire dalla chat, scrivi `esci`.

### Esempi di Domande
- "How old are you?"
- "Who are you?"
- "How are you?"
- "Thank you!"

## Contribuzione
Se desideri contribuire a questo progetto, apri un'issue per discutere le modifiche che intendi apportare. Le pull request sono benvenute.

## Licenza
Questo progetto è sotto la licenza MIT. Consulta il file LICENSE per ulteriori dettagli.

## Autore
Mattia Gatto - Sviluppatore del MATT_AI_BOT.
```

### Note
- Puoi personalizzare ulteriormente le sezioni, aggiungendo dettagli specifici o modificando il testo in base alle tue esigenze.
- Assicurati che il codice e la documentazione siano sempre in linea e aggiornati con eventuali modifiche apportate.