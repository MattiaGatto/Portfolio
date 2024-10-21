import os
os.environ['TF_CPP_MIN_LOG_LEVEL'] = '3';
import pickle
from Utils import *
from sklearn import metrics
from sklearn.multiclass import OneVsRestClassifier
from sklearn.model_selection import KFold
import numpy as np
from sklearn.model_selection import cross_val_score
from sklearn.neighbors import KNeighborsClassifier

def run_KNN_I(path='immagini-3'):
    print("_____Start preprocessing_____\n")
    X_train_2D, X_test_2D, X_val_2D, y_train_2D, y_test_2D, y_val_2D = set_dati_2D(path)
    print("\n_____End preprocessing_____\n")
    print("_____Test accuracy_____\n")
    lista=[5,10,20,50]
    ris=[]
    for ne in lista:
        f="1_immagini/classificazione/Stime_di_densità/Nearest_Neighbor/Knn_clf_ovr"+str(ne)
        if os.path.isfile(f):
            knn_clf = pickle.load(open(f, 'rb'))
            ovr=knn_clf
        else:
            knn_clf=KNeighborsClassifier( n_neighbors = ne);
            ovr = OneVsRestClassifier(knn_clf)
            ovr.fit(X_train_2D,y_train_2D);
        #     knn_clf.fit(X_train,y_train)
            pickle.dump(ovr, open(f, 'wb'))
        y_pred=ovr.predict(X_test_2D); #etichette predette
        print(y_pred)
        print("Accuracy:",ne,metrics.accuracy_score(y_test_2D, y_pred))
        ris.append(metrics.accuracy_score(y_test_2D, y_pred))
    plotta_test(ris,lista)

    print("\n_____KFold accuracy con k=10_____\n")
    ris_score=[]
    lista=[5,10,20,50]
    L=0
    for ne in lista:
        f="1_immagini/classificazione/Stime_di_densità/Nearest_Neighbor/scores_KNN_clf"+str(ne)
        # evaluate model
        if os.path.isfile(f):
            scores= pickle.load(open(f, 'rb'))
        else:
            # prepare the cross-validation procedure
            cv = KFold(n_splits=10, random_state=1, shuffle=True)
            clf = KNeighborsClassifier(n_neighbors=ne);
            modelovr = OneVsRestClassifier(clf)
            scores=cross_val_score(modelovr,X_train_2D,y_train_2D, scoring='accuracy', cv=cv, n_jobs=-1);
            pickle.dump(scores, open(f, 'wb'))
        # report performance
        print('Accuracy ' + str(ne) + ': %.3f (%.3f)' % (np.mean(scores), np.std(scores)))
        ris_score.append(np.mean(scores))
        plotta(scores, ris[L], ' con parametro=' + str(ne))
        L = L + 1

    plotta_confronti(ris_score, ris, lista)