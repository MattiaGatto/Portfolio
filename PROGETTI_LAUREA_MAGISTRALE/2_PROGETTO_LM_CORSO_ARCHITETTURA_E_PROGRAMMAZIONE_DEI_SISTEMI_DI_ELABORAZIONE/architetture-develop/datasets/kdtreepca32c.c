/**************************************************************************************
*
* CdL Magistrale in Ingegneria Informatica
* Corso di Architetture e Programmazione dei Sistemi di Elaborazione - a.a. 2018/19
*
* Progetto dell'algoritmo di Product Quantization for Nearest Neighbor Search
* in linguaggio assembly x86-32 + SSE
*
* Fabrizio Angiulli, aprile 2019
*
**************************************************************************************/

/*
*
* Software necessario per l'esecuzione:
*
*    NASM (www.nasm.us)
*    GCC (gcc.gnu.org)
*
* entrambi sono disponibili come pacchetti software
* installabili mediante il packaging tool del sistema
* operativo; per esempio, su Ubuntu, mediante i comandi:
*
*    sudo apt-get install nasm
*    sudo apt-get install gcc
*
* potrebbe essere necessario installare le seguenti librerie:
*
*    sudo apt-get install lib32gcc-4.8-dev (o altra versione)
*    sudo apt-get install libc6-dev-i386
*
* Per generare il file eseguibile:
*
* nasm -f elf32 kdtreepca32.nasm && gcc -O0 -m32 -msse kdtreepca32.o kdtreepca32c.c -o kdtreepca32c && ./kdtreepca32c
*
* oppure
*
* ./runkdtreepca32
*
*/

#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <string.h>
#include <time.h>
#include <libgen.h>
#include <xmmintrin.h>

#define	MATRIX		float*
#define	KDTREE		node_t* // modificare con il tipo di dato utilizzato
typedef struct node node_t;
struct node{
    float* point;
    int id;
    node_t* leftchild;
    node_t* rightchild;
};
typedef struct {
    char* filename; //nome del file, estensione .ds per il data set, estensione .qs per l'eventuale query set
    MATRIX ds; //data set
    MATRIX qs; //query set
    int n; //numero di punti del data set
    int k; //numero di dimensioni del data/query set
    int nq; //numero di punti del query set
    int h; //numero di componenti principali da calcolare 0 se PCA non richiesta
    int kdtree_enabled; //1 per abilitare la costruzione del K-d-Tree, 0 altrimenti
    KDTREE kdtree; //riferimento al K-d-Tree, NULL se costruzione non richiesta
    float r; //raggio di query, -1 se range query non richieste
    int silent; //1 per disabilitare le stampe, 0 altrimenti
    int display; //1 per stampare i risultati, 0 altrimenti
    MATRIX U; //matrice U restituita dall'algoritmo PCA
    MATRIX V; //matrice V restituita dall'algoritmo PCA

    //STRUTTURE OUTPUT MODIFICABILI
    int* QA; //risposte alle query in forma di coppie di interi (id_query, id_vicino)
    int nQA; //numero di risposte alle query
} params;

/*
*
*	Le funzioni sono state scritte assumento che le matrici siano memorizzate
* 	mediante un array (float*), in modo da occupare un unico blocco
* 	di memoria, ma a scelta del candidato possono essere
* 	memorizzate mediante array di array (float**).
*
* 	In entrambi i casi il candidato dovrà inoltre scegliere se memorizzare le
* 	matrici per righe (row-major order) o per colonne (column major-order).
*
* 	L'assunzione corrente è che le matrici siano in row-major order.
*
*/


void* get_block(int size, int elements) {
    return _mm_malloc(elements*size,16);
}


void free_block(void* p) {
    _mm_free(p);
}


MATRIX alloc_matrix(int rows, int cols) {
    return (MATRIX) get_block(sizeof(float),rows*cols);
}


void dealloc_matrix(MATRIX mat) {
    free_block(mat);
}


