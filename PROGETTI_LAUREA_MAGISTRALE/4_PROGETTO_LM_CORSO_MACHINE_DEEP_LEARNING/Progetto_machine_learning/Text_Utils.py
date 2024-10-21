import numpy as np
import os
os.environ['TF_CPP_MIN_LOG_LEVEL'] = '3';
from sklearn.model_selection import train_test_split
import sklearn.base
import sklearn.preprocessing
import string
import sklearn;
import sklearn.feature_extraction;
import sklearn.feature_extraction.text;
import nltk
import matplotlib.pyplot as plt
import pandas as pd
from nltk.corpus import stopwords
from nltk.stem import PorterStemmer
import scipy

def transform_DS(ds):
    New_Testo = []
    for i in range(len(ds)):
        frase = str(ds[i])
        # tokenization
        word_tokens = nltk.word_tokenize(frase);

        # Rimuovi punteggiatura
        word_tokens = list(filter(lambda token: token not in string.punctuation, word_tokens))

        # stopwords list
        stop_words = set(stopwords.words('english'))
        # stoppping
        filtered_sentence = [w for w in word_tokens if not w in stop_words]

        # stemmer
        ps = PorterStemmer()
        # stemming
        stemmed_sentence = [ps.stem(w) for w in filtered_sentence]

        document = ' '.join(stemmed_sentence)
        # sostituisci risultato:
        New_Testo.append(document)
    return New_Testo

def plotta_label_text(l):
    # creating the dataset
    class_names = ['1/2', '3', '4/5'];
    data = {class_names[0]: len(np.where(l==0)[0]), class_names[1]: len(np.where(l==1)[0]),class_names[2]: len(np.where(l==2)[0])}
    label = list(data.keys())
    values = list(data.values())
    # creating the bar plot
    plt.bar(label, values, width=0.4,color='red')
    plt.xlabel("label")
    plt.ylabel("size")
    plt.title("Numero esempi, min= "+str(min(values))+", max= "+str(max(values)))
    plt.show()

def transform_Label(label):
    New_Testo=label.copy()
    for i in range (len(label)):
        if(label[i]==4 or label[i]==5):
             New_Testo[i]=2
        if(label[i]==1 or label[i]==2):
             New_Testo[i]=0
        if(label[i]==3):
            New_Testo[i]=1
    plotta_label_text(np.array(New_Testo))
    return New_Testo

def create_dataset_Text(path):
    #path='testi-3.xlsx'
    df = pd.read_excel(path)
    Testo = df['reviewText'].tolist()
    label = df['overall'].tolist()
    print('N°recensioni->',len(Testo))
    print("Prima label:\n",label[0])
    print("Prima recensione:\n",Testo[0],'\n')
    ds_transform = transform_DS(Testo)
    vectorizer = sklearn.feature_extraction.text.TfidfVectorizer( stop_words=stopwords.words('english'))
    vectors = vectorizer.fit_transform(ds_transform)#.toarray()

    y_label=transform_Label(label)

    #prima di fare questo devo fare i processi di Tokenizzazione,lemmatizzazione,rimozione delle stop words
    Y=np.array(y_label,dtype=float)
    # X=vectors.todense()

    return vectors,Y

def create_dataset_Text_ridotto(path):
    # path='testi-3.xlsx'
    df = pd.read_excel(path)
    Testo = df['reviewText'].tolist()
    label = df['overall'].tolist()
    print('N°recensioni->', len(Testo))
    print("Prima label:\n", label[0])
    print("Prima recensione:\n", Testo[0], '\n')
    ds_transform = transform_DS(Testo)
    vectorizer = sklearn.feature_extraction.text.TfidfVectorizer(max_df=0.0003, stop_words=stopwords.words('english'))
    vectors = vectorizer.fit_transform(ds_transform)

    y_label=transform_Label(label)

    #prima di fare questo devo fare i processi di Tokenizzazione,lemmatizzazione,rimozione delle stop words
    Y=np.array(y_label,dtype=float)
    # X=vectors.todense()

    return vectors,Y

def dataset_Text(dig,X,Y):
    x_train=X
    y_train=Y
    I_dig = np.where(y_train == dig)[0]
    x_dig = x_train[I_dig]
    y_dig = [0] * x_dig.shape[0] # 0 valore inlier
    x_training = x_dig
    for i in range(5):
        id_anomaly = np.array([])
        if (i != dig): #se non è il valore di inlier
            jj = np.where(y_train == i)[0]
            xjj = x_train[jj]
            id_anomaly = np.concatenate((id_anomaly,jj))
            x_training = scipy.sparse.vstack((x_training, xjj))
    y_nodig = [1] *((len(y_train))-(len(np.where(y_train == dig)[0])))
    y_training = np.concatenate((y_dig, y_nodig))
    id_anomaly = id_anomaly.astype(int)
    return x_training.todense(),y_training,x_dig, id_anomaly

def plotta_label_anomaly_text(anomaly):
    # creating the dataset
    class_names = ['inlier', 'anomaly'];
    data = {class_names[0]: len(np.where(anomaly==0)[0]), class_names[1]: len(np.where(anomaly==1)[0])}
    label = list(data.keys())
    values = list(data.values())
    # creating the bar plot
    plt.bar(label, values, width=0.4,color='red')
    plt.xlabel("label")
    plt.ylabel("size")
    plt.title("Numero esempi, min= "+str(min(values))+", max= "+str(max(values)))
    plt.show()

def anomaly_data_text(path):
    vectors,Y=create_dataset_Text(path)
    X=vectors.todense()

    class_names =np.unique(Y)
    print('\nLe classi di label sono: ',class_names)
    class_names_count = [ 0,0,0];
    for i in range (len(Y)):
        if(Y[i]==0.):class_names_count[0]=class_names_count[0]+1
        if(Y[i]==1.):class_names_count[1]=class_names_count[1]+1
        if(Y[i]==2.):class_names_count[2]=class_names_count[2]+1
    print('Conteggio elementi, per determinare il valore inlier: ', class_names_count)

    pos=0
    m=class_names_count[0]
    I=0
    while (pos < len(class_names_count)) :
        if class_names_count[pos] > m :
            m = class_names_count[pos]
            I=pos
        pos = pos + 1
    print ('Il valore inlier è: ',class_names[I], 'con ',m,' elementi.')
    inlier_val = class_names[I]
    print(f'il valore da considerare inlier è {class_names[I]}')
    x_training, y_training,x_dig, id_anomaly = dataset_Text(inlier_val,X,Y)
    plotta_label_anomaly_text(y_training)
    return x_training, y_training,x_dig, id_anomaly