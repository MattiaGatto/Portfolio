import os
os.environ['TF_CPP_MIN_LOG_LEVEL'] = '3';
import pickle
from Utils import *
from sklearn import metrics
from sklearn.multiclass import OneVsRestClassifier
from sklearn.model_selection import KFold
import numpy as np
from sklearn.model_selection import cross_val_score
from sklearn.naive_bayes import GaussianNB

def run_GBN_I(path='immagini-3'):
    print("_____Start preprocessing_____\n")
    X_train_2D, X_test_2D, X_val_2D, y_train_2D, y_test_2D, y_val_2D = set_dati_2D(path)
    print("\n_____End preprocessing_____\n")
    print("_____Test accuracy_____\n")
    f="1_immagini/classificazione/Stime_di_densità/Gaussian_Naive_Bayes/GNB_ovr_clf"
    if os.path.isfile(f):
        clf = pickle.load(open(f, 'rb'))
        ovr=clf
    else:
        gnb = GaussianNB()
        ovr = OneVsRestClassifier(gnb)
        ovr.fit(X_train_2D,y_train_2D);
        pickle.dump(ovr, open(f, 'wb'))

    y_pred = ovr.predict(X_test_2D)
    print(y_pred)
    print("Accuracy:",metrics.accuracy_score(y_test_2D, y_pred))

    print("\n_____KFold accuracy con k=10_____\n")
    # prepare the cross-validation procedure
    cv = KFold(n_splits=10, random_state=1, shuffle=True)
    clf = GaussianNB()
    modelovr = OneVsRestClassifier(clf)
    f="1_immagini/classificazione/Stime_di_densità/Gaussian_Naive_Bayes/scores_GNB_ovr_clf"
    # evaluate model
    if os.path.isfile(f):
        scores= pickle.load(open(f, 'rb'))
    else:
        scores=cross_val_score(modelovr,X_train_2D,y_train_2D, scoring='accuracy', cv=cv, n_jobs=-1);
        pickle.dump(scores, open(f, 'wb'))
    #report performance
    print('Accuracy: %.3f (%.3f)' % (np.mean(scores), np.std(scores)))
    plotta(scores,metrics.accuracy_score(y_test_2D, y_pred),'')