/*
*
* 	load_data
* 	=========
*
*	Legge da file una matrice di N righe
* 	e M colonne e la memorizza in un array lineare in row-major order
*
* 	Codifica del file:
* 	primi 4 byte: numero di righe (N) --> numero intero a 32 bit
* 	successivi 4 byte: numero di colonne (M) --> numero intero a 32 bit
* 	successivi N*M*4 byte: matrix data in row-major order --> numeri floating-point a precisione singola
*
*****************************************************************************
*	Se lo si ritiene opportuno, è possibile cambiare la codifica in memoria
* 	della matrice.
*****************************************************************************
*
*/
MATRIX load_data(char* filename, int *n, int *k) {
    FILE* fp;
    int rows, cols, status, i;

    fp = fopen(filename, "rb");

    if (fp == NULL){
        printf("'%s': bad data file name!\n", filename);
        exit(0);
    }

    status = fread(&cols, sizeof(int), 1, fp);
    status = fread(&rows, sizeof(int), 1, fp);

    MATRIX data = alloc_matrix(rows,cols);
    status = fread(data, sizeof(float), rows*cols, fp);
    fclose(fp);

    *n = rows;
    *k = cols;

    return data;
}

/*
*
* 	save_data
* 	=========
*
*	Salva su file un array lineare in row-major order
*	come matrice di N righe e M colonne
*
* 	Codifica del file:
* 	primi 4 byte: numero di righe (N) --> numero intero a 32 bit
* 	successivi 4 byte: numero di colonne (M) --> numero intero a 32 bit
* 	successivi N*M*4 byte: matrix data in row-major order --> numeri interi o floating-point a precisione singola
*
*/
void save_data(char* filename, void* X, int n, int k) {
    FILE* fp;
    int i;
    fp = fopen(filename, "wb");
    if(X != NULL){
        fwrite(&k, 4, 1, fp);
        fwrite(&n, 4, 1, fp);
        for (i = 0; i < n; i++) {
            fwrite(X, 4, k, fp);
            //printf("%i %i\n", ((int*)X)[0], ((int*)X)[1]);
            X += 4*k;
        }
    }
    fclose(fp);
}



// PROCEDURE ASSEMBLY
extern void prova(params* input);
extern void subMatAssembly32(MATRIX dataset,int n, int k, MATRIX u_p, MATRIX v_p);
extern void euclidean_distance32(float* p, float* q, int k,float *res);
extern void dividiMatAssembly32(MATRIX vector, int nv, float costante, MATRIX res);

/*
* Stampa DS
*/
void stampa(params* input){
  for(int i = 0; i<2;i++){
    for(int j = 0; j<input->k;j++){
      printf("%f;",input->ds[i*input->k+j]);
    }
    printf("\n");
  }
}
/*
*  Prodotto tra matrici
*
*/

MATRIX prodMatrix(MATRIX matrix1, MATRIX matrix2, int n1,int k1n2, int k2){
  MATRIX res = alloc_matrix(n1,k2);
  for(int i=0; i<n1; i++){
    for (int j=0; j<k2;j++){
      float sum = 0.0;
      for(int k = 0; k<k1n2; k++){
        sum+=matrix1[i*k1n2+k]*matrix2[j*k1n2+k];
      }
      res[i*k2+j] = sum;
    }
  }
  return res;
}


void dividiMat(MATRIX vector, int nv, float costante, MATRIX res){
  for(int i = 0; i<nv; i++){
    res[i] = vector[i]/costante;
  }
}

void subMat(MATRIX ds,int n,int k,MATRIX u_p,MATRIX v_p){
  for(int i = 0; i < n; i++){
    for(int j = 0; j < k; j++){
      float newval = ds[i*k+j]- u_p[i]*v_p[j];
      ds[i*k+j] = newval;
    }
  }
}

void trasponi(MATRIX ds, MATRIX ds_t, int n, int k){
  for(int i = 0; i < n; i++){
    for(int j = 0; j < k; j++){
      ds_t[j*n+i] = ds[i*k+j];
    }
  }
}

