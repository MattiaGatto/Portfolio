import os
os.environ['TF_CPP_MIN_LOG_LEVEL'] = '3';
import pickle
from Utils import *
from sklearn import metrics
from sklearn.multiclass import OneVsRestClassifier
from sklearn.model_selection import KFold
import numpy as np
from sklearn.svm import SVC
from sklearn.model_selection import cross_val_score

def run_SVM_I(path='immagini-3'):
    print("_____Start preprocessing_____\n")
    X_train_2D, X_test_2D, X_val_2D, y_train_2D, y_test_2D, y_val_2D = set_dati_2D(path)
    print("\n_____End preprocessing_____\n")
    print("_____Test accuracy_____\n")
    deg_max = 6
    train_err = [0] * deg_max
    valid_err = [0] * deg_max

    f1 = "1_immagini/classificazione/SVM/SVM_ovr_clf_train_err"
    f2 = "1_immagini/classificazione/SVM/SVM_ovr_clf_valid_err"
    bool = 0
    if os.path.isfile(f1):
        train_err = pickle.load(open(f1, 'rb'))
        valid_err = pickle.load(open(f1, 'rb'))
        bool = 1
    else:
        bool = 0

    # SVM con kernel polinomiale con grado crescente
    ris = []
    for i in range(deg_max):
        f = "1_immagini/classificazione/SVM/SVM_ovr_clf" + str(i + 1)
        if os.path.isfile(f):
            clf = pickle.load(open(f, 'rb'))
            ovr = clf
        else:
            clf = SVC(kernel='poly', degree=i + 1, gamma='auto', C=1)
            ovr = OneVsRestClassifier(clf)
            ovr.fit(X_train_2D, y_train_2D);
            pickle.dump(ovr, open(f, 'wb'))

        y_pred = ovr.predict(X_test_2D);  # etichette predette
        print(y_pred)
        print("Accuracy:", i + 1, metrics.accuracy_score(y_test_2D, y_pred))
        ris.append(metrics.accuracy_score(y_test_2D, y_pred))
        if (bool == 0):
            train_err[i] = 1 - ovr.score(X_train_2D, y_train_2D)
            valid_err[i] = 1 - ovr.score(X_val_2D, y_val_2D)

    pickle.dump(train_err, open(f1, 'wb'))
    pickle.dump(valid_err, open(f2, 'wb'))

    # Grafico degli errori al variare del grado del kernel
    degs = np.cumsum([1] * deg_max)
    #plt.figure()
    plt.plot(degs, train_err, degs, valid_err)
    plt.legend(['training error', 'validation error'])
    #plt.ion()
    #plt.pause(1.00001)
    plt.show()
    plotta_test(ris, [1, 2, 3, 4, 5, 6])

    print("\n_____KFold accuracy con k=10_____\n")
    ris_score=[]
    lista=[1,2,3,4,5,6]
    L=0
    for ne in lista:
        f="1_immagini/classificazione/SVM/scores_SVM_ovr_clf"+str(ne)
        # evaluate model
        if os.path.isfile(f):
            scores= pickle.load(open(f, 'rb'))
        else:
            # prepare the cross-validation procedure
            cv = KFold(n_splits=10, random_state=1, shuffle=True)
            clf = SVC(kernel='poly', degree=ne, gamma='auto', C=1)
            modelovr = OneVsRestClassifier(clf)
            scores=cross_val_score(modelovr,X_train_2D,y_train_2D, scoring='accuracy', cv=cv, n_jobs=-1);
            pickle.dump(scores, open(f, 'wb'))
        #report performance
        print('Accuracy ' + str(ne) + ': %.3f (%.3f)' % (np.mean(scores), np.std(scores)))
        ris_score.append(np.mean(scores))
        plotta(scores,ris[L],' con grado='+str(ne))
        L=L+1

    plotta_confronti(ris_score,ris,lista)