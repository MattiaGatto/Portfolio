import cImage
def riduzione_percentuale(immagine):
    # in questo esempio restituiamo l'immagine in un tono di rosso,verde o blu diminuito in base alla percentuale inserita dall' utente.
    larghezza = immagine.getWidth()
    altezza = immagine.getHeight()
    nuova_immagine = cImage.EmptyImage(larghezza,altezza)
    l=int(input("inserisci la percentuale da sottrarre: "))
    x = str(input("Scegli tra le seguenti tonalità: rosso, verde o blu, altrimenti restituisce un errore:\n"))
    
    if x=="rosso" or x=="Rosso" or x=="ROSSO":
        for i in range(altezza):
            for j in range(larghezza):
                pixel = immagine.getPixel(j,i)
                r = pixel.getRed()
                c = r//100*l
                newred=(pixel.getRed())-c
                newgreen=pixel.getGreen()
                newblue=pixel.getBlue()
                nuovo_Pixel = cImage.Pixel(newred,newgreen,newblue)
                nuova_immagine.setPixel(j,i,nuovo_Pixel)
        return nuova_immagine
    elif x=="verde" or x=="Verde" or x=="VERDE":
        for i in range(altezza):
            for j in range(larghezza):
                pixel = immagine.getPixel(j,i)
                r = pixel.getGreen()
                c = r//100*l
                newred=pixel.getRed()
                newgreen=pixel.getGreen()-c
                newblue=pixel.getBlue()
                nuovo_Pixel = cImage.Pixel(newred,newgreen,newblue)
                nuova_immagine.setPixel(j,i,nuovo_Pixel)
        return nuova_immagine
    elif x=="blu" or x=="Blu" or x=="BLU":
        for i in range(altezza):
            for j in range(larghezza):
                pixel = immagine.getPixel(j,i)
                r = pixel.getBlue()
                c = r//100*l
                newred=pixel.getRed()
                newgreen=pixel.getGreen()
                newblue=pixel.getBlue()-c
                nuovo_Pixel = cImage.Pixel(newred,newgreen,newblue)
                nuova_immagine.setPixel(j,i,nuovo_Pixel)
        return nuova_immagine
def trasformazione1(file_immagine):
    # carichiamo e mostriamo l'immagine originale
    immagine = cImage.Image(file_immagine)            
    larghezza = immagine.getWidth()
    altezza = immagine.getHeight()
    finestra = cImage.ImageWin("Immagine originale",larghezza,altezza)
    immagine.draw(finestra)

    # invochiamo la funzione che implementa l'operazion, in modo da operare sull' immagine
    nuova_immagine = riduzione_percentuale(immagine)
    
    # mostiamo la nuova immagine cioè il risultato
    larghezza = nuova_immagine.getWidth()
    altezza = nuova_immagine.getHeight()
    finestra_2 = cImage.ImageWin("Nuova immagine",larghezza,altezza)
    nuova_immagine.draw(finestra_2)
    finestra_2.exitOnClick()
    finestra.exitOnClick()   


def toni(immagine):
    # in questo esempio restituiamo l'immagine in toni di rosso,verde o blu
    larghezza = immagine.getWidth()
    altezza = immagine.getHeight()
    nuova_immagine = cImage.EmptyImage(larghezza,altezza)
    x = str(input("Scegli tra le seguenti tonalità: rosso, verde o blu, altrimenti restituisce un errore:\n"))
    if x=="rosso" or x=="Rosso" or x=="ROSSO":
        for i in range(altezza):
            for j in range(larghezza):
                pixel = immagine.getPixel(j,i)
                nuovo_Pixel = (pixel.getRed())
                nuovo_Pixel = cImage.Pixel(nuovo_Pixel,0,0)
                nuova_immagine.setPixel(j,i,nuovo_Pixel)
        return nuova_immagine
    elif x=="verde" or x=="Verde" or x=="VERDE":
        for i in range(altezza):
            for j in range(larghezza):
                pixel = immagine.getPixel(j,i)
                nuovo_Pixel = (pixel.getGreen())
                nuovo_Pixel = cImage.Pixel(0,nuovo_Pixel,0)
                nuova_immagine.setPixel(j,i,nuovo_Pixel)
        return nuova_immagine
    elif x=="blu" or x=="Blu" or x=="BLU":
        for i in range(altezza):
            for j in range(larghezza):
                pixel = immagine.getPixel(j,i)
                nuovo_Pixel = (pixel.getBlue())
                nuovo_Pixel = cImage.Pixel(0,0,nuovo_Pixel)
                nuova_immagine.setPixel(j,i,nuovo_Pixel)
        return nuova_immagine