/*
*	PCA
* 	=====================
*/
void pca(params* input) {

    // -------------------------------------------------
    // Codificare qui l'algoritmo PCA
    // -------------------------------------------------
    prova(input);
    // Calcola le matrici U e V
    // -------------------------------------------------
    MATRIX ds = input->ds;
    int k = input->k;
    int n = input->n;
    MATRIX ds_t = alloc_matrix(k,n);
    int h = input->h;
    float r = input->r;
    int nq = input->nq;
    MATRIX qs = input->qs;
    float theta = 1e-8;

    //Centriamo la media di D
    for(int j = 0; j < k; j++){
      float sum = 0.0;
      for (int i = 0; i<n; i++){
        sum += ds[i*k+j];
      }
      sum = sum / n;
      for (int i = 0; i<n; i++){
        float newval = ds[i*k+j] - sum;
        ds[i*k+j] = newval;
        ds_t[j*n+i] = newval;
      }
      if(r >= 0){
        for (int i = 0; i< nq; i++){
          qs[i*k+j] -= sum;
        }
      }
    }
    //Fine di centrare la media di D

    //Allocazione U e V
    MATRIX u_g = alloc_matrix(n, h);
    MATRIX v_g = alloc_matrix(h, k); //memorizzata per colonne
    MATRIX u_p = alloc_matrix(1, n);
    MATRIX v_p = alloc_matrix(1, k);

    //sia u_p la prima colonna di ds
    for (int i = 0; i<n ; i++){
      u_p[i] = ds_t[i];
    }
    int j = 0;
    //Vettore dei load e normalizzazione
    while(j<h){
      MATRIX temporaneo = prodMatrix(u_p,u_p,1,n,1);
      float u_n= temporaneo[0];
      dealloc_matrix(temporaneo);
      temporaneo = prodMatrix(ds_t,u_p,k,n,1);
      dividiMatAssembly32(temporaneo,k,u_n,v_p);  //Vettore dei load
      dealloc_matrix(temporaneo);
      temporaneo = prodMatrix(v_p,v_p,1,k,1);
      float v_n= temporaneo[0];
      dealloc_matrix(temporaneo);
      dividiMatAssembly32(v_p,k,sqrtf(v_n),v_p);
      float t = u_n;
      temporaneo = prodMatrix(v_p,v_p,1,k,1);
      v_n = temporaneo[0];
      dealloc_matrix(temporaneo);
      temporaneo = prodMatrix(ds,v_p,n,k,1);
      dividiMatAssembly32(temporaneo,n,v_n,u_p);
      dealloc_matrix(temporaneo);
      temporaneo = prodMatrix(u_p,u_p,1,n,1);
      float t_first = temporaneo[0];
      dealloc_matrix(temporaneo);
      if( fabsf(t_first-t) >= theta*t_first){
        continue;
      }

      //inserisci U come j-esima colonna di U
      for (int i = 0; i<n ; i++){
        u_g[i*h+j] = u_p[i];
      }
      for (int i = 0; i<k ; i++){
        v_g[j*k+i] = v_p[i];
      }
      subMatAssembly32(ds,n,k,u_p,v_p);
      //subMat(ds,n,k,u_p,v_p); // vecchia subMat
      trasponi(ds,ds_t,n,k);
      j++;
    }
    //restituire i risultati
    input->U = u_g;
    input->V = v_g;
    dealloc_matrix(u_p);
    dealloc_matrix(v_p);
    dealloc_matrix(ds_t);
}

/*
*	K-d-Tree
* 	======================
*/

int floatcmp(const void* p1, const void* p2){
  float f1 = *(const float*)p1;
  float f2 = *(const float*)p2;
  return (f1>f2)-(f1<f2);
}

//calcolo mediano
float mediano(MATRIX m, int n, int c,int k){
  MATRIX array = alloc_matrix(n,1);
  for(int i = 0; i < n; i++){
    array[i] = m[i*k+c];
  }

  qsort(array,n,sizeof(float),floatcmp);
  float res = array[n/2];
  dealloc_matrix(array);
  return res;//poi vediamo se randomico
}


