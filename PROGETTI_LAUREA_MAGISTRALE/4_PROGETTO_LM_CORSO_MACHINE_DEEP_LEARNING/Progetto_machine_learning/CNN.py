import os
os.environ['TF_CPP_MIN_LOG_LEVEL'] = '3';
import pickle
import tensorflow as tf # API PRIMO LIVELLO
import tensorflow.keras as keras # API SECONDO LIVELLO
import tensorflow.keras.layers as layers # LAYERS
from Utils import *
import keras
import pandas as pd
from sklearn.model_selection import KFold
from keras.wrappers.scikit_learn import KerasClassifier
from keras.models import Sequential
from keras.layers import Dense, Conv2D, Flatten,Dropout
import numpy as np
from sklearn.model_selection import cross_val_score

# Create function returning a compiled network
def create_network():
    # Start neural network
    network = Sequential()
    network.add(Conv2D(32, kernel_size=3, activation='relu', input_shape=(80, 60, 1)))
    network.add(Dropout(0.3))
    network.add(Conv2D(16, kernel_size=3, activation='relu'))
    network.add(Dropout(0.3))
    network.add(Flatten())
    network.add(Dense(10, activation='softmax'))

    # Compile neural network
    network.compile(loss='sparse_categorical_crossentropy',  # Cross-entropy
                    optimizer='sgd',  # Root Mean Square Propagation
                    metrics=['accuracy'])  # Accuracy performance metric

    # Return compiled network
    return network


def run_CNN_I(path):
    print("_____Start preprocessing_____\n")
    X_train, X_test, X_val, y_train, y_test, y_val = set_dati(path)
    print("\n_____End preprocessing_____\n")
    print("_____Test accuracy_____\n")
    img_rows=X_train[0].shape[0]
    img_cols=X_train[0].shape[1]

    X_train=X_train.reshape(X_train.shape[0],img_rows,img_cols,1)
    X_test=X_test.reshape(X_test.shape[0],img_rows,img_cols,1)

    model = Sequential()
    model.add(Conv2D(32, kernel_size=3, activation='relu', input_shape=( 80, 60,1)))
    model.add(Dropout(0.3))
    model.add(Conv2D(16, kernel_size=3, activation='relu'))
    model.add(Dropout(0.3))
    model.add(Flatten())
    model.add(Dense(10, activation='softmax'))

    model.compile(optimizer='adam', loss='categorical_crossentropy', metrics=['accuracy'])

    model.compile(loss="sparse_categorical_crossentropy",optimizer="sgd", metrics=["accuracy"])
    f="1_immagini/classificazione/Reti_neurali/Convolutional_neural_network/CNN.hist"
    if os.path.isfile(f):
        history_dict = pickle.load(open(f, 'rb'))
        model.load_weights('1_immagini/classificazione/Reti_neurali/Convolutional_neural_network/CNN')
    else:
        history = model.fit(X_train, y_train, epochs=10,validation_data=(X_test, y_test)) #E questo è tutto! La rete neurale è addestrata.
        history_dict = history.history;
        pickle.dump(history_dict, open(f, 'wb'))
        model.save_weights('1_immagini/classificazione/Reti_neurali/Convolutional_neural_network/CNN');

    pd.DataFrame(history_dict).plot(figsize=(8, 5))
    plt.grid(True)
    plt.gca().set_ylim(0, 1) # set the vertical range to [0-1]
    plt.show()

    f="1_immagini/classificazione/Reti_neurali/Convolutional_neural_network/CNN_out.npy"
    if os.path.isfile(f):
        out = np.load(f)
    else:
        out = model.evaluate(X_test, y_test)
        np.save(f,out)
    print('Valutazione:',out)

    print("\n_____KFold accuracy con k=10_____\n")
    f = "1_immagini/classificazione/Reti_neurali/Convolutional_neural_network/scores_CNN_clf"
    if os.path.isfile(f):
        scores = pickle.load(open(f, 'rb'))
    else:
        # Wrap Keras model so it can be used by scikit-learn
        neural_network = KerasClassifier(build_fn=create_network, epochs=10)
        cv = KFold(n_splits=10, random_state=1, shuffle=True)
        # Evaluate neural network using three-fold cross-validation
        scores = cross_val_score(neural_network, X_train, y_train, scoring='accuracy', cv=cv, n_jobs=-1)
        pickle.dump(scores, open(f, 'wb'))
    print('Accuracy: %.3f (%.3f)' % (np.mean(scores), np.std(scores)))
    plotta(scores, out[1],'')