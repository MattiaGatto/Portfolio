import os
os.environ['TF_CPP_MIN_LOG_LEVEL'] = '3';
import matplotlib.pyplot as plt
import cv2 as cv
import numpy as np
from sklearn.model_selection import train_test_split

def create_dataset (img_folder):
    img_data_array = []
    IMG_WIDTH = 80
    IMG_HEIGHT = 60
    for filename in os.listdir (img_folder):
        immagine = cv.imread(os.path.join(img_folder,filename), cv.IMREAD_GRAYSCALE)
        immagine = cv.resize (immagine, (IMG_HEIGHT, IMG_WIDTH))
        immagine = np.array (immagine)
        immagine = immagine.astype ('float32')
        immagine/=255
        img_data_array.append (immagine)
    return np.array(img_data_array)

def plotta_label(l):
    # creating the dataset
    class_names = ['handbags', 'sports-shoes', 'tops', 'trousers'];
    data = {class_names[0]: len(np.where(l==0)[0]), class_names[1]: len(np.where(l==1)[0]),class_names[2]: len(np.where(l==2)[0]),class_names[3]: len(np.where(l==3)[0])}
    label = list(data.keys())
    values = list(data.values())
    # creating the bar plot
    plt.bar(label, values, width=0.4,color='red')
    plt.xlabel("label")
    plt.ylabel("size")
    plt.title("Numero esempi, min= "+str(min(values))+", max= "+str(max(values)))
    plt.show()


def assign_label(folder,class_names,size):
    label = []
    for i in range (size):
        for j in range (len(class_names)):
            if(class_names[j] in os.listdir(folder)[i]):
                label.append(j)
    plotta_label(np.array(label))
    return np.array(label)

def plotta_test(ris,lista):
    data={}
    for i in range(len(lista)):
        data[str(str(lista[i])+"n="+'%.3f' % ris[i])]=ris[i]
    courses = list(data.keys())
    values = list(data.values())
    # creating the bar plot
    plt.bar(courses, values,width = 0.8)
    plt.xlabel("Test prediction")
    plt.ylabel("accuracy")
    plt.title("Scores")
    plt.show()

def plotta(score,y_test,n):
    # creating the dataset
    data = {"10cv = "+'%.3f' % (np.mean(score)): np.mean(score), "Test = "+'%.3f' % (y_test):y_test}
    courses = list(data.keys())
    values = list(data.values())
    # creating the bar plot
    plt.bar(courses, values, width = 0.4)
    plt.xlabel("Kfold")
    plt.ylabel("Scores")
    plt.title("Scores by 10fold e test"+n)
    plt.show()

def set_dati_2D(path):
    class_names = ['handbags', 'sports-shoes', 'tops', 'trousers'];
    #folder = "immagini-3"
    folder=path
    ds = create_dataset(folder)
    size = len(os.listdir(folder))
    ds_label = assign_label(folder, class_names, size)

    #Prova stampa
    print("Stampa di un elemento del datset dopo l'elaborazione")
    plt.imshow(ds[0], cmap="gray")
    plt.show()
    #print('handbags' in os.listdir(folder)[0])

    nsamples, nx, ny = ds.shape
    d2_train_dataset = ds.reshape((nsamples, nx * ny))
    X_train_2D, X_test_2D, y_train_2D, y_test_2D = train_test_split(d2_train_dataset, ds_label, test_size=0.2, random_state=1)
    print("Informazioni sul Trainset del dataset (taglia, tipo, ampiezza)-->",X_train_2D.shape, X_train_2D.dtype, len(X_train_2D))
    print("Informazioni sul Trainset delle label (taglia, tipo, ampiezza)-->",y_train_2D.shape, y_train_2D.dtype, len(y_train_2D))
    X_train_2D, X_val_2D, y_train_2D, y_val_2D = train_test_split(X_train_2D, y_train_2D, test_size=0.2, random_state=1)

    return X_train_2D, X_test_2D, X_val_2D, y_train_2D, y_test_2D, y_val_2D