//k-d-Tree ricorsivo
KDTREE kdtreeRic(MATRIX m, int k, int n, int l, MATRIX indici){
  if (n==0){
    return NULL;
  }
  int c = l%k;
  float valp = mediano(m,n,c,k);
  float* p = alloc_matrix(1,k);
  int id = -1;
  MATRIX D1 = alloc_matrix(n,k);
  MATRIX D2 = alloc_matrix(n,k);
  MATRIX indici_D1 = alloc_matrix(n,1);
  MATRIX indici_D2 = alloc_matrix(n,1);
  int n_d1 =0;
  int n_d2 =0;
  int mediano_vuoto=1;
  for(int i=0; i<n;++i){
    if(m[i*k+c] < valp){
      for(int ii = 0; ii<k; ii++){
        D1[n_d1*k+ii] = m[i*k+ii];
      }
      indici_D1[n_d1] = indici[i];
      n_d1++;
    }
    else if(m[i*k+c] == valp && mediano_vuoto){
      id=indici[i];
      for(int ii = 0; ii<k; ii++){
        p[ii]=m[i*k+ii];
      }
      mediano_vuoto = 0;
    }
    else {
      for(int ii = 0; ii<k; ii++){
        D2[n_d2*k+ii] = m[i*k+ii];
      }
      indici_D2[n_d2] = indici[i];
      n_d2++;
    }
  }
  KDTREE new_node=malloc(sizeof(node_t));
  new_node->point = p;
  new_node->id = id;
  new_node->leftchild = kdtreeRic(D1,k,n_d1,l+1,indici_D1);
  dealloc_matrix(D1);
  dealloc_matrix(indici_D1);
  new_node->rightchild = kdtreeRic(D2,k,n_d2,l+1,indici_D2);
  dealloc_matrix(D2);
  dealloc_matrix(indici_D2);
  return new_node;
}

void kdtree(params* input) {

    // -------------------------------------------------
    // Codificare qui l'algoritmo di costruzione
    // -------------------------------------------------
    MATRIX indici= alloc_matrix(input->n,1);
    for(int i = 0; i < input->n; i++){
      indici[i]=i;
    }
    if (input->h>0){
      input->kdtree=kdtreeRic(input->U,input->h,input->n,0,indici);
    }
    else{
      input->kdtree = kdtreeRic(input->ds,input->k,input->n,0,indici);
    }
    dealloc_matrix(indici);
}

/*
*	Range Query Search
* 	======================
*/

void depthFirstTest(KDTREE ds, int k, int level){
  if(ds != NULL){
    printf("livello=%d\n",level);
    for(int i = 0; i < k; i++){
      printf("%f,",ds->point[i]);
    }
    printf("\n");
    depthFirstTest(ds->leftchild,k,level+1);
    depthFirstTest(ds->rightchild,k,level+1);
  }
}
void test(){
  MATRIX mx = alloc_matrix(16,1);
  mx[0] = 0.1;
  mx[1] = -5.6;
  mx[2] = 1.4;
  mx[3] = -2.8;
  mx[4] = 45;
  mx[5] = 6.9;
  mx[6] = 18.1;
  mx[7] = 19;
  mx[8] = 10.1;
  mx[9] = -25.6;
  mx[10] = 31.4;
  mx[11] = -42.8;
  mx[12] = 0.45;
  mx[13] = 56.9;
  mx[14] = 18.12;
  mx[15] = 119;
  //KDTREE prova= kdtreeRic(mx,2,8,0);
  //depthFirstTest(prova,2,0);

}
/*
* ECLIDEAN DISTANCE
*
*/

void euclidean_distance(float* p, float* q, int k,float *res){
  float sum=0;
  for(int i = 0; i<k; i++){ // sqrt(sommatoria(q[j]-p[j])^2)
    sum += (p[i]-q[i]) * (p[i]-q[i]);
  }
  *res= sqrtf(sum);
}


