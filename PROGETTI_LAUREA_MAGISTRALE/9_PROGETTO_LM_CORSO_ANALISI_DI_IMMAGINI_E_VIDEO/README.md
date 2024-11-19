# README

## **progetto di analisi immagini e video** 
Progetto di Simonpaolo Lopez e Mattia Gatto presso l'Università della Calabria, Dipartimento DIMES. L'obiettivo del progetto è classificare i **trailer** video a partire dai **keyframe** estrapolati, in un problema di **multi-classificazione** con 85 etichette possibili.

### Dettagli del dataset:
- **Training set**: 4292 trailer
- **Test set**: 1112 trailer
- Il dataset complessivo conta **5404 trailer** con un numero di frame variabile tra 3 e 80, e una media di circa 20 frame per trailer.

### Data analysis:
- Si è notato un forte **sbilanciamento** delle etichette.
- La media dei colori presenti nei frame è stata utilizzata per sostituire i frame non rilevanti, ad esempio i frame neri.

### Preprocessing:
- Diverse tecniche di **data augmentation** sono state sperimentate senza risultati significativi. Solo il **center crop** a 224x224 pixel e la trasformazione in tensore sono state mantenute.
  
### Architettura utilizzata:
- **VGG16 pre-addestrata**, con l'output completamente connesso e classificazione tramite una **rete densa**.
- L'ottimizzatore **Adam** con learning rate di 0.001 è stato utilizzato con la funzione di perdita **BCELoss**.
  
### Tentativi e valutazioni:
- Sono state provate diverse **funzioni di perdita**: BCEwithLogitsLoss, PoissonNLLLoss e CrossEntropyLoss.
- Sono stati inizializzati pesi proporzionali alle occorrenze delle etichette con diverse formule, e il miglior risultato è stato ottenuto alla **26ª epoca** con un **f1-score micro-avg** di 0.27 e 36 classi etichettate con f1-score > 0.

### Altre tecniche:
- Sono stati utilizzati altre architetture come **VGG19**, **VGG11** e **ResNet50**, ma con risultati inferiori rispetto a VGG16.
- È stato anche provato l'uso di un **autoencoder** per ricostruire i frame e identificare più feature, alternando dataset etichettati e non etichettati nelle fasi di addestramento. Tuttavia, i risultati in termini di valutazione non sono stati sufficientemente soddisfacenti. 
