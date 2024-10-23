# Segment SAM App

Questo progetto contiene un servizio Flask per la segmentazione di immagini, basato su SAM (Segment Anything Model). Il pacchetto include diversi file e cartelle che permettono l'avvio e l'uso del servizio, oltre a file di esempio per facilitare il testing.

## Contenuto del pacchetto

Il pacchetto ZIP `DEPLOY.ZIP` contiene:

### 1. `segment_sam_app/`
   - **`image/`**: cartella con immagini di esempio.
   - **`model/`**: contiene i modelli utilizzati dal servizio.
   - **`results_json/`**: cartella dove vengono salvati i file JSON e GeoJSON generati dal servizio.
   - **`temp/`**: cartella dove caricare l'immagine da processare.
   - **`app.py`**: script principale del servizio Flask.
   - **`requirements.txt`**: elenco delle dipendenze e librerie necessarie per eseguire il servizio.

### 2. `image/`
   - Cartella aggiuntiva con ulteriori immagini di esempio.

### 3. `results_json/`
   - Cartella che conterrà i file JSON e GeoJSON generati dal servizio.

### 4. `esempio_esecuzione.png`
   - Screenshot del terminale con i comandi per avviare il servizio Flask.

### 5. `script_cmd.txt`
   - File di testo contenente i comandi utilizzati per avviare il servizio, corrispondenti all'immagine `esempio_esecuzione.png`.

### 6. `esempio_richiesta_postman.png`
   - Screenshot di una richiesta HTTP su Postman, impostata come segue:
     - **Richiesta POST a**: `http://127.0.0.1:5000/segment`
     - **Form-data**:
       - **Key**: `file`
       - **file_type**: Text
       - **Value**: `photo_2024-09-20_16-00-22.jpg` (questo file è un esempio caricato in `segment_sam_app/temp`).

### 7. `test_sam_model.ipynb`
   - Notebook Jupyter utilizzato per lo sviluppo, con metodi aggiuntivi utili per l'analisi.

### 8. `test_sam_model.html`
   - Export del notebook con i relativi output.

## Esecuzione del servizio

1. Posizionarsi nella directory `segment_sam_app/`.
2. Installare le dipendenze elencate in `requirements.txt`:
   ```bash
   pip install -r requirements.txt
   ```
3. Avviare il servizio Flask:
   ```bash
   python app.py
   ```

## Test con Postman

1. Effettuare una richiesta POST al servizio:
   - **URL**: `http://127.0.0.1:5000/segment`
   - **Form-data**:
     - **Key**: `file`
     - **Value**: caricare un'immagine, ad esempio `photo_2024-09-20_16-00-22.jpg`.

## Contatti

Per ulteriori informazioni o richieste, non esitare a contattarmi.