void testE(){
  int i=0;
  MATRIX mx = alloc_matrix(16,1);
  mx[0] = 0.1;
  mx[1] = -5.6;
  mx[2] = 1.4;
  mx[3] = -2.8;
  mx[4] = 45;
  mx[5] = 6.9;
  mx[6] = 18.1;
  mx[7] = 19;
  mx[8] = 10.1;
  mx[9] = -25.6;
  mx[10] = 31.4;
  mx[11] = -42.8;
  mx[12] = 0.45;
  mx[13] = 56.9;
  mx[14] = 18.12;
  mx[15] = 119;

  MATRIX mx1 = alloc_matrix(16,1);
  /*mx[0] = 0.2;
<<<<<<< HEAD
  mx1[1] = -4.6;
  mx1[2] = 0.4;
  mx1[3] = -5.8;
  mx1[4] = 30;
  mx1[5] = 2.2;
  mx1[6] = 11.2;
  mx1[7] = 10;
  mx1[8] = 1.1;
  mx1[9] = -29.9;
  mx1[10] = 1.4;
  mx1[11] = -3.1;
  mx1[12] = 4.49;
  mx1[13] = 8,5;
  mx1[14] = -9.11;
  mx1[15] = 188.5;
=======
  mx[1] = -4.6;
  mx[2] = 0.4;
  mx[3] = -5.8;
  mx[4] = 30;
  mx[5] = 2.2;
  mx[6] = 11.2;
  mx[7] = 10;
  mx[8] = 1.1;
  mx[9] = -29.9;
  mx[10] = 1.4;
  mx[11] = -3.1;
  mx[12] = 4.49;
  mx[13] = 8,5;
  mx[14] = -9.11;
  mx[15] = 188.5;
>>>>>>> ffd519d438fbe2f177ef96c83337caf8fc98789d
  float res=0;
  euclidean_distance32(mx,mx1,16,&res);
  printf("res=%f\n", res);
  *///test euclidean32ASSEMBLY

  dividiMatAssembly32(mx, 16, 2.0, mx1);
  for(i=0;i<16;++i){
    printf("m[%d]=%f/2.0-->m[%d]=%f\n",i,mx[i],i,mx1[i]);
  }
}


/*
* DISTANCE *  |
*/
float distance(MATRIX q, MATRIX acca, int k){
  float* p = alloc_matrix(1,k);
  for (int j = 0; j<k; j++){
    float qj = q[j];
    if (qj <= acca[j*2]){
      p[j] = acca[j*2];
    }
    else if(qj>=acca[j*2+1]){
      p[j] = acca[j*2+1];
    }
    else{
      p[j] = qj;
    }
  }
  //float res = euclidean_distance(p, q, k);
  float res=0;
  euclidean_distance32(p, q, k,&res);
  dealloc_matrix(p);
  return res;
}

int range_query_punto(KDTREE kdtree,MATRIX q_primo,int qid,float r,MATRIX acca,
    int* qa,int base,int k, int l){
  if(distance(q_primo,acca,k)>r){
      return base;
  }
  MATRIX p = kdtree->point;
  //if(euclidean_distance(q_primo,p,k) <= r){
  float res=0;
  euclidean_distance32(q_primo,p,k,&res);
  if( res<= r){
    qa[base*2] = qid;
    qa[base*2+1] = kdtree->id;
    base++;
  }
  int c = l%k;
  float old_acca;
  if(kdtree->leftchild!=NULL){
    old_acca = acca[c*2+1];
    acca[c*2+1] = p[c];
    base = range_query_punto(kdtree->leftchild,q_primo,qid,r,acca,qa,base,k,l+1);
    acca[c*2+1]=old_acca;
  }
  if(kdtree->rightchild != NULL){
    old_acca = acca[c*2];
    acca[c*2] = p[c];
    base = range_query_punto(kdtree->rightchild,q_primo,qid,r,acca,qa,base,k,l+1);
    acca[c*2] = old_acca;
  }
  return base;
}

