from Utils import *
from Text_Utils import *
import numpy as np
import os
os.environ['TF_CPP_MIN_LOG_LEVEL'] = '3';
import pickle
import tensorflow as tf # API PRIMO LIVELLO
import tensorflow.keras as keras # API SECONDO LIVELLO
import tensorflow.keras.layers as layers # LAYERS
import matplotlib.pyplot as plt
from sklearn.model_selection import train_test_split
from keras.wrappers.scikit_learn import KerasClassifier
from sklearn.model_selection import KFold
from sklearn.model_selection import cross_val_score
import pandas as pd

# Create function returning a compiled network
def create_network(DIM):
    # Start neural network
    Net = keras.models.Sequential([
        keras.layers.Flatten(input_dim=(DIM)),
        keras.layers.Dense(32, activation="relu"),
        keras.layers.Dense(16, activation="relu"),
        keras.layers.Dense(3, activation="softmax")
    ])
    # Compile neural network
    Net.compile(loss='sparse_categorical_crossentropy',  # Cross-entropy
                optimizer='sgd',  # Root Mean Square Propagation
                metrics=['accuracy'])  # Accuracy performance metric

    # Return compiled network
    return Net

def run_NN_T(path='testi-3.xlsx'):
    print("_____Start preprocessing_____\n")
    vectors,Y=create_dataset_Text(path)
    X=vectors.todense()
    X_train, X_test, y_train, y_test = train_test_split(X,Y, test_size=0.2, random_state=0)
    print("Informazioni sul Trainset del dataset (taglia, tipo)-->", X_train.shape, X_train.dtype, type(X_train))
    print("Informazioni sul Trainset delle label (taglia, tipo)-->", y_train.shape, y_train.dtype, type(y_train))
    print("Informazioni sul Testset del dataset (taglia, tipo)-->", X_test.shape, X_test.dtype, type(X_test))
    print("Informazioni sul Testset delle label (taglia, tipo)-->", y_test.shape, y_test.dtype, type(y_test))
    print("\n_____End preprocessing_____\n\n")
    print("_____Test accuracy_____\n")


    model = keras.models.Sequential([
        keras.layers.Flatten(input_dim=(15665)),
        keras.layers.Dense(32, activation="relu"),
        keras.layers.Dense(16, activation="relu"),
        keras.layers.Dense(3, activation="softmax")
    ])
    # output_layer = keras.layers.Dense(100)
    model.compile(loss="sparse_categorical_crossentropy",optimizer="sgd", metrics=["accuracy"])
    f="2_testo/classificazione/Reti_Neurali/NN_Text.hist"
    if os.path.isfile(f):
        history_dict = pickle.load(open(f, 'rb'))
        model.load_weights('2_testo/classificazione/Reti_Neurali/NN_Text')
    else:
        history = model.fit(X_train, y_train, epochs=100,batch_size=32,validation_data=(X_test, y_test)) #E questo è tutto! La rete neurale è addestrata.
        history_dict = history.history;
        pickle.dump(history_dict, open(f, 'wb'))
        model.save_weights('2_testo/classificazione/Reti_Neurali/NN_Text');

    pd.DataFrame(history_dict).plot(figsize=(8, 5))
    plt.grid(True)
    plt.gca().set_ylim(0, 1) # set the vertical range to [0-1]
    plt.show()

    f="2_testo/classificazione/Reti_Neurali/NN_out_Text.npy"
    if os.path.isfile(f):
        out = np.load(f)
    else:
        out = model.evaluate(X_test, y_test,batch_size=32)
        np.save(f,out)
    print('Valutazione:', out)


    print("\n_____KFold accuracy con k=10_____\n")
    vectors,Y=create_dataset_Text_ridotto(path)


    X=np.array(vectors.todense(), dtype=float)
    X_train, X_test, y_train, y_test = train_test_split(X,Y, test_size=0.2, random_state=0)
    DIM=X_train.shape[1]
    print('Dimensioni del dataset ridotte:',X_train.shape,type(X_train),'\nDimensioni delle label ridotte:',y_train.shape,type(y_train))

    f = "2_testo/classificazione/Reti_Neurali/scores_NN_clf_Text"
    if os.path.isfile(f):
        score = pickle.load(open(f, 'rb'))
    else:
        # Wrap Keras model so it can be used by scikit-learn
        neural_network = KerasClassifier(build_fn=create_network(DIM), epochs=100, batch_size=32)
        cv = KFold(n_splits=10, random_state=1, shuffle=True)
        # Evaluate neural network using three-fold cross-validation
        score = cross_val_score(neural_network, X_train, y_train, scoring='accuracy', cv=cv,
                                n_jobs=-1)  # ,error_score ='raise')
        pickle.dump(score, open(f, 'wb'))

    print('Accuracy: %.3f (%.3f)' % (np.mean(score), np.std(score)))
    plotta(score,out[1],'')