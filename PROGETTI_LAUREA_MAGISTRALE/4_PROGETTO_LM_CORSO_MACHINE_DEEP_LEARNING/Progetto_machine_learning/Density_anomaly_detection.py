import os
os.environ['TF_CPP_MIN_LOG_LEVEL'] = '3';
from Utils import *
import pickle
import matplotlib.pyplot as plt
# Load libraries
import keras
import numpy as np
from keras.layers import Dense
from sklearn.metrics import roc_curve,roc_auc_score

def run_Density_Anomaly_I(path='immagini-3'):
    print("_____Start preprocessing_____\n")
    x_training, y_training,x_dig, id_anomaly=anomaly_data(path)
    print("\n_____End preprocessing_____\n")
    print("_____Test accuracy_____\n")

    # SIZE OF LAYERS
    n = 80*60
    l2 = 256
    l3 = 128

    # MODEL'S STRUCTURE
    input = keras.Input(shape=(n,))
    x = keras.layers.Dense(l2, activation ='relu')(input)
    x = keras.layers.Dense(l3,activation = 'relu')(x)
    encoder = keras.Model(input, x)
    x = keras.layers.Dense(l2, activation='relu')(x)
    x = keras.layers.Dense(n, activation='sigmoid')(x)
    autoencoder = keras.Model(input, x)
    autoencoder.compile(optimizer='adam', loss='mse')


    ep = 10 #10, 100, 500, 1000
    batches = 32 #32,64, 128
    #x_training = x_training.reshape(x_training.shape[0],n)
    x_dig = x_dig.reshape(x_dig.shape[0], n)

    f="1_immagini/anomalydetection/density_autoencoder/anomaly.hist"
    if os.path.isfile(f):
        history_dict = pickle.load(open(f, 'rb'))
        autoencoder.load_weights('1_immagini/anomalydetection/density_autoencoder/anomaly')
    else:
        history = autoencoder.fit(x_dig, x_dig, epochs=ep, batch_size=batches);
        history_dict = history.history;
        pickle.dump(history_dict, open(f, 'wb'))
        autoencoder.save_weights('1_immagini/anomalydetection/density_autoencoder/anomaly');

    print("\n_____outlierness_____\n")
    f="1_immagini/anomalydetection/density_autoencoder/anomaly_out.npy"
    if os.path.isfile(f):
        outlierness = np.load(f)
    else:
        outlierness = np.zeros(x_training.shape[0])
        for i in range(x_training.shape[0]):
            outlierness[i] = autoencoder.evaluate(x_training[i].reshape((1, n)), x_training[i].reshape((1, n)),verbose=1)
        np.save(f,outlierness)

    plt.figure(1)
    plt.plot(outlierness,'.')
    plt.xlabel('test id')
    plt.ylabel('outlierness')
    plt.show()

    print("\n_____Valore di AUC_____\n")
    fpr, tpr, thresholds = roc_curve(y_training,outlierness) #fpr=false positive rate,falsi positivi trovati cioè outlier che in realtà sono inlier, tpr=true positive rate, veri positivi trovati cioè anomalie
    auc = roc_auc_score(y_training,outlierness)
    plt.figure(2)
    plt.plot(fpr,tpr)
    plt.plot([0, 1], [0, 1], linestyle='--')
    plt.title('AUC = '+str(auc))
    plt.show()