import os
os.environ['TF_CPP_MIN_LOG_LEVEL'] = '3';
import pickle
import tensorflow as tf # API PRIMO LIVELLO
import tensorflow.keras as keras # API SECONDO LIVELLO
import tensorflow.keras.layers as layers # LAYERS
from Utils import *
#import keras
import pandas as pd
from sklearn.model_selection import KFold
import numpy as np
from sklearn.model_selection import cross_val_score


# Create function returning a compiled network
def create_network():
    # Start neural network
    network = keras.models.Sequential([
        keras.layers.Flatten(input_shape=[80, 60]),
        keras.layers.Dense(300, activation="relu"),
        keras.layers.Dense(100, activation="relu"),
        keras.layers.Dense(10, activation="softmax")
    ])
    # Compile neural network
    network.compile(loss='sparse_categorical_crossentropy',  # Cross-entropy
                    optimizer='sgd',  # Root Mean Square Propagation
                    metrics=['accuracy'])  # Accuracy performance metric

    # Return compiled network
    return network

def run_NN_I(path='immagini-3'):
    print("_____Start preprocessing_____\n")
    X_train, X_test, X_val, y_train, y_test, y_val = set_dati(path)
    print("\n_____End preprocessing_____\n")
    print("_____Test accuracy_____\n")
    model = keras.models.Sequential([
        keras.layers.Flatten(input_shape=[80, 60]),
        keras.layers.Dense(300, activation="relu"),
        keras.layers.Dense(100, activation="relu"),
        keras.layers.Dense(10, activation="softmax")
    ])
    # output_layer = keras.layers.Dense(10)
    model.compile(loss="sparse_categorical_crossentropy",optimizer="sgd", metrics=["accuracy"])
    f="1_immagini/classificazione/Reti_neurali/Neural_networks/NN.hist"
    if os.path.isfile(f):
        history_dict = pickle.load(open(f, 'rb'))
        model.load_weights('1_immagini/classificazione/Reti_neurali/Neural_networks/NN')
    else:
        history = model.fit(X_train, y_train, epochs=100,validation_data=(X_test, y_test)) #E questo è tutto! La rete neurale è addestrata.
        history_dict = history.history;
        pickle.dump(history_dict, open(f, 'wb'))
        model.save_weights('1_immagini/classificazione/Reti_neurali/Neural_networks/NN');

    pd.DataFrame(history_dict).plot(figsize=(8, 5))
    plt.grid(True)
    plt.gca().set_ylim(0, 1) # set the vertical range to [0-1]
    plt.show()

    f="1_immagini/classificazione/Reti_neurali/Neural_networks/NN_out.npy"
    if os.path.isfile(f):
        out = np.load(f)
    else:
        out = model.evaluate(X_test, y_test)
        np.save(f,out)
    print('Valutazione:',out)

    #test visivo
    class_names = ['handbags', 'sports-shoes', 'tops', 'trousers'];
    X_new = X_test[:3]
    y_proba = model.predict(X_new)
    y_proba.round(2)
    y_pred = model.predict_classes(X_new)
    # print(y_pred)
    np.array(class_names)[y_pred]
    y_new = y_test[:3]
    print('\nTest di predizione:')
    print('Primi 3 elementi del testset: ',y_new)
    print('Verifica di corrispondenza con i primi tre elementi predetti: ',y_pred==y_new) #ha classificato correttamente entrambe le tre

    print("\n_____KFold accuracy con k=10_____\n")
    f = "1_immagini/classificazione/Reti_neurali/Neural_networks/scores_NN_clf"
    if os.path.isfile(f):
        scores = pickle.load(open(f, 'rb'))
    else:
        # Wrap Keras model so it can be used by scikit-learn
        neural_network = keras.wrappers.scikit_learn.KerasClassifier(build_fn=create_network, epochs=100)
        cv = KFold(n_splits=10, random_state=1, shuffle=True)
        # Evaluate neural network using three-fold cross-validation
        scores = cross_val_score(neural_network, X_train, y_train, scoring='accuracy', cv=cv, n_jobs=-1)
        pickle.dump(scores, open(f, 'wb'))

    print('Accuracy: %.3f (%.3f)' % (np.mean(scores), np.std(scores)))

    plotta(scores,out[1], '')