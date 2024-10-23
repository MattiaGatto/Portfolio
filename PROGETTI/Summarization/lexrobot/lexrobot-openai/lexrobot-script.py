import pip

def import_or_install(package):
    try:
        __import__(package)
    except ImportError:
        pip.main(['install', package])  

import_or_install("openai")
import_or_install("nltk")
import_or_install("torch")
import_or_install("torchvision")
import_or_install("torchaudio")
# We install the Simple Transformers library to use Transformer models in a simple way
from openai import OpenAI
import os
import nltk
import torch
import random
random.seed(42)
import textwrap
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
with open(path_data+"/InputScript.txt", "w") as text_file:
    text_file.write(wrap(testo))

testo=preprocessing_single_text(testo)
print(compuying_print[3])
try:

    OPENAI_API_KEY='your_api'
    client = OpenAI(api_key=OPENAI_API_KEY)
except:
    print("Errore: creazione Client OpenAI - OPENAI_API_KEY")
def generate_summary(text):
    input_chunks = text
    completion = client.chat.completions.create(
      model="gpt-3.5-turbo",
      messages=[
        {"role": "system", "content": "Legal Assistant able to analyze the text of the judgment extract its main concepts, identify relevant legal precedents, and provide a detailed summary."},
        # {"role": "user", "content": "Summarize the following article and return the result into Italian:\n"+input_chunks}
        {"role": "user", "content": "Summarize the following article:\n"+input_chunks}
      ]
    )
    output_chunks=completion.choices
    output_gen=output_chunks[0].message
    return output_chunks,output_gen.content

def generate_translation(text,language_from="English",language_to="Italian"):
    input_chunks = text
    completion = client.chat.completions.create(
      model="gpt-3.5-turbo",
      messages=[
        {"role": "user", "content": "Translate the following article written in "+language_from+" into "+language_to+":\n"+input_chunks}
      ]
    )
    output_chunks=completion.choices
    output_gen=output_chunks[0].message
    return output_gen.content

print(compuying_print[4])
english_text=generate_translation(testo,language_from="Italian",language_to="English")
output_chunks,output_gen_summary=generate_summary(english_text)
output_gen=generate_translation(output_gen_summary,language_from="English",language_to="Italian")
print(compuying_print[5])
with open(path_data+"/Output.txt", "w") as text_file:
    text_file.write(wrap(output_gen))
print("\n\nRisultato salvato in:  "+path_data+"Output.txt")
print("Ecco il risultato dell'elaborazione:")
print(wrap(output_gen))