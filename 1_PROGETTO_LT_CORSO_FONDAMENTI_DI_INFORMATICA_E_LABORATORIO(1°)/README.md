# Trasformazione Immagini

## Descrizione del Progetto

L’homework è basato sull’utilizzo del modulo “cImage” per applicare trasformazioni ad immagini. Il modulo “cImage” è disponibile sul sito dedicato al corso, insieme ad alcuni esempi di utilizzo. Si richiamano di seguito alcune funzioni del modulo utili per la realizzazione dell’homework (si faccia comunque riferimento anche agli esempi forniti sul sito del corso).
- Le funzioni getWidth() e getHeight() applicate ad un'immagine (cImage.Image) ne restituiscono larghezza e altezza, rispettivamente.
- La funzione cImage.EmptyImage(l,a) restituisce una nuova immagine vuota di larghezza l e altezza a.
- La funzione getPixel(j,i) applicata ad un'immagine restituisce il pixel sulla riga i e colonna j.
- La funzione setPixel(j,i,p) applicata ad un'immagine imposta il pixel p sulla riga i e colonna j.
- La funzione getRed() applicata ad un pixel restituisce il valore della componente rosso. I valori sono compresi tra 0 e 255.
- La funzione getGreen() applicata ad un pixel restituisce il valore della componente verde. I valori sono compresi tra 0 e 255.
- La funzione getBlue() applicata ad un pixel restituisce il valore della componente blu. I valori sono compresi tra 0 e 255.
- La funzione cImage.Pixel(r,g,b) restituisce un nuovo pixel con i dati valori di rosso (r), verde (g) e blu (b). I valori sono compresi tra 0 e 255.
- La funzione captureClicks(n) applicata ad una finestra (cImage.ImageWin) cattura n click del mouse su un’immagine e restituisce la lista delle coordinate dei pixel su cui sono stati effettuati i click (si veda in particolare l’esempio “gestione_immagini_cattura_click.py”).

## Struttura del Progetto

```
.
├── homework_modulo_1 Mattia_Gatto_corso_A_Ingegneria_.py    # Implementazione della funzione operazione()
├── cImage.py/               # libreria per manipolare immagini
├── tiger.gif/               # immagine di input
└── README.md               # Questo file
```



## Contributi

Se desideri migliorare o aggiungere altre trasformazioni, sei libero di fare una pull request o aprire una issue per discutere di nuove idee.