def set_dati(path):
    class_names = ['handbags', 'sports-shoes', 'tops', 'trousers'];
    #folder = "immagini-3"
    folder=path
    ds = create_dataset(folder)
    size = len(os.listdir(folder))
    ds_label = assign_label(folder, class_names, size)

    # Prova stampa
    print("Stampa di un elemento del datset dopo l'elaborazione")
    plt.imshow(ds[0], cmap="gray")
    plt.show()
    # print('handbags' in os.listdir(folder)[0])

    X_train, X_test, y_train, y_test = train_test_split(ds, ds_label, test_size=0.2, random_state=1)
    print("Informazioni sul Trainset del dataset (taglia, tipo, ampiezza)-->",X_train.shape, X_train.dtype, len(X_train))
    print("Informazioni sul Trainset dele label (taglia, tipo, ampiezza)-->",y_train.shape, y_train.dtype, len(y_train))
    X_train, X_val, y_train, y_val = train_test_split(X_train, y_train, test_size=0.2, random_state=1)

    return X_train, X_test, X_val, y_train, y_test, y_val

def dataset(dig,ds,ds_label,I):
    #x_train, x_test, y_train, y_test = train_test_split(ds,ds_label, test_size=0.2, random_state=0)
    x_train=ds
    y_train=ds_label
    I_dig = np.where(y_train == dig)[0]
    x_dig = x_train[I_dig]
    y_dig = [0] * x_dig.shape[0] # 0 valore inlier
    x_training = x_dig
    for i in range(4):
        id_anomaly = np.array([])
        if (i != dig): #se non è il valore di inlier
            jj = np.where(y_train == i)[0]
            xjj = x_train[jj]
            id_anomaly = np.concatenate((id_anomaly,jj))
            x_training = np.concatenate((x_training, xjj))
    y_nodig = [1] *((len(y_train))-(len(np.where(y_train == I)[0])))
    y_training = np.concatenate((y_dig, y_nodig))
    id_anomaly = id_anomaly.astype(int)
    return x_training, y_training, x_dig, id_anomaly

def plotta_label_anomaly(anomaly):
    # creating the dataset
    class_names = ['inlier', 'anomaly'];
    data = {class_names[0]: len(np.where(anomaly==0)[0]), class_names[1]: len(np.where(anomaly==1)[0])}
    label = list(data.keys())
    values = list(data.values())
    # creating the bar plot
    plt.bar(label, values, width=0.4,color='red')
    plt.xlabel("label")
    plt.ylabel("size")
    plt.title("Numero esempi, min= "+str(min(values))+", max= "+str(max(values)))
    plt.show()

def anomaly_data(path):
    class_names = [ 'handbags', 'sports-shoes', 'tops', 'trousers'];
    #folder="immagini-3"
    folder=path
    ds = create_dataset(folder)
    size=len(os.listdir(folder))
    ds_label = assign_label(folder, class_names, size)
    print('*********',np.unique(ds_label))
    class_names_count = [ 0,0,0,0];
    for i in ds_label:
        class_names_count[i]=class_names_count[i]+1
    print('Conteggio elementi, per determinare il valore inlier: ',class_names_count)

    pos=0
    m=class_names_count[0]
    I=0
    while (pos < len(class_names_count)) :
        if class_names_count[pos] > m :
            m = class_names_count[pos]
            I=pos
        pos = pos + 1
    #print (f'{class_names[I]}, start=1583 {class_names[ds_label[1583]]},end={1583+1831} {class_names[ds_label[1583+1831]]}')

    inlier_clothes = I
    print(f'il valore da considerare inlier è {I}, ossia {class_names[I]}')
    x_training, y_training, x_dig, id_anomaly = dataset(inlier_clothes,ds,ds_label,I)
    print("Informazioni sul dataset (taglia, tipo)-->",x_training.shape,type(x_training))
    print("Informazioni sul Trainset dei valori inlier (taglia, tipo)-->", x_dig.shape, type(x_dig))
    print("Informazioni sulle  label (taglia)-->",y_training.shape)
    plotta_label_anomaly(y_training)
    return x_training, y_training, x_dig, id_anomaly

def plotta_confronti(ris_score,ris,labels):
    x = np.arange(len(ris_score))  # the label locations
    width = 0.35  # the width of the bars

    fig, ax = plt.subplots()
    rects1 = ax.bar(x - width / 2, ris_score, width, label='10fold')
    rects2 = ax.bar(x + width / 2, ris, width, label='test')

    # Add some text for labels, title and custom x-axis tick labels, etc.
    ax.set_ylabel('Scores')
    ax.set_title('Scores by 10fold and test')
    ax.set_xticks(x)
    ax.set_xticklabels(labels)
    ax.legend()
    fig.tight_layout()
    print('\n10fold con n='+str(labels)+'\n',ris_score,'\nTest con n='+str(labels)+'\n',ris)
    plt.show()