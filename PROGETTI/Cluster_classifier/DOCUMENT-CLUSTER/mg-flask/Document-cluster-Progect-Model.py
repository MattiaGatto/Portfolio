# We install the Simple Transformers library to use Transformer models in a simple way
import pandas as pd
import os
import pickle
import string
from sklearn.decomposition import PCA
from sklearn.metrics.pairwise import cosine_similarity
import warnings
import nltk
import pickle
import re
nltk.download('names')
nltk.download('stopwords')
nltk.download('wordnet')
nltk.download('punkt')
nltk.download('averaged_perceptron_tagger')
from nltk.corpus import names
from nltk.corpus import stopwords
from nltk.stem.snowball import SnowballStemmer

warnings.simplefilter("ignore")

path_model="model/"
path_data="data/"

compuying_print=["Computing: [........................................] 0/7",
"Computing: [#####...................................] 1/7",
"Computing: [##########..............................] 2/7",
"Computing: [################........................] 3/7",
"Computing: [#####################...................] 4/7",
"Computing: [##########################..............] 5/7",
"Computing: [#################################.......] 6/7",
"Computing: [########################################] 7/7"]

print(compuying_print[0])
n_cluster=4

f = path_model+"k-means/"+"K_means_"+ str(n_cluster)
if os.path.isfile(f):
  with open(f, "rb") as f:                                   # riapre il file in lettura ...
    kmeans= pickle.load(f)                                   # ... carica il record ...
    clusters = kmeans.labels_

    print(compuying_print[1])
    f.close()

if os.path.isfile(path_model+"tfidf.pickle"):
  with open(path_model+"tfidf.pickle", "rb") as f:           # riapre il file in lettura ...
    tfizer=pickle.load(f)                                    # ... carica il record ...
    print(compuying_print[2])
    f.close()

# inizializziamo la PCA con 2 componenti
pca = PCA(n_components=2, random_state=42)
if os.path.isfile(path_model+"pca.pickle"):
  with open(path_model+"pca.pickle", "rb") as f:             # riapre il file in lettura ...
    pca_vecs=pickle.load(f)                                  # ... carica il record ...
    print(compuying_print[3])
    f.close()

n_cluster_key=10
if os.path.isfile(path_model+"cluster_map.pickle"+str(n_cluster_key)):
  with open(path_model+"cluster_map.pickle"+str(n_cluster_key), "rb") as f:           # riapre il file in lettura ...
    cluster_map=pickle.load(f)                                                        # ... carica il record ...
    print(compuying_print[4])
    f.close()

df_sentence = pd.read_csv(path_data+"outputfile_sentence_processed.csv")
df_sentence = df_sentence.rename(columns={'FactAndLaw': 'text'})
if os.path.isfile(path_model+"X_tfidf.pickle"):
  with open(path_model+"X_tfidf.pickle", "rb") as f:           # riapre il file in lettura ...
    X_tfidf=pickle.load(f)                                     # ... carica il record ...
    print(compuying_print[5])
    f.close()
else:
  with open(path_model+"X_tfidf.pickle", "wb") as f:           # riapre il file in lettura ...
    X_tfidf=tfizer.transform(df_sentence['text'])              # ... salva il record ...
    pickle.dump(X_tfidf,f)
    print(compuying_print[5])
    f.close()

with open(path_data+"exception/"+"comuni.txt", "r") as tf:
    comuni = tf.read().split('\n')
comuni=set(list(x.lower() for x in comuni))
with open(path_data+"exception/"+"otherStopwords.txt", "r") as tfo:
    otherstopwords = tfo.read().split('\n')
otherstopwords=set(list(x.lower() for x in otherstopwords))
with open(path_data+"exception/"+"avverbi_italiani.txt", "r") as tfo:
    avverbi_italiani = tfo.read().split('\n')
avverbi_italiani=set(list(x.lower() for x in avverbi_italiani))
with open(path_data+"exception/"+"acronimi_.txt", "r") as tfo:
    acronimi = tfo.read().split('\n')
acronimi=list(x.lower() for x in acronimi)
acronimi=set(list(''.join(filter(lambda x: x not in ['.',' ',',','!','?','#','\\','/','^','\'','\s','\n','\t'], w)) for w in acronimi))
with open(path_data+"exception/"+"codifiche_accenti.txt", "r") as tfo:
    codifiche_accenti = tfo.read().split('\n')
codifiche_accenti=set(list(x.lower() for x in codifiche_accenti))

