import numpy as np
import os
os.environ['TF_CPP_MIN_LOG_LEVEL'] = '3';
import pickle
import pathlib
import sklearn
import tensorflow as tf # API PRIMO LIVELLO
import tensorflow.keras as keras # API SECONDO LIVELLO
import tensorflow.keras.layers as layers # LAYERS
import matplotlib
import matplotlib.pyplot as plt


data_dir=pathlib.PureWindowsPath('c:/Windows','/Users/matti/Desktop/Università/__________Magistrale__________/_________Machine deep learning/Progetto/immagini-3')
ds=tf.keras.preprocessing.image_dataset_from_directory(data_dir);
class_names = [ 'handbags', 'sport-shoes', 'tops', 'trousers'];
