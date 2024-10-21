import sys
from Adaboost import *
from SVM import *
from NeuralNetwork import *
from CNN import *
from Density_anomaly_detection import *
from GNB import *
from KNN import *
from Text_Density_anomaly_detection import *
from Text_KNN import *
from Text_Adaboost import *
from Text_SVM import *
from Text_NeuralNetwork import *

def lancia_model_Image(lancia,path):
    if((str(lancia)).lower()=='adaboost'or lancia==str(1)):
        run_Adaboost_I(path)
    if ((str(lancia)).lower() == 'svm'or lancia==str(2)):
        run_SVM_I(path)
    if ((str(lancia)).lower() == 'neural network'or lancia==str(3)):
        run_NN_I(path)
    if ((str(lancia)).lower() == 'convolutional neural network'or lancia==str(4)):
        run_CNN_I(path)
    if ((str(lancia)).lower() == 'gaussian naive bayes'or lancia==str(5)):
        run_GBN_I(path)
    if ((str(lancia)).lower() == 'nearest neighbor'or lancia==str(6)):
        run_KNN_I(path)
    if ((str(lancia)).lower() == 'density autoencoder'or lancia==str(7)):
        run_Density_Anomaly_I(path)


def lancia_model_Text(lancia,path):
    if((str(lancia)).lower()=='adaboost'or lancia==str(1)):
        run_Adaboost_T(path)
    if ((str(lancia)).lower() == 'svm'or lancia==str(2)):
        run_SVM_T(path)
    if ((str(lancia)).lower() == 'neural network'or lancia==str(3)):
        run_NN_T(path)
    if ((str(lancia)).lower() == 'nearest neighbor'or lancia==str(4)):
        run_KNN_T(path)
    if ((str(lancia)).lower() == 'density autoencoder'or lancia==str(5)):
        run_Density_Anomaly_T(path)




if __name__ == '__main__':

    nome_script, primo, secondo = sys.argv
    if((primo=='-i'or primo=='--image') and  len(secondo)!=0):
        print('Hai scelto immage! \n')
        lancia=input('Che algoritmo vuoi lanciare?\n'
                     'Classificazione:\n'
                     '\t* (1) AdaBoost\n'
                     '\t* (2) SVM\n'
                     '\t* Reti neurali:\n'
                     '\t\t+ (3) Neural network\n'
                     '\t\t+ (4) Convolutional Neural network\n'
                     '\t* Stime di Densità:\n'
                     '\t\t+ (5) Gaussian Naive Bayes\n'
                     '\t\t+ (6) Nearest Neighbor\n'
                     '\nAnomaly Detection:\n'
                     '\t* (7) Density autoencoder\n\n'
                     )
        lancia_model_Image(lancia,secondo)

    if((primo=='-t'or primo=='--text')and  len(secondo)!=0):
        print('Hai scelto text! \n')
        lancia = input('Che algoritmo vuoi lanciare?\n'
                       'Classificazione:\n'
                       '\t* (1) AdaBoost\n'
                       '\t* (2) SVM\n'
                       '\t* Reti neurali:\n'
                       '\t\t+ (3) Neural network\n'
                       '\t* Stime di Densità:\n'
                       '\t\t+ (4) Nearest Neighbor\n'
                       '\nAnomaly Detection:\n'
                       '\t* (5) Density autoencoder\n\n'
                       )
        lancia_model_Text(lancia,secondo)

    if((primo=='-a_i'or primo=='--all_i')and  len(secondo)!=0):
        print('Esegui tutti di fila, i modelli addestrati sul dataset di tipo immagini!\n')
        path = secondo
        print('\t_________________________adaboost_________________________\n')
        run_Adaboost_I(path)
        print('\t_________________________svm_________________________')
        run_SVM_I(path)
        print('\t_________________________neural network_________________________\n')
        run_NN_I(path)
        print('\t_________________________Convolutional neural network_________________________\n')
        run_CNN_I(path)
        print('\t_________________________Gaussian Naive Bayes_________________________\n')
        run_GBN_I(path)
        print('\t_________________________nearest neighbor_________________________\n')
        run_KNN_I(path)
        print('\t_________________________density autoencoder_________________________\n')
        run_Density_Anomaly_I(path)


    if ((primo == '-a_t' or primo == '--all_t')and  len(secondo)!=0):
        print('Esegui tutti di fila, i modelli addestrati sul dataset di tipo testo!\n')
        path=secondo
        print('\t_________________________adaboost_________________________\n')
        run_Adaboost_T(path)
        print('\t_________________________svm_________________________')
        run_SVM_T(path)
        print('\t_________________________neural network_________________________\n')
        run_NN_T(path)
        print('\t_________________________nearest neighbor_________________________\n')
        run_KNN_T(path)
        print('\t_________________________density autoencoder_________________________\n')
        run_Density_Anomaly_T(path)



    if((primo=='--help' or primo=='-h')or (len(secondo) <2 and len(secondo) <2)):
        print("Il primo elemento indica il nome dello script python è MAIN.py\n"
              "Il primo parametro indica invece se analizzare testi o immagini:"
              "\n\t* -i o --image per le immagini;"
              "\n\t* -t o --text per i testi;"
              "\n\t* -a_i o --all_i per lanciare tutti gli script di immagini, ci vorrà un pò!;"
              "\n\t* -a_t o --all_t per lanciare tutti gli script di testo, ci vorrà un pò!;"
              "\n\t* -h o --help per informazioni!;"
              "\nIl secondo parametro passato è il path del relativo percorso di testo o immagini!\n"
              "\nAll'inserimento basta o scrivere il nome dell'algoritmo(non importa se minuscolo o maiuscolo), oppure il numero corrispondente!\n\n ")
