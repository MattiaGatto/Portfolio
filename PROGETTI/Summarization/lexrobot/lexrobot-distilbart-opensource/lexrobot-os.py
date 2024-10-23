# We install the Simple Transformers library to use Transformer models in a simple way
import pip

def import_or_install(package):
    try:
        __import__(package)
    except ImportError:
        pip.main(['install', package])  

import_or_install("transformers")
import_or_install("translators")
import_or_install("openai")
import_or_install("nltk")
import_or_install("torch")
import_or_install("torchvision")
import_or_install("torchaudio")

import os
import nltk
import torch
import random
random.seed(42)
from transformers import pipeline, AutoTokenizer,AutoModelForCausalLM, AutoModelWithLMHead
import textwrap
from pprint import pprint
import translators as ts
import warnings
warnings.simplefilter("ignore")

device = torch.device("cuda")

path_data="data/"

compuying_print=["Computing: [........................................] 0/5",
"Computing: [#####...................................] 1/5",
"Computing: [##########..............................] 2/5",
"Computing: [################........................] 3/5",
"Computing: [##########################..............] 4/5",
"Computing: [########################################] 5/5"]

print(compuying_print[0])
with open(path_data+"exception/"+"otherStopwords.txt", "r") as tfo:
    otherstopwords = tfo.read().split('\n')
otherstopwords=set(list(x.lower() for x in otherstopwords))
otherstopwords=set(otherstopwords.union(['&','|','^',';','\s','\n','\t']))
with open(path_data+"exception/"+"codifiche_accenti.txt", "r") as tfo:
    codifiche_accenti = tfo.read().split('\n')
codifiche_accenti=set(list(x.lower() for x in codifiche_accenti))


for _,_,files in os.walk(path_data+"exception/"):
    for file in files:
        if not file in ['codifiche_accenti.txt','otherStopwords.txt']: 
            print(f"Ci troviamo nella cartella: '{file}'")
            with open(path_data+"exception/"+file, "r") as tfo:
                otherstopwords_plus = tfo.read().split('\n')
            otherstopwords=set(list(otherstopwords+otherstopwords_plus))
print(compuying_print[1])

# stopwords list
eccezioni=set(codifiche_accenti.union(otherstopwords))

# text wrapping function
def wrap(x):
  return textwrap.fill(x, replace_whitespace = False, fix_sentence_endings = True)

def preprocessing_single_text(t):
    #LOWER_CASE
    testo= str(t).lower()
    # tokenization
    word_tokens = nltk.word_tokenize(testo)
    
    filtered_sentence= [w for w in word_tokens if (not w in eccezioni)]
    document = ' '.join(filtered_sentence)
    return document

# Test value prediction
testo=input("Inserisci il FactAndLaw sul quale effettuare la summarizzation:")
print(compuying_print[2])
#esempio
#testo="ricorso epigrafe chiede rsquo ottemperanza giudicato decreto corte d rsquo appello perugia n. 666 5.3.2018 ministero rsquo economia finanze egrave stato condannato pagare favore sig giuseppe nevi titolo indennizzo ex lege 89/2001 somma euro 1.250,00 oltre interessi legali domanda saldo unitamente spese lite pari euro 450,00 oltre iva accessori legge rifondere difensore antistatario avv laura crucianelli anch rsquo ella tal titolo ricorrente chiedono altres igrave ricorrenti caso ulteriore ritardo pagamento somme egrave causa rsquo indennit agrave mora rsquo art 114 comma 4 lett codice processo amministrativo rsquo amministrazione egrave costituita giudizio rilevando cessazione materia contendere ragione rsquo avvenuto pagamento somme argomento camera consiglio giorno 12 ottobre 2001 causa egrave stata trattenuta decisione ograve premesso deve osservarsi ministero intimato provveduto pagamento dovuto ordinativi atti causa resta pertanto dichiarare cessazione materia contendere sensi rsquo art 34 comma 5 codice processo amministrativo ragione mancanza osservazioni senso contrario parte ricorrente va disposta condanna spese lite ministero rsquo economia finanze secondo criterio soccombenza ldquo virtuale rdquo risultando comunque pagamento intervenuto successivamente notifica presente ricorso"
print()
print("Il testo inserito è il seguente:")
print(wrap(testo))
print()
with open(path_data+"/InputScript-os.txt", "w") as text_file:
    text_file.write(wrap(testo))
testo=preprocessing_single_text(testo)
print(compuying_print[3])

def translate(text,from_lang='it',to_lang="en"):
    segments=split_text_in_periods(text,500)
    summary_final=[]
    for text in segments:
        translation = ts.translate_text(text,from_language=from_lang,to_language=to_lang)
        summary_final.append(translation)
    return ''.join(summary_final),segments

def split_text_in_periods(text, max_length=512):
    # Lista per mantenere i segmenti divisi
    segments = []
    current_segment = ''
    current_length = 0
    
    # Dividere il testo in periodi basandosi sul carattere punto
    periods = text.split('.')
    
    for period in periods:
        # Aggiungere uno spazio per il periodo successivo, tranne che per il primo
        if current_length != 0:
            period = ' ' + period
        period_length = len(period)
        
        # Controllare se l'aggiunta del periodo corrente supera il limite di lunghezza
        if current_length + period_length <= max_length:
            current_segment += period + '.'
            current_length += period_length + 1  # Aggiunge 1 per il punto
        else:
            # Se il segmento attuale è vuoto ma il periodo è troppo lungo, lo spezziamo
            if not current_segment and period_length > max_length:
                part_length = max_length - 1  # -1 per lasciare spazio al punto
                while period:
                    segment_part = period[:part_length] + '.'
                    segments.append(segment_part)
                    period = period[part_length:].strip()
            else:
                # Salvare il segmento corrente e iniziare un nuovo segmento
                segments.append(current_segment)
                current_segment = period + '.'
                current_length = period_length + 1
    
    # Assicurarsi di aggiungere l'ultimo segmento se non è vuoto
    if current_segment:
        segments.append(current_segment)
    return segments

summarizer = pipeline('summarization',model='sshleifer/distilbart-cnn-12-6')
MAX_LENGTH=512#3000
def generete_summary_distilbart(text,summarizer=summarizer):
    try:
        traslation,_=translate(text,from_lang='it',to_lang="en")
        text=traslation
    except:
        print()
    segments=split_text_in_periods(text)
    max_length=MAX_LENGTH//8
    min_length=(max_length)//16
    summary_final=[]
    for text in segments:
        sequence = text
        summarized = summarizer(sequence, min_length=min_length, max_length=max_length)
        summary_final.append(str(summarized[0]['summary_text']))
    traslation=''.join(summary_final)
    try:
        traslation,_=translate(traslation,from_lang='en',to_lang="it")
    except:
        print()
    try:
        traslation,_=translate(traslation,from_lang='en',to_lang="it")
    except:
        print()
    return traslation,segments

print(compuying_print[4])
summary,segments=generete_summary_distilbart(testo)
print(compuying_print[5])
with open(path_data+"/Output-os.txt", "w") as text_file:
    text_file.write(wrap(summary))
print("\n\nRisultato salvato in:  "+path_data+"Output-os.txt")
print("Ecco il risultato dell'elaborazione:")
print(wrap(summary))