def trasformazione2(file_immagine):
    # carichiamo e mostriamo l'immagine originale
    immagine = cImage.Image(file_immagine)            
    larghezza = immagine.getWidth()
    altezza = immagine.getHeight()
    finestra = cImage.ImageWin("Immagine originale",larghezza,altezza)
    immagine.draw(finestra)

    # invochiamo la funzione che implementa l'operazione toni in modo da operare sull' immagine
    nuova_immagine = toni(immagine)
    
    # mostiamo la nuova immagine cioè il risultato
    larghezza = nuova_immagine.getWidth()
    altezza = nuova_immagine.getHeight()
    finestra_2 = cImage.ImageWin("Nuova immagine",larghezza,altezza)
    nuova_immagine.draw(finestra_2)
    finestra_2.exitOnClick()
    finestra.exitOnClick()
    
def pixel_adiacenti(immagine):
    # in questo esempio restituiamo l'immagine con pixel adiacenti in toni di rosso,verde o blu
    larghezza = immagine.getWidth()
    altezza = immagine.getHeight()
    nuova_immagine = cImage.EmptyImage(larghezza,altezza)
    x = str(input("Scegli tra le seguenti tonalità: rosso, verde o blu, altrimenti restituisce un errore:\n"))
    if x=="rosso" or x=="Rosso" or x=="ROSSO":
        h=int(input("Inserire il numero dell opzione da eseguire:\n"
                    "1.Se si vuole effettuare la media dei pixel adiacenti;\n"
                    "2.Se si vuole dividere la somma dei pixel adiacenti per un numero a piacere;\n"))
        if h==1:
            d=4
            for i in range(altezza):
                for j in range(larghezza):
                    pixel = immagine.getPixel(j,i)
                    nuovo_Rosso=controlliRed(altezza,larghezza,immagine,i,j,d)
                    nuovo_Verde=pixel.getGreen()
                    nuovo_Blue=pixel.getBlue()
                    nuovo_Pixel = cImage.Pixel(nuovo_Rosso,nuovo_Verde,nuovo_Blue)
                    nuova_immagine.setPixel(j,i,nuovo_Pixel)
            return nuova_immagine
        elif h==2:
            d=int(input("Inserire il numero della divisione:\n"))
            for i in range(altezza):
                for j in range(larghezza):
                    pixel = immagine.getPixel(j,i)
                    nuovo_Rosso=controlliRed(altezza,larghezza,immagine,i,j,d)
                    nuovo_Verde=pixel.getGreen()
                    nuovo_Blue=pixel.getBlue()
                    nuovo_Pixel = cImage.Pixel(nuovo_Rosso,nuovo_Verde,nuovo_Blue)
                    nuova_immagine.setPixel(j,i,nuovo_Pixel)
            return nuova_immagine
    if x=="verde" or x=="Verde" or x=="VERDE":
        h=int(input("Inserire il numero dell opzione da eseguire:\n"
                    "1.Se si vuole effettuare la media dei pixel adiacenti;\n"
                    "2.Se si vuole dividere la somma dei pixel adiacenti per un numero a piacere;\n"))
        if h==1:
            d=4
            for i in range(altezza):
                for j in range(larghezza):
                    pixel = immagine.getPixel(j,i)
                    nuovo_Verde=controlliGreen(altezza,larghezza,immagine,i,j,d)
                    nuovo_Rosso=pixel.getRed()
                    nuovo_Blue=pixel.getBlue()
                    nuovo_Pixel = cImage.Pixel(nuovo_Rosso,nuovo_Verde,nuovo_Blue)
                    nuova_immagine.setPixel(j,i,nuovo_Pixel)
            return nuova_immagine
        elif h==2:
            d=int(input("Inserire il numero della divisione:\n"))
            for i in range(altezza):
                for j in range(larghezza):
                    pixel = immagine.getPixel(j,i)
                    nuovo_Verde=controlliGreen(altezza,larghezza,immagine,i,j,d)
                    nuovo_Rosso=pixel.getRed()
                    nuovo_Blue=pixel.getBlue()
                    nuovo_Pixel = cImage.Pixel(nuovo_Rosso,nuovo_Verde,nuovo_Blue)
                    nuova_immagine.setPixel(j,i,nuovo_Pixel)
            return nuova_immagine
        
    if x=="blu" or x=="Blu" or x=="BLU":
        h=int(input("Inserire il numero dell opzione da eseguire:\n"
                    "1.Se si vuole effettuare la media dei pixel adiacenti;\n"
                    "2.Se si vuole dividere la somma dei pixel adiacenti per un numero a piacere;\n"))
        if h==1:
            d=4
            for i in range(altezza):
                for j in range(larghezza):
                    pixel = immagine.getPixel(j,i)
                    nuovo_Blue=controlliBlue(altezza,larghezza,immagine,i,j,d)
                    nuovo_Verde=pixel.getGreen()
                    nuovo_Rosso=pixel.getRed()
                    nuovo_Pixel = cImage.Pixel(nuovo_Rosso,nuovo_Verde,nuovo_Blue)
                    nuova_immagine.setPixel(j,i,nuovo_Pixel)
            return nuova_immagine
        elif h==2:
            d=int(input("Inserire il numero della divisione:\n"))
            for i in range(altezza):
                for j in range(larghezza):
                    pixel = immagine.getPixel(j,i)
                    nuovo_Blue=controlliBlue(altezza,larghezza,immagine,i,j,d)
                    nuovo_Verde=pixel.getGreen()
                    nuovo_Rosso=pixel.getRed()
                    nuovo_Pixel = cImage.Pixel(nuovo_Rosso,nuovo_Verde,nuovo_Blue)
                    nuova_immagine.setPixel(j,i,nuovo_Pixel)
            return nuova_immagine


