import os
os.environ['TF_CPP_MIN_LOG_LEVEL'] = '3';
import pickle
from Utils import *
import sklearn.ensemble as ske
from sklearn import metrics
from sklearn.multiclass import OneVsRestClassifier
from sklearn.model_selection import KFold
import numpy as np
from sklearn.model_selection import cross_val_score


def run_Adaboost_I(path='immagini-3'):
    print("_____Start preprocessing_____\n")
    X_train_2D, X_test_2D, X_val_2D, y_train_2D, y_test_2D, y_val_2D = set_dati_2D(path)
    print("\n_____End preprocessing_____\n\n")
    print("_____Test accuracy_____\n")
    ris=[]
    lista=[1, 3, 5,10,20,50]
    for ne in lista: #classificatori che sta addestrando
        f="1_immagini/classificazione/AdaBoost/ada_clf_ovr"+str(ne)
        if os.path.isfile(f):
            ada_clf = pickle.load(open(f, 'rb'))
            ovr=ada_clf
        else:
            ada_clf=ske.AdaBoostClassifier( n_estimators = ne, algorithm='SAMME' );
            ovr = OneVsRestClassifier(ada_clf)
            ovr.fit(X_train_2D,y_train_2D);
            pickle.dump(ovr, open(f, 'wb'))
        y_pred=ovr.predict(X_test_2D); #etichette predette
        print(y_pred)
        print("Accuracy:",ne,metrics.accuracy_score(y_test_2D, y_pred))
        ris.append(metrics.accuracy_score(y_test_2D, y_pred))
    plotta_test(ris,lista)

    print("\n_____KFold accuracy con k=10_____\n")
    ris_score=[]
    L=0
    for ne in lista:
        f="1_immagini/classificazione/AdaBoost/scores_ada_ovr_clf"+str(ne)
        # evaluate model
        if os.path.isfile(f):
            scores= pickle.load(open(f, 'rb'))
        else:
            # prepare the cross-validation procedure
            cv = KFold(n_splits=10, random_state=1, shuffle=True)
            ada_clf = ske.AdaBoostClassifier(n_estimators=ne, algorithm='SAMME')
            modelovr = OneVsRestClassifier(ada_clf)
            scores=cross_val_score(modelovr,X_train_2D,y_train_2D, scoring='accuracy', cv=cv, n_jobs=-1);
            pickle.dump(scores, open(f, 'wb'))
        #report performance
        print('Accuracy '+str(ne)+': %.3f (%.3f)' % (np.mean(scores), np.std(scores)))
        ris_score.append(np.mean(scores))

        plotta(scores,ris[L],' con n='+str(ne))
        L=L+1

    plotta_confronti(ris_score,ris,lista)