for _,_,files in os.walk(path_data+"exception/"):
    for file in files:
        if not file in ['acronimi_.txt','avverbi_italiani.txt','codifiche_accenti.txt','comuni.txt','otherStopwords.txt']: 
            print(f"Ci troviamo nella cartella: '{file}'")
            with open(path_data+"exception/"+file, "r") as tfo:
                otherstopwords_plus = tfo.read().split('\n')
            otherstopwords=set(list(otherstopwords+otherstopwords_plus))

# stopwords list
stop_words = set(stopwords.words('italian'))
stemmer = SnowballStemmer("italian")
nomi_person= set(w.lower() for w in names.words('male.txt')+names.words('female.txt'))

eccezioni=set(comuni.union(stop_words.union(codifiche_accenti.union(otherstopwords.union(nomi_person.union(avverbi_italiani.union(acronimi)))))))


def preprocessing_single_text(t):
    #LOWER_CASE
    testo= str(t).lower()

    # tokenization
    word_tokens = nltk.word_tokenize(testo);

    # Rimuovi punteggiatura
    word_tokens = list(filter(lambda token: token not in string.punctuation, word_tokens))

    # stoppping and stemming
    filtered_sentence = [re.sub(r'[0-9]+', '',stemmer.stem(''.join(filter(lambda x: x not in ['.',' ',',','-','!','?','#','\\','/','^','\'','\s','\n','\t'], w)))) 
                        for w in word_tokens if (not w in eccezioni) 
                        and (len(stemmer.stem(w))>3) ]
    filtered_sentence= [w for w in filtered_sentence if (not w in eccezioni) and (len(w)>3)]

    document = ' '.join(filtered_sentence)

    return document

pca_vecs_D = pca_vecs.transform(X_tfidf.toarray())
# salviamo le nostre due dimensioni in x0 e x1
x0 = pca_vecs_D[:, 0]
x1 = pca_vecs_D[:, 1]
df_sentence['cluster'] = kmeans.predict(X_tfidf)
df_sentence['cluster'] = df_sentence['cluster'].map(cluster_map)
df_sentence['x0'] = x0
df_sentence['x1'] = x1

# Test value prediction
testo=input("Inserisci il FactAndLaw da verificare:")
#esempio
#testo="ricorso epigrafe chiede rsquo ottemperanza giudicato decreto corte d rsquo appello perugia n. 666 5.3.2018 ministero rsquo economia finanze egrave stato condannato pagare favore sig giuseppe nevi titolo indennizzo ex lege 89/2001 somma euro 1.250,00 oltre interessi legali domanda saldo unitamente spese lite pari euro 450,00 oltre iva accessori legge rifondere difensore antistatario avv laura crucianelli anch rsquo ella tal titolo ricorrente chiedono altres igrave ricorrenti caso ulteriore ritardo pagamento somme egrave causa rsquo indennit agrave mora rsquo art 114 comma 4 lett codice processo amministrativo rsquo amministrazione egrave costituita giudizio rilevando cessazione materia contendere ragione rsquo avvenuto pagamento somme argomento camera consiglio giorno 12 ottobre 2001 causa egrave stata trattenuta decisione ograve premesso deve osservarsi ministero intimato provveduto pagamento dovuto ordinativi atti causa resta pertanto dichiarare cessazione materia contendere sensi rsquo art 34 comma 5 codice processo amministrativo ragione mancanza osservazioni senso contrario parte ricorrente va disposta condanna spese lite ministero rsquo economia finanze secondo criterio soccombenza ldquo virtuale rdquo risultando comunque pagamento intervenuto successivamente notifica presente ricorso"
print(testo)

X_predict=pd.DataFrame({'text':[preprocessing_single_text(testo)]})
X_tfidf_predict = tfizer.transform(X_predict['text'])
if os.path.isfile(path_model+"pca_vecs.pickle"):
  with open(path_model+"pca_vecs.pickle", "rb") as f:                          # riapre il file in lettura ...
    pca_vecs_pred=pickle.load(f)                                               # ... carica il record ...
    print(compuying_print[6])
    f.close()
else:
  with open(path_model+"pca_vecs.pickle", "wb") as f:                          # riapre il file in lettura ...
    pca_vecs_pred = pca_vecs.transform(X_tfidf_predict.toarray())              # ... salva il record ...
    pickle.dump(pca_vecs_pred,f)
    print(compuying_print[6])
    f.close()

# salviamo le nostre due dimensioni in x0 e x1
x0_pred = pca_vecs_pred[:, 0]
x1_pred = pca_vecs_pred[:, 1]
X_predict['cluster'] = kmeans.predict(X_tfidf_predict)
X_predict['cluster'] = X_predict['cluster'].map(cluster_map)
X_predict['x0'] = x0_pred
X_predict['x1'] = x1_pred
print(compuying_print[7])
print(X_predict)