def controlliRed(altezza,larghezza,immagine,i,j,d):
    if j!=0 and i!=0 and i!=altezza-1 and j!=larghezza-1:
        pixel1 = immagine.getPixel(j+1,i)
        pixel1 = pixel1.getRed()
        
        pixel2 = immagine.getPixel(j,i+1)
        pixel2 = pixel2.getRed()
        pixel3 = immagine.getPixel(j-1,i)
        pixel3 = pixel3.getRed()
        pixel4 = immagine.getPixel(j,i-1)
        pixel4 = pixel4.getRed()
        nuovo_Rosso = (pixel1+pixel2+pixel3+pixel4)//d
        return nuovo_Rosso
    elif j==0 and i==0  :
        pixel1= immagine.getPixel(j+1,i)
        pixel1 = pixel1.getRed()
        pixel2 = immagine.getPixel(j,i+1)
        pixel2 = pixel2.getRed()
        nuovo_Rosso = (pixel1+pixel2)//2
        return nuovo_Rosso
    elif i==0 and i!=altezza-1 and j!=larghezza-1 and j!=0:
        pixel1 = immagine.getPixel(j+1,i)
        pixel1 = pixel1.getRed()
        pixel2 = immagine.getPixel(j,i+1)
        pixel2 = pixel2.getRed()
        pixel3 = immagine.getPixel(j-1,i)
        pixel3 = pixel3.getRed()
        nuovo_Rosso = (pixel1+pixel2+pixel3)//3
        return nuovo_Rosso
    elif i==0 and j==larghezza-1  and i!=altezza and j!=0:
        pixel1 = immagine.getPixel(j,i+1)
        pixel1 = pixel1.getRed()
        pixel2 = immagine.getPixel(j-1,i)
        pixel2 = pixel2.getRed()
        nuovo_Rosso = (pixel1+pixel2)//2
        return nuovo_Rosso
    elif j==0 and i!=altezza-1 and j!=larghezza-1 and i!=0:
        pixel1 = immagine.getPixel(j+1,i)
        pixel1 = pixel1.getRed()
        pixel2 = immagine.getPixel(j,i+1)
        pixel2 = pixel2.getRed()
        pixel3 = immagine.getPixel(j,i-1)
        pixel3 = pixel3.getRed()
        nuovo_Rosso = (pixel1+pixel2+pixel3)//3
        return nuovo_Rosso
    elif j==larghezza-1 and i!=altezza-1 and j!=larghezza and i!=0:
        pixel1 = immagine.getPixel(j-1,i)
        pixel1 = pixel1.getRed()
        pixel2 = immagine.getPixel(j,i+1)
        pixel2 = pixel2.getRed()
        pixel3 = immagine.getPixel(j,i-1)
        pixel3 = pixel3.getRed()
        nuovo_Rosso = (pixel1+pixel2+pixel3)//3
        return nuovo_Rosso
    elif j==0 and i==altezza-1 and i!=0 and j!=larghezza-1:
        pixel1 = immagine.getPixel(j+1,i)
        pixel1 = pixel1.getRed()
        pixel2 = immagine.getPixel(j,i-1)
        pixel2 = pixel2.getRed()
        nuovo_Rosso = (pixel1+pixel2)//2
        return nuovo_Rosso
    
    elif i==altezza-1 and j!=larghezza-1 and i!=0 and j!=0:
        pixel1 = immagine.getPixel(j-1,i)
        pixel1 = pixel1.getRed()
        pixel2 = immagine.getPixel(j+1,i)
        pixel2 = pixel2.getRed()
        pixel3 = immagine.getPixel(j,i-1)
        pixel3 = pixel3.getRed()
        nuovo_Rosso = (pixel1+pixel2+pixel3)//3
        return nuovo_Rosso
        
    elif i==altezza-1 and j==larghezza-1 and i!=0 and j!=0:
        pixel1 = immagine.getPixel(j-1,i)
        pixel1 = pixel1.getRed()
        pixel2 = immagine.getPixel(j,i-1)
        pixel2 = pixel2.getRed()
        nuovo_Rosso = (pixel1+pixel2)//2
        return nuovo_Rosso
    return nuovo_Rosso

