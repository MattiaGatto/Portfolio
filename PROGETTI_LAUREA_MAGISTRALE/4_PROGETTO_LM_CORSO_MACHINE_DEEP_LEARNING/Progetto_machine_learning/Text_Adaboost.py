import os
os.environ['TF_CPP_MIN_LOG_LEVEL'] = '3';
import pickle
from Utils import *
from Text_Utils import *
import sklearn.ensemble as ske
from sklearn import metrics
from sklearn.multiclass import OneVsRestClassifier
from sklearn.model_selection import KFold
import numpy as np
from sklearn.model_selection import cross_val_score

def run_Adaboost_T(path='testi-3.xlsx'):
    print("_____Start preprocessing_____\n")
    vectors,Y=create_dataset_Text(path)
    X=vectors
    X_train, X_test, y_train, y_test = train_test_split(X,Y, test_size=0.2, random_state=0)
    print("Informazioni sul Trainset del dataset (taglia, tipo)-->",X_train.shape,X_train.dtype,type(X_train))
    print("Informazioni sul Trainset delle label (taglia, tipo)-->",y_train.shape,y_train.dtype,type(y_train))
    print("Informazioni sul Testset del dataset (taglia, tipo)-->",X_test.shape,X_test.dtype,type(X_test))
    print("Informazioni sul Testset delle label (taglia, tipo)-->",y_test.shape,y_test.dtype,type(y_test))
    print("\n_____End preprocessing_____\n\n")
    print("_____Test accuracy_____\n")
    lista = [1, 3, 5, 10, 20, 25, 50]
    ris = []
    for ne in lista:  # classificatori che sta addestrando
        f = "2_testo/classificazione/AdaBoost/ada_clf_ovr_Text" + str(ne)
        if os.path.isfile(f):
            ada_clf = pickle.load(open(f, 'rb'))
            ovr = ada_clf
        else:
            ada_clf = ske.AdaBoostClassifier(n_estimators=ne, algorithm='SAMME');
            ovr = OneVsRestClassifier(ada_clf)
            ovr.fit(X_train, y_train);
            pickle.dump(ovr, open(f, 'wb'))
        y_pred = ovr.predict(X_test);  # etichette predette
        print(y_pred)
        print("Accuracy:", ne, metrics.accuracy_score(y_test, y_pred))
        ris.append(metrics.accuracy_score(y_test, y_pred))

    plotta_test(ris, lista)
    print("\n_____KFold accuracy con k=10_____\n")
    ris_score=[]
    L=0
    for ne in lista:

        f="2_testo/classificazione/AdaBoost/scores_ada_ovr_clf_Text"+str(ne)
        # evaluate model
        if os.path.isfile(f):
            scores= pickle.load(open(f, 'rb'))
        else:
            # prepare the cross-validation procedure
            cv = KFold(n_splits=10, random_state=1, shuffle=True)
            ada_clf = ske.AdaBoostClassifier(n_estimators=ne, algorithm='SAMME')
            modelovr = OneVsRestClassifier(ada_clf)
            scores=cross_val_score(modelovr,X_train,y_train, scoring='accuracy', cv=cv, n_jobs=-1);
            pickle.dump(scores, open(f, 'wb'))
        #report performance
        print('Accuracy ' + str(ne) + ': %.3f (%.3f)' % (np.mean(scores), np.std(scores)))
        ris_score.append(np.mean(scores))

        plotta(scores, ris[L], ' con n=' + str(ne))
        L = L + 1

    plotta_confronti(ris_score, ris, lista)