import warnings
warnings.filterwarnings("ignore")
import cv2
import json
import numpy as np
import matplotlib.pyplot as plt
import geojson
from shapely.geometry import Polygon, mapping

# Aggiungi qui il processo di rimozione delle strisce dai bordi
def apply_morphological_smoothing(image):
    # Dividi l'immagine nei tre canali B, G e R
    channels = cv2.split(image)

    # Definisci un kernel per le operazioni morfologiche
    kernel = np.ones((7,7), np.uint8)

    smoothed_channels = []
    for channel in channels:
        # Applica una chiusura morfologica e dilatazione su ciascun canale
        closing = cv2.morphologyEx(channel, cv2.MORPH_CLOSE, kernel)
        dilated = cv2.dilate(closing, kernel, iterations=1)

        # Applica una sfocatura gaussiana per un effetto di smoothing
        smoothed_channel = cv2.GaussianBlur(dilated, (5, 5), 0)

        smoothed_channels.append(smoothed_channel)

    # Ricombina i canali B, G e R in un'unica immagine
    smoothed_image = cv2.merge(smoothed_channels)

    return smoothed_image

# Supponiamo che 'masks' sia una lista di dizionari contenente le segmentazioni
# Ciascun dizionario ha una chiave 'segmentation' con la maschera come array numpy
def save_masks(masks,SAVE = True,json_path="results_json/contour_mask_data.json"):
    contours_list = []

    # 2. Estrai le coordinate dei pixel dei contorni
    for mask in masks:
        mask_array = mask['segmentation']
        
        # Assicurati che la maschera sia binaria (0 e 255)
        mask_array = mask_array.astype(np.uint8) * 255
        contours, _ = cv2.findContours(mask_array, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
        
        contour_list = []
        for contour in contours:
            contour_pixels = contour[:, 0, :].tolist()  # Estrai solo le coordinate X, Y dei pixel
            contour_list.append(contour_pixels)
        contours_list.append({"contour": contour_list})
    # 3. Crea un dizionario per contenere i dati dei contorni
    masks_data = {
        "contours": contours_list
    }
    # 4. Opzione per salvare i dati in un file JSON
    if SAVE:
        # 5. Salva i dati in un file JSON
        with open(json_path, "w") as json_file:
            json.dump(masks_data, json_file, indent=4)
        
        print(f"\t7. File JSON salvato come {json_path}")
    return masks_data

def plot_geojson_filled_contours(image_path, geojson_path='results_json/output_masks.geojson'):
    # 1. Carica l'immagine originale
    image = cv2.imread(image_path)
    image_original = image.copy()  # Copia per il confronto

    # 2. Carica il file GeoJSON
    with open(geojson_path, 'r') as f:
        geojson_data = json.load(f)
    
    # 3. Crea una nuova immagine dove verranno colorati internamente i contorni (poligoni riempiti)
    image_with_filled_contours = image.copy()

    # 4. Estrai i contorni dai poligoni GeoJSON e colorali internamente
    for feature in geojson_data['features']:
        if feature['geometry']['type'] == 'Polygon':
            for polygon in feature['geometry']['coordinates']:
                # Converti le coordinate del poligono in pixel
                contour = np.array(polygon, dtype=np.int32)

                # Riempie i poligoni con un colore verde
                cv2.fillPoly(image_with_filled_contours, [contour], color=(0, 0, 255))  # Rosso riempito
                # Disegna anche il bordo del poligono (rosso scuro) per differenziarlo
                cv2.polylines(image_with_filled_contours, [contour], isClosed=True, color=(0, 0, 100), thickness=1)
    
    # 5. Converti le immagini BGR in RGB per la visualizzazione con matplotlib
    image_rgb_original = cv2.cvtColor(image_original, cv2.COLOR_BGR2RGB)
    image_rgb_filled_contours = cv2.cvtColor(image_with_filled_contours, cv2.COLOR_BGR2RGB)

    # 6. Visualizza le immagini con i poligoni riempiti sopra e l'immagine originale sotto
    fig, ax = plt.subplots(2, 1, figsize=(30, 30))  # 2 righe, 1 colonna

    # Mostra l'immagine con i contorni colorati internamente
    ax[0].imshow(image_rgb_filled_contours)
    ax[0].axis('off')  # Nasconde gli assi
    ax[0].set_title('Immagine con poligoni riempiti',fontsize = 30)

    # Mostra l'immagine originale per confronto
    ax[1].imshow(image_rgb_original)
    ax[1].axis('off')  # Nasconde gli assi
    ax[1].set_title('Immagine originale',fontsize = 30)

    # Mostra le immagini
    plt.tight_layout()
    plt.show()

def masks_to_geojson(masks, SAVE=True, output_geojson_path="results_json/output_masks.geojson", image_height=None):
    features = []

    # Itera su ogni maschera in masks
    for mask in masks:
        # Estrai l'array di segmentazione dalla maschera
        mask_array = mask['segmentation']
        
        # Assicurati che la maschera sia binaria (0 e 255)
        mask_array = mask_array.astype(np.uint8) * 255
        
        # Trova i contorni della maschera
        contours, _ = cv2.findContours(mask_array, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
        
        # Itera su ogni contorno trovato
        for contour in contours:
            # Inverti le coordinate Y
            contour_list = contour[:, 0, :].tolist()  # Converti le coordinate del contorno in una lista
            if image_height is not None:  # Se l'altezza dell'immagine è fornita
                # Inverti le coordinate Y
                contour_list = [(x, image_height - y) for x, y in contour_list]
                
            if len(contour_list) >= 3:  # I poligoni devono avere almeno 3 punti
                polygon = Polygon(contour_list)
                
                # Crea una feature GeoJSON per il poligono
                feature = geojson.Feature(geometry=mapping(polygon), properties={})
                features.append(feature)

    # Crea la FeatureCollection GeoJSON
    feature_collection = geojson.FeatureCollection(features)

    # Salva il GeoJSON in un file
    if SAVE:
        with open(output_geojson_path, "w") as f:
            json.dump(feature_collection, f, indent=4)

        print(f"\t9. GeoJSON corretto sull'asse y, salvato in: {output_geojson_path}")


file_path_image = "image/photo_2024-09-20_16-00-22.jpg"
def sam_segmentation_image(file_path_image,save=True):

    print("> START:")
    print("\t0. Import librerire necessarie")
    
    
    # Test di lettura di un'immagine
    image = cv2.imread(file_path_image)
    if image is not None:
        print("\t1. Immagine caricata correttamente")
    else:
        print("\t1. Errore nel caricamento dell'immagine")


    #lettura modello e creazione generator_masks
    from segment_anything import sam_model_registry,SamAutomaticMaskGenerator

    # Aprire e leggere un file locale
    model='vit_h'
    file_path = "model/sam_vit_h_4b8939.pth"

    print(f"\t2. Modello '{model}'. Creazione del modello...")
    # Utilizza il contesto "with" per aprire e leggere il file
    import torch
    device = torch.device("cuda" if torch.cuda.is_available() else "cpu")
    # Stampa il dispositivo che verrà utilizzato
    print(f"\t\tUtilizzando: {device}")
    with open(file_path, 'r') as file:
        sam = sam_model_registry[model](checkpoint=file_path)
        sam.to(device=device)

    mask_generator = SamAutomaticMaskGenerator(sam)
    print(f"\t3. Mask Generator. Creazione masks...")
    masks = mask_generator.generate(image)
    print("\t4. Sono stati identificati ",len(masks),"oggetti")




    smoothed_image = apply_morphological_smoothing(image)
    print("\t5. Immagine con contorni levigati.","L'immagine ha la seguente shape:",smoothed_image.shape) # Controlla il tipo di dato

    new_masks = mask_generator.generate(smoothed_image)
    print("\t6. Sono stati identificati ",len(new_masks),"oggetti, a seguito della levigazione dell'immagine.")

    masks=new_masks

    if save:
        save_masks(masks)
        # Specifica il percorso in cui salvare il file GeoJSON
        output_geojson_path = "results_json/output_masks.geojson"
        
        # Specifica il percorso in cui salvare il file GeoJSON
        output_geojson_path = "results_json/output_masks.geojson"
        image_height = image.shape[0]  # Sostituisci con l'altezza reale dell'immagine
        # Esegui la conversione e salva il GeoJSON
        masks_to_geojson(masks, output_geojson_path, image_height=image_height)
    return masks

def sam_segmentation(file_path_image,PLOT=False):
    if PLOT:
        # Esegui la funzione per plottare i contorni colorati e l'immagine originale
        print("> START:")
        plot_geojson_filled_contours(file_path_image)
        return []
    else:
        masks=sam_segmentation_image(file_path_image)
        return masks

# Funzione per la segmentazione dell'immagine
def segmentation(file_path_image="image/photo_2024-09-20_16-00-22.jpg",PLOT=False):

    masks=sam_segmentation(file_path_image,PLOT)

    print("> END")

    return len(masks), 200

from flask import Flask, request, jsonify
app = Flask(__name__)

# Endpoint per eseguire la segmentazione
@app.route('/segment', methods=['POST'])
def segment_image():
    params=dict(request.form)
    print(params)
    if 'file' not in request.form:
        return jsonify({"error": "Nessun file caricato"}), 400
 
    # file = request.files['file'] 
    # file_path = f"temp/{file.filename}"
    # file.save(file_path)
    file = params['file']
    file_path = f"temp/{file}"
    print(file_path)
    num_masks, status = segmentation(file_path)
    if status == 400:
        return jsonify({"error": num_masks}), status

    return jsonify({"message": f"Sono stati identificati {num_masks} oggetti"}), 200

if __name__ == '__main__':
    # app.run(debug=True)
    app.run()