def controlliGreen(altezza,larghezza,immagine,i,j,d):
    if j!=0 and i!=0 and i!=altezza-1 and j!=larghezza-1:
        pixel1 = immagine.getPixel(j+1,i)
        pixel1 = pixel1.getGreen()
        
        pixel2 = immagine.getPixel(j,i+1)
        pixel2 = pixel2.getGreen()
        pixel3 = immagine.getPixel(j-1,i)
        pixel3 = pixel3.getGreen()
        pixel4 = immagine.getPixel(j,i-1)
        pixel4 = pixel4.getGreen()
        nuovo_Verde = (pixel1+pixel2+pixel3+pixel4)//d
        return nuovo_Verde
    elif j==0 and i==0  :
        pixel1= immagine.getPixel(j+1,i)
        pixel1 = pixel1.getGreen()
        pixel2 = immagine.getPixel(j,i+1)
        pixel2 = pixel2.getGreen()
        nuovo_Verde = (pixel1+pixel2)//2
        return nuovo_Verde
    elif i==0 and i!=altezza-1 and j!=larghezza-1 and j!=0:
        pixel1 = immagine.getPixel(j+1,i)
        pixel1 = pixel1.getGreen()
        pixel2 = immagine.getPixel(j,i+1)
        pixel2 = pixel2.getGreen()
        pixel3 = immagine.getPixel(j-1,i)
        pixel3 = pixel3.getGreen()
        nuovo_Verde = (pixel1+pixel2+pixel3)//3
        return nuovo_Verde
    elif i==0 and j==larghezza-1  and i!=altezza and j!=0:
        pixel1 = immagine.getPixel(j,i+1)
        pixel1 = pixel1.getGreen()
        pixel2 = immagine.getPixel(j-1,i)
        pixel2 = pixel2.getGreen()
        nuovo_Verde = (pixel1+pixel2)//2
        return nuovo_Verde
    elif j==0 and i!=altezza-1 and j!=larghezza-1 and i!=0:
        pixel1 = immagine.getPixel(j+1,i)
        pixel1 = pixel1.getGreen()
        pixel2 = immagine.getPixel(j,i+1)
        pixel2 = pixel2.getGreen()
        pixel3 = immagine.getPixel(j,i-1)
        pixel3 = pixel3.getGreen()
        nuovo_Verde = (pixel1+pixel2+pixel3)//3
        return nuovo_Verde
    elif j==larghezza-1 and i!=altezza-1 and j!=larghezza and i!=0:
        pixel1 = immagine.getPixel(j-1,i)
        pixel1 = pixel1.getGreen()
        pixel2 = immagine.getPixel(j,i+1)
        pixel2 = pixel2.getGreen()
        pixel3 = immagine.getPixel(j,i-1)
        pixel3 = pixel3.getGreen()
        nuovo_Verde = (pixel1+pixel2+pixel3)//3
        return nuovo_Verde
    elif j==0 and i==altezza-1 and i!=0 and j!=larghezza-1:
        pixel1 = immagine.getPixel(j+1,i)
        pixel1 = pixel1.getGreen()
        pixel2 = immagine.getPixel(j,i-1)
        pixel2 = pixel2.getGreen()
        nuovo_Verde = (pixel1+pixel2)//2
        return nuovo_Verde
    
    elif i==altezza-1 and j!=larghezza-1 and i!=0 and j!=0:
        pixel1 = immagine.getPixel(j-1,i)
        pixel1 = pixel1.getGreen()
        pixel2 = immagine.getPixel(j+1,i)
        pixel2 = pixel2.getGreen()
        pixel3 = immagine.getPixel(j,i-1)
        pixel3 = pixel3.getGreen()
        nuovo_Verde = (pixel1+pixel2+pixel3)//3
        return nuovo_Verde
        
    elif i==altezza-1 and j==larghezza-1 and i!=0 and j!=0:
        pixel1 = immagine.getPixel(j-1,i)
        pixel1 = pixel1.getGreen()
        pixel2 = immagine.getPixel(j,i-1)
        pixel2 = pixel2.getGreen()
        nuovo_Verde = (pixel1+pixel2)//2
        return nuovo_Verde
    return nuovo_Verde
