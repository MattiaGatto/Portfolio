# We install the Simple Transformers library to use Transformer models in a simple way
import pandas as pd
import csv
import os
from pathlib import Path

import pickle
import sklearn
from sklearn.feature_extraction.text import TfidfTransformer
from sklearn.feature_extraction.text import TfidfTransformer
from sklearn.feature_extraction.text import TfidfVectorizer
import string

class_names_list={
            0: "Accepted",1:"Rejected",2:"Improcedibile",3:"Inammissibile",4:"Mixed",5:"NotComputed"
        }

print("RandomForestClassifier_moc_clf_Text_agg")
if os.path.isfile("RandomForestClassifier_moc_clf_Text_agg"):
  with open("RandomForestClassifier_moc_clf_Text_agg", "rb") as f:           # riapre il file in lettura ...
    clf = pickle.load(f)  # ... carica il record ...
    multi_classifier = clf
    print("MODELLO GIA' ADDESTRATO")
    f.close()
print("Inserisci il FactAndLaw da predirre:")
testo=input()

t=pd.core.series.Series(testo)
print("tfidf.pickle")
if os.path.isfile("tfidf.pickle"):
  with open("tfidf.pickle", "rb") as f:           # riapre il file in lettura ...
    tfizer_l=pickle.load(f)                                  # ... carica il record ...
    f.close()

# Test value prediction
test_text = tfizer_l.transform(t)
prediction = multi_classifier.predict_proba(test_text)
print(t)
# val_test=0
# print(f"Real value: {class_names_list[int(val_test)]}")
print(f"Predicted value: \n\t-{class_names_list[int(0)]}-> {(prediction[0][0])} \n\t-{class_names_list[int(1)]}-> {(prediction[0][1])} \n\t-{class_names_list[int(2)]}-> {(prediction[0][2])} \n\t-{class_names_list[int(3)]}-> {(prediction[0][3])}")