/*
  CREATE ACCA

*/
MATRIX createAcca(MATRIX ds, int n, int k){
  MATRIX acca = alloc_matrix(2,k);
  for(int i = 0; i < k; i++){
    //Calcolo min max
    float min = ds[i];
    float max = ds[i];
    for(int j = 0; j < n; j++){
      float cur = ds[j*k+i];
      if (max < cur){
        max = cur;
      }
      if (cur < min){
        min = cur;
      }
    }
    acca[i*2] = min;
    acca[i*2+1] = max;
  }
  return acca;
}
/*
  RANGE QUERY
*/
void range_query(params* input) {

    // -------------------------------------------------
    // Codificare qui l'algoritmo di ricerca
    // -------------------------------------------------

    // Calcola il risultato come una matrice di nQA coppie di interi
    // (id_query, id_vicino)
    // o in altro formato
    // -------------------------------------------------
    int nq = input->nq;
    float r = input->r;
    MATRIX qs = input->qs;
    MATRIX V = input->V;
    int h = input->h;
    int k = input->k;
    MATRIX ds = input->ds;
    MATRIX U = input->U;
    int n = input->n;
    int* qa = (int*) malloc(sizeof(int)*n*nq);
    MATRIX q = alloc_matrix(1,k);
    MATRIX acca;
    int nQA=0;
    if(h > 0){
      //versionePCA
      //costruzione acca con pca
      acca = createAcca(U,n,h);
      for(int i = 0; i < nq; i++){ //qua ci va nq al posto di 1 (i<nq)
        for(int j = 0; j < k; j++){ //copia il punto
          q[j] = qs[i*k+j];
        }
        MATRIX q_primo = prodMatrix(q,V,1,k,h);
        nQA = range_query_punto(input->kdtree,q_primo,i,r,acca,qa,nQA,h,0);
        dealloc_matrix(q_primo);
      }
    }
    else{
      //versioneNormale
      //Costruzione H senza PCA
      acca = createAcca(ds,n,k);
      for(int i = 0; i < nq; i++){
        for(int j = 0; j < k; j++){ //copia il punto
          q[j] = qs[i*k+j];
        }
        nQA = range_query_punto(input->kdtree,q,i,r,acca,qa,nQA,k,0);
      }
    }
    input->QA = qa;
    input->nQA = nQA;
    dealloc_matrix(q);
    dealloc_matrix(acca);
}