def controlliBlue(altezza,larghezza,immagine,i,j,d):
    if j!=0 and i!=0 and i!=altezza-1 and j!=larghezza-1:
        pixel1 = immagine.getPixel(j+1,i)
        pixel1 = pixel1.getBlue()
        
        pixel2 = immagine.getPixel(j,i+1)
        pixel2 = pixel2.getBlue()
        pixel3 = immagine.getPixel(j-1,i)
        pixel3 = pixel3.getBlue()
        pixel4 = immagine.getPixel(j,i-1)
        pixel4 = pixel4.getBlue()
        nuovo_Blue = (pixel1+pixel2+pixel3+pixel4)//d
        return nuovo_Blue
    elif j==0 and i==0  :
        pixel1= immagine.getPixel(j+1,i)
        pixel1 = pixel1.getBlue()
        pixel2 = immagine.getPixel(j,i+1)
        pixel2 = pixel2.getBlue()
        nuovo_Blue = (pixel1+pixel2)//2
        return nuovo_Blue
    elif i==0 and i!=altezza-1 and j!=larghezza-1 and j!=0:
        pixel1 = immagine.getPixel(j+1,i)
        pixel1 = pixel1.getBlue()
        pixel2 = immagine.getPixel(j,i+1)
        pixel2 = pixel2.getBlue()
        pixel3 = immagine.getPixel(j-1,i)
        pixel3 = pixel3.getBlue()
        nuovo_Blue = (pixel1+pixel2+pixel3)//3
        return nuovo_Blue
    elif i==0 and j==larghezza-1  and i!=altezza and j!=0:
        pixel1 = immagine.getPixel(j,i+1)
        pixel1 = pixel1.getBlue()
        pixel2 = immagine.getPixel(j-1,i)
        pixel2 = pixel2.getBlue()
        nuovo_Blue = (pixel1+pixel2)//2
        return nuovo_Blue
    elif j==0 and i!=altezza-1 and j!=larghezza-1 and i!=0:
        pixel1 = immagine.getPixel(j+1,i)
        pixel1 = pixel1.getBlue()
        pixel2 = immagine.getPixel(j,i+1)
        pixel2 = pixel2.getBlue()
        pixel3 = immagine.getPixel(j,i-1)
        pixel3 = pixel3.getBlue()
        nuovo_Blue = (pixel1+pixel2+pixel3)//3
        return nuovo_Blue
    elif j==larghezza-1 and i!=altezza-1 and j!=larghezza and i!=0:
        pixel1 = immagine.getPixel(j-1,i)
        pixel1 = pixel1.getBlue()
        pixel2 = immagine.getPixel(j,i+1)
        pixel2 = pixel2.getBlue()
        pixel3 = immagine.getPixel(j,i-1)
        pixel3 = pixel3.getBlue()
        nuovo_Blue = (pixel1+pixel2+pixel3)//3
        return nuovo_Blue
    elif j==0 and i==altezza-1 and i!=0 and j!=larghezza-1:
        pixel1 = immagine.getPixel(j+1,i)
        pixel1 = pixel1.getBlue()
        pixel2 = immagine.getPixel(j,i-1)
        pixel2 = pixel2.getBlue()
        nuovo_Blue = (pixel1+pixel2)//2
        return nuovo_Blue
    
    elif i==altezza-1 and j!=larghezza-1 and i!=0 and j!=0:
        pixel1 = immagine.getPixel(j-1,i)
        pixel1 = pixel1.getBlue()
        pixel2 = immagine.getPixel(j+1,i)
        pixel2 = pixel2.getBlue()
        pixel3 = immagine.getPixel(j,i-1)
        pixel3 = pixel3.getBlue()
        nuovo_Blue = (pixel1+pixel2+pixel3)//3
        return nuovo_Blue
        
    elif i==altezza-1 and j==larghezza-1 and i!=0 and j!=0:
        pixel1 = immagine.getPixel(j-1,i)
        pixel1 = pixel1.getBlue()
        pixel2 = immagine.getPixel(j,i-1)
        pixel2 = pixel2.getBlue()
        nuovo_Blue = (pixel1+pixel2)//2
        return nuovo_Blue
    return nuovo_Blue
    
     
def trasformazione3(file_immagine):
    # carichiamo e mostriamo l'immagine originale
    immagine = cImage.Image(file_immagine)            
    larghezza = immagine.getWidth()
    altezza = immagine.getHeight()
    finestra = cImage.ImageWin("Immagine originale",larghezza,altezza)
    immagine.draw(finestra)

    # invochiamo la funzione che implementa l'operazione in modo da operare sull' immagine
    nuova_immagine = pixel_adiacenti(immagine)
    
    # mostiamo la nuova immagine cioè il risultato
    larghezza = nuova_immagine.getWidth()
    altezza = nuova_immagine.getHeight()
    finestra_2 = cImage.ImageWin("Nuova immagine",larghezza,altezza)
    nuova_immagine.draw(finestra_2)
    finestra_2.exitOnClick()
    finestra.exitOnClick()

import random
def dividi_In_4_parti_casuali(immagine):
    # in questo esempio restituiamo l'immagine divisa in 4 parti e in modo randomico ogni volta che
    # eseguiamo il programma si reggistra un ordine diverso, ma mai viene preso uno dei 4 quadranti, per due volte nella nuova_immagine. 
    larghezza = immagine.getWidth()
    altezza = immagine.getHeight()
    finestra_2 = cImage.ImageWin("Immagine divisa",larghezza,altezza)
    nuova_immagine=cImage.EmptyImage(larghezza,altezza)
    app1=cImage.EmptyImage(larghezza//2,altezza//2)
    app2=cImage.EmptyImage(larghezza//2,altezza//2)
    app3=cImage.EmptyImage(larghezza//2,altezza//2)
    app4=cImage.EmptyImage(larghezza//2,altezza//2)
    
    sotto_im=cImage.EmptyImage(larghezza//2,altezza//2)
    app1=primo(immagine,sotto_im)
    sotto_im=cImage.EmptyImage(larghezza//2,altezza//2)
    app2=secondo(immagine,sotto_im)
    sotto_im=cImage.EmptyImage(larghezza//2,altezza//2)
    app3=terzo(immagine,sotto_im)
    sotto_im=cImage.EmptyImage(larghezza//2,altezza//2)
    app4=quarto(immagine,sotto_im)
    
    
    casualità([app1,app2,app3,app4],finestra_2,larghezza,altezza)
    finestra_2.exitOnClick()
    return nuova_immagine
def casualità(img,finestra,larghezza,altezza):
    inserito=[]
    for i in range (24):
        rnd=random.randint(1,4)
        if rnd not in inserito:
            inserito.append(rnd)
        else:
            rnd1=random.randint(1,4)
            if rnd!=rnd1 and rnd1 not in inserito:
                inserito.append(rnd1)
            else:
                rnd2=random.randint(1,4)
                if rnd1!=rnd2 and rnd2 not in inserito:
                   inserito.append(rnd2)
                else:
                    rnd3=random.randint(1,4)
                    if rnd2!=rnd3 and rnd3 not in inserito:
                       inserito.append(rnd3)
                    else:
                        rnd4=random.randint(1,4)
                        if rnd3!=rnd4 and rnd4 not in inserito:
                            inserito.append(rnd4)     
       
    print("L'ordine iniziale della divisione in 4 parti della tua immagine che era [1,2,3,4],ora risulta casualmente cambiato in: ",inserito)
    for i in range(len(inserito)):
        if inserito[i]==1:
            img[i].setPosition(0,0)
            img[i].draw(finestra)
            ##print("Caso 1")
        if inserito[i]==2:
            img[i].setPosition(larghezza//2,0)
            img[i].draw(finestra)
            ##print("Caso 2")
        if inserito[i]==3:
            img[i].setPosition(0,altezza//2)
            img[i].draw(finestra)
            ##print("Caso 3")
        if inserito[i]==4:
            ##print("Caso 4")
            img[i].setPosition(larghezza//2,altezza//2)
            img[i].draw(finestra)

    
def primo(immagine,nuova_immagine):

    x=-1
    for i in range(0,immagine.getWidth()//2-1):
        x+=1
        y=0
        for j in range(0,immagine.getHeight()//2-1):
            pixel=immagine.getPixel(i,j)
            nuova_immagine.setPixel(x,y,pixel)
            y+=1
    return nuova_immagine
def secondo(immagine,nuova_immagine):
    x=-1
    for i in range(immagine.getWidth()//2,immagine.getWidth()-1):
        x+=1
        y=0
        for j in range(0,immagine.getHeight()//2-1):
            pixel=immagine.getPixel(i,j)
            nuova_immagine.setPixel(x,y,pixel)
            y+=1
    return nuova_immagine
def terzo(immagine,nuova_immagine):
    x=-1
    for i in range(0,immagine.getWidth()//2-1):
        x+=1
        y=0
        for j in range(immagine.getHeight()//2,immagine.getHeight()-1):
            pixel=immagine.getPixel(i,j)
            nuova_immagine.setPixel(x,y,pixel)
            y+=1
    return nuova_immagine
def quarto(immagine,nuova_immagine):
    x=-1
    for i in range(immagine.getWidth()//2,immagine.getWidth()-1):
        x+=1
        y=0
        for j in range(immagine.getHeight()//2,immagine.getHeight()-1):
            pixel=immagine.getPixel(i,j)
            nuova_immagine.setPixel(x,y,pixel)
            y+=1
    return nuova_immagine

            

def trasformazione4(file_immagine):
    # carichiamo e mostriamo l'immagine originale
    immagine = cImage.Image(file_immagine)
    larghezza = immagine.getWidth()
    altezza = immagine.getHeight()
    finestra = cImage.ImageWin("Immagine originale",larghezza,altezza)
    immagine.draw(finestra)
    nuova_immagine = dividi_In_4_parti_casuali(immagine)
    finestra.exitOnClick()
def operazione(immagine):
    # in questo esempio restituiamo l'immagine ribaltata sull' asse verticale o orizzontale
    
    x = str(input("Vuoi ribaltare l' immagine sull' asse orizzontale o verticale?\n"))
    if x=="asse orizzontale" or x=="orizzontale":
        larghezza = immagine.getWidth()
        altezza = immagine.getHeight()
        nuova_immagine = cImage.EmptyImage(larghezza,altezza)
        for i in range(altezza):
            for j in range(larghezza):
                pixel = immagine.getPixel(j,i)
                nuova_immagine.setPixel(j,(altezza-i)-1,pixel)
        return nuova_immagine
    elif x=="asse verticale" or x=="verticale":
        larghezza = immagine.getWidth()
        altezza = immagine.getHeight()
        nuova_immagine = cImage.EmptyImage(larghezza,altezza)
        for i in range(altezza):
            for j in range(larghezza):
                pixel = immagine.getPixel(j,i)
                nuova_immagine.setPixel((larghezza-j)-1,i,pixel)                            
                
        return nuova_immagine
def trasformazione5(file_immagine):
    # carichiamo e mostriamo l'immagine originale
    immagine = cImage.Image(file_immagine)            
    larghezza = immagine.getWidth()
    altezza = immagine.getHeight()
    finestra = cImage.ImageWin("Immagine originale",larghezza,altezza)
    immagine.draw(finestra)

    # invochiamo la funzione che implementa l'operazione, in modo da operare sull' immagine
    nuova_immagine = operazione(immagine)
    
    # mostiamo la nuova immagine cioè il risultato
    larghezza = nuova_immagine.getWidth()
    altezza = nuova_immagine.getHeight()
    finestra_2 = cImage.ImageWin("Nuova immagine",larghezza,altezza)
    nuova_immagine.draw(finestra_2)
    finestra_2.exitOnClick()
    finestra.exitOnClick()
import cImage 

def click_superiore_o_inferiore (immagine,finestra):
    # in questa funzione tramite un click in alto a sinistra o in basso a destra l' immagine
    # viene ritagliata in base alla parte inferiore o superiore della diagonale secondaria e in più
    # risulta cambiata la nuova immagine in scala di grigi
    larghezza = immagine.getWidth()
    altezza = immagine.getHeight()
    nuova_immagine = cImage.EmptyImage(larghezza,altezza)
    clicks = finestra.captureClicks(2)    
    x = clicks[0][0]
    y = clicks[1][1]
    print(clicks)
    if individua(x,y,altezza,larghezza):
        for  i in range(altezza):
            for j in range(larghezza-i-1):
                pixel = immagine.getPixel(j,i)
                nuovo_Pixel = (pixel.getRed() + pixel.getGreen() + pixel.getBlue()) // 3
                nuovo_Pixel = cImage.Pixel(nuovo_Pixel,nuovo_Pixel,nuovo_Pixel)
                nuova_immagine.setPixel(j,i,nuovo_Pixel)
        for  i in range(altezza):
            for j in range(larghezza-1,larghezza-i-1,-1):
                pixel = immagine.getPixel(j,i) 
                nuova_immagine.setPixel(j,i,pixel)        
        return nuova_immagine
    else:
        for  i in range(altezza):
            for j in range(larghezza-1,larghezza-i-1,-1):
                pixel = immagine.getPixel(j,i) 
                nuovo_Pixel = (pixel.getRed() + pixel.getGreen() + pixel.getBlue()) // 3
                nuovo_Pixel = cImage.Pixel(nuovo_Pixel,nuovo_Pixel,nuovo_Pixel)
                nuova_immagine.setPixel(j,i,nuovo_Pixel)
        for  i in range(altezza):
            for j in range(larghezza-i-1):
                pixel = immagine.getPixel(j,i)
                nuova_immagine.setPixel(j,i,pixel)        
        return nuova_immagine
            
def individua(x,y,alt,lar):
    for  i in range(alt):
        for j in range(lar-i-1):
            #print(i,"  ",j)
            if x==i and y==j:
                return True

    return False         
def trasformazione6(file_immagine):
    # carichiamo e mostriamo l'immagine originale
    immagine = cImage.Image(file_immagine)            
    larghezza = immagine.getWidth()
    altezza = immagine.getHeight()
    finestra = cImage.ImageWin("Immagine originale",larghezza,altezza)
    immagine.draw(finestra)

    # invochiamo la funzione che implementa l'operazione, in modo da operare sull' immagine
    nuova_immagine = click_superiore_o_inferiore (immagine,finestra)
    
    # mostiamo la nuova immagine cioè il risultato
    larghezza = nuova_immagine.getWidth()
    altezza = nuova_immagine.getHeight()
    finestra_2 = cImage.ImageWin("Nuova immagine",larghezza,altezza)
    nuova_immagine.draw(finestra_2)
    finestra_2.exitOnClick()
    finestra.exitOnClick()
    
        
def main():
    print("Homework modulo.1  Mattia Gatto corso_A Ingegneria informatica primo_anno.")
    n=str(input("Inserire il nome dell'immagine di estensione :.gif o .png;\n"))
    
    c=int(input("Inserire il numero di trasormazione da effettuare;\n"
                "1.Riduce la percentuale delle componenti di rosso,verde o blu;\n"
                "2.Trasforma la nuova immagine in toni di rosso,verde o blu;\n"
                "3.Costruisce un’immagine in cui la componente rossa(verde o blu) di ogni pixel è uguale alla media delle componenti rosse(verdi o blu) dei pixel adiacenti nell’immagine originale;\n"
                "4.Divide  l’immagine originale in 4 quadranti uguali e li ricompone in ordine diverso;\n"
                "5.Ruota in base alla diagonale orizzontale o verticale;\n"
                "6.Cliccando in alto a sinistra o in basso a destra l' immagine in quella zona cambierà effeto in scala di grigi;\n"))

    if c ==1:
        print(trasformazione1(n))
    elif c ==2:
        print (trasformazione2(n))
    elif c ==3:
        print (trasformazione3(n))
    elif c ==4:
        print (trasformazione4(n))
    elif c==5:
        print(trasformazione5(n))
    elif c==6:
        print(trasformazione6(n))
    else:
        while c!=1 or c!=2 or c!=3 or c!=4 or c!=5 or c!=6:
            c=int(input("inserire il numero di trasormazione da effettuare da 1 a 6;\n"))
            if c ==1:
                print(trasformazione1(n))
                break
            elif c ==2:
                print (trasformazione2(n))
                break
            elif c ==3:
                print (trasformazione3(n))
                break
            elif c ==4:
                print (trasformazione4(n))
                break
            elif c==5:
                print(trasformazione5(n))
                break
            elif c==6:
                print(trasformazione6(n))
                break

        
    s=str(input("Vuoi effettuare un' altra trasformazione?\n"
                "rispondere si se si vuole, no per chiudere il programma;\n"))
    if s=="NO" or s=="no" or s=="No":
       exit()  
    while s =="SI" or s=="si" or s=="Si":
        c=int(input("Inserire il numero di trasormazione da effettuare;\n"
                    "1.Riduce la percentuale delle componenti di rosso,verde o blu;\n"
                    "2.Trasforma la nuova immagine in toni di rosso,verde o blu;\n"
                    "3.Costruisce un’immagine in cui la componente rossa(verde o blu) di ogni pixel è uguale alla media delle componenti rosse(verdi o blu) dei pixel adiacenti nell’immagine originale;\n"
                    "4.Divide  l’immagine originale in 4 quadranti uguali e li ricompone in ordine diverso;\n"
                    "5.Ruota in base alla diagonale orizzontale o verticale;\n"
                    "6.Cliccando in alto a sinistra o in basso a destra l' immagine in quella zona cambierà effeto in scala di grigi;\n"))
        if c ==1:
            print(trasformazione1(n))
        elif c ==2:
            print (trasformazione2(n))
        elif c ==3:
            print (trasformazione3(n))
        elif c ==4:
            print (trasformazione4(n))
        elif c==5:
            print(trasformazione5(n))
        elif c==6:
            print(trasformazione6(n))
        else:
            while c!=1 or c!=2 or c!=3 or c!=4 or c!=5 or c!=6:    
                c=int(input("inserire il numero di trasormazione da effettuare da 1 a 6;\n"))
                if c ==1:
                    print(trasformazione1(n))
                    break
                elif c ==2:
                    print (trasformazione2(n))
                    break
                elif c ==3:
                    print (trasformazione3(n))
                    break
                elif c ==4:
                    print (trasformazione4(n))
                    break
                elif c==5:
                    print(trasformazione5(n))
                    break
                elif c==6:
                    print(trasformazione6(n))
                    break
        s=str(input("Vuoi effettuare un' altra trasformazione?\n"
                    "rispondere si se si vuole, no per chiudere il programma;\n")) 
        if s=="NO" or s=="no" or s=="No":
           exit()
        
main()