int main(int argc, char** argv) {
    //testE();
    char fname[256];
    char* dsname;
    int i, j, k;
    clock_t t;
    float time;

    //
    // Imposta i valori di default dei parametri
    //

    params* input = malloc(sizeof(params));
    input->filename = NULL;
    input->h = 0;
    input->kdtree = NULL;
    input->r = -1;
    input->silent = 0;
    input->display = 0;
    input->QA = NULL;
    input->nQA = 0;

    //
    // Visualizza la sintassi del passaggio dei parametri da riga comandi
    //

    if (argc <= 1 && !input->silent) {
        printf("Usage: %s <data_name> [-pca <h>] [-kdtree [-rq <r>]]\n", argv[0]);
        printf("\nParameters:\n");
        printf("\t-d: display query results\n");
        printf("\t-s: silent\n");
        printf("\t-pca <h>: h-component PCA enabled\n");
        printf("\t-kdtree: kdtree building enabled\n");
        printf("\t-rq <r>: range query search with radius r enabled\n");
        printf("\n");
        exit(0);
    }

    //
    // Legge i valori dei parametri da riga comandi
    //

    int par = 1;
    while (par < argc) {
        if (par == 1) {
            input->filename = argv[par];
            par++;
        } else if (strcmp(argv[par],"-s") == 0) {
            input->silent = 1;
            par++;
        } else if (strcmp(argv[par],"-d") == 0) {
            input->display = 1;
            par++;
        } else if (strcmp(argv[par],"-pca") == 0) {
            par++;
            if (par >= argc) {
                printf("Missing h value!\n");
                exit(1);
            }
            input->h = atoi(argv[par]);
            par++;
        } else if (strcmp(argv[par],"-kdtree") == 0) {
            input->kdtree_enabled = 1;
            par++;
            if (par < argc && strcmp(argv[par],"-rq") == 0) {
                par++;
                if (par >= argc) {
                    printf("Missing radius value!\n");
                    exit(1);
                }
                input->r = atof(argv[par]);
                if(input->r < 0){
                    printf("Range query radius must be non-negative!\n");
                    exit(1);
                }
                par++;
            }
        } else{
            printf("WARNING: unrecognized parameter '%s'!\n",argv[par]);
            par++;
        }
    }

    //
    // Legge i dati e verifica la correttezza dei parametri
    //

    if(input->filename == NULL || strlen(input->filename) == 0){
        printf("Missing input file name!\n");
        exit(1);
    }

    sprintf(fname, "%s.ds", input->filename);
    dsname = basename(strdup(input->filename));
    input->ds = load_data(fname, &input->n, &input->k);

    if(input->h < 0){
        printf("Invalid value of PCA parameter h!\n");
        exit(1);
    }
    if(input->h > input->k){
        printf("Value of PCA parameter h exceeds data set dimensions!\n");
        exit(1);
    }

    if(input->r >= 0){
        sprintf(fname, "%s.qs", input->filename);
        input->qs = load_data(fname, &input->nq, &k);
        if(input->k != k){
            printf("Data set dimensions and query set dimensions are not compatible!\n");
            exit(1);
        }
    }

    //
    // Visualizza il valore dei parametri
    //

    if(!input->silent){
        printf("Input file name: '%s'\n", input->filename);
        printf("Data set size [n]: %d\n", input->n);
        printf("Number of dimensions [k]: %d\n", input->k);
        if(input->h > 0){
            printf("PCA search enabled\n");
            printf("Number of principal components [h]: %i\n",input->h);
        }else{
            printf("PCA search disabled\n");
        }
        if(input->kdtree_enabled){
            printf("Kdtree building enabled\n");
            if(input->r >= 0){
                printf("Range query search enabled\n");
                printf("Range query search radius [r]: %f\n",input->r);
            }else{
                printf("Range query search disabled\n");
            }
        }else{
            printf("Kdtree building disabled\n");
        }
    }

    //
    // Calcolo PCA
    //

    if(input->h > 0){
        t = clock();
        pca(input);
        t = clock() - t;
        time = ((float)t)/CLOCKS_PER_SEC;
        sprintf(fname, "%s.U", dsname);
        sprintf(fname, "%s.V", dsname);
    }else
        time = -1;

    if (!input->silent)
        printf("\nPCA time = %.3f secs\n", time);
    else
        printf("%.3f\n", time);

    //
    // Costruzione K-d-Tree
    //

    if(input->kdtree_enabled){//qua prima c'era kdtree
        t = clock();
        kdtree(input);
        t = clock() - t;
        time = ((float)t)/CLOCKS_PER_SEC;
    }else
        time = -1;
    if (!input->silent)
        printf("\nIndexing time = %.3f secs\n", time);
    else
        printf("%.3f\n", time);

    //
    // Range query search
    //

    if(input->r >= 0){
        t = clock();
        range_query(input);
        t = clock() - t;
        time = ((float)t)/CLOCKS_PER_SEC;
    }else
        time = -1;
    if (!input->silent)
        printf("\nQuerying time = %.3f secs\n", time);
    else
        printf("%.3f\n", time);

    //
    // Salva il risultato delle query
    // da modificare se si modifica il formato delle matrici di output
    //

    if(input->r >= 0){
        if(!input->silent && input->display) {
            //NB: il codice non assume che QA sia ordinata per query, in caso lo sia ottimizzare il codice
            printf("\nQuery Answer:\n");
            for(i = 0; i < input->nq; i++){
                printf("query %d: [ ", i);
                for(j = 0; j < input->nQA; j++)
                    if(input->QA[j*2] == i)
                        printf("%d ", input->QA[j*2+1]);
                printf("]\n");
            }
            printf("\n");
        }
        sprintf(fname, "%s.qa", dsname);
        save_data(fname, input->QA, input->nQA, 2);
    }

    if (!input->silent)
        printf("\nDone.\n");

    return 0;
}


/*  COSE DA FARE IN ASSEMBLY E QUALI NO

prodmatrix sì (lo ha fatto fassetti, quindi copiamo da lui)
dividiMat sì (Alfredo)
subMat sì (Francesco)
euclidean_distance sì (Mattia)

createacca forse

range query no
pca non credo
distance no
range query punto no

*/
