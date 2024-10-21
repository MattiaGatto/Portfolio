; ---------------------------------------------------------
; PQNN con istruzioni SSE a 32 bit
; ---------------------------------------------------------
; F. Angiulli
; 23/11/2017
;

;
; Software necessario per l'esecuzione:
;
;     NASM (www.nasm.us)
;     GCC (gcc.gnu.org)
;
; entrambi sono disponibili come pacchetti software
; installabili mediante il packaging tool del sistema
; operativo; per esempio, su Ubuntu, mediante i comandi:
;
;     sudo apt-get install nasm
;     sudo apt-get install gcc
;
; potrebbe essere necessario installare le seguenti librerie:
;
;     sudo apt-get install lib32gcc-4.8-dev (o altra versione)
;     sudo apt-get install libc6-dev-i386
;
; Per generare file oggetto:
;
;     nasm -f elf32 pqnn32.nasm
;
%include "sseutils.nasm"

section .data			; Sezione contenente dati inizializzati

uno:		dd		1.0
;
;align 16
;inizio:		dd		1.0, 2.0, 3.0, 4.0

section .bss			; Sezione contenente dati non inizializzati

;alignb 16
;vec2:		resq	4

section .text			; Sezione contenente il codice macchina


; ----------------------------------------------------------
; macro per l'allocazione dinamica della memoria
;
;	getmem	<size>,<elements>
;
; alloca un'area di memoria di <size>*<elements> bytes
; (allineata a 16 bytes) e restituisce in EAX
; l'indirizzo del primo bytes del blocco allocato
; (funziona mediante chiamata a funzione C, per cui
; altri registri potrebbero essere modificati)
;
;	fremem	<address>
;
; dealloca l'area di memoria che ha inizio dall'indirizzo
; <address> precedentemente allocata con getmem
; (funziona mediante chiamata a funzione C, per cui
; altri registri potrebbero essere modificati)

extern get_block
extern free_block

%macro	getmem	2
	mov	eax, %1
	push	eax
	mov	eax, %2
	push	eax
	call	get_block
	add	esp, 8
%endmacro

%macro	fremem	1
	push	%1
	call	free_block
	add	esp, 4
%endmacro

; ------------------------------------------------------------
; Funzioni
; ------------------------------------------------------------

global prova

input		equ		8

msg	db	'n:',0
nl	db	10,0

prova:
		; ------------------------------------------------------------
		; Sequenza di ingresso nella funzione
		; ------------------------------------------------------------
		push		ebp							; salva il Base Pointer
		mov			ebp, esp					; il Base Pointer punta al Record di Attivazione corrente
		push		ebx							; salva i registri da preservare
		push		esi
		push		edi
		; ------------------------------------------------------------
		; legge i parametri dal Record di Attivazione corrente
		; ------------------------------------------------------------

		; elaborazione

		; esempio: stampa input->n e di input->k
		mov EAX, [EBP+input]	; indirizzo della struttura contenente i parametri
		; [EAX] contiene l'indirizzo della stringa con il nome del file
		; [EAX+4] contiene l'indirizzo di partenza del data set
		; [EAX+8] contiene l'indirizzo di partenza del query set
		prints msg
		printi dword[eax+12]	; a 12 byte dall'inizio della struct si trova n
		prints nl
		;printi dword[EAX+16]	; a 4 byte da n si trova k


		; ------------------------------------------------------------
		; Sequenza di uscita dalla funzione
		; ------------------------------------------------------------

		pop	edi									; ripristina i registri da preservare
		pop	esi
		pop	ebx
		mov	esp, ebp							; ripristina lo Stack Pointer
		pop	ebp									; ripristina il Base Pointer
		ret										; torna alla funzione C chiamante

; ------------------------------------------------------------
; Funzione submat
; ------------------------------------------------------------

global subMatAssembly32

dataset_sub	equ	8
n_sub equ 12
k_sub	equ 16
u_p_sub equ 20
v_p_sub equ 24
pack_sub equ 4
dim_sub equ 4

subMatAssembly32:
		; ------------------------------------------------------------
		; INGRESSO
		; ------------------------------------------------------------
		PUSH EBP
		MOV EBP, ESP
		PUSH EBX
		PUSH ESI
		PUSH EDI
		; ------------------------------------------------------------
		; ELABORAZIONE
		; ------------------------------------------------------------

		MOV EAX,[EBP+dataset_sub] ; ds
		MOV EBX,[EBP+n_sub] ; n
		MOV ECX,[EBP+k_sub] ; k
		MOV EDX,[EBP+u_p_sub] ; &u_p[i]
		MOV ESI,[EBP+v_p_sub] ; v_p
		XOR EDI,EDI; MOV EDI,0; i*k
		PUSH EBP ; mi servono variabili
		XOR EBP,EBP; MOV EBP,0; j
		IMUL EBX,ECX; n*k

.for_i:
		MOVSS XMM0,[EDX] ;u_p[i]
		SHUFPS XMM0,XMM0,00_00_00_00b ; riempio 4 spazi con lo stesso float
		XOR EBP,EBP;MOV EBP,0; j = 0
.for_j:
		MOVAPS XMM1,[ESI+EBP*dim_sub] ;v_p[j]
		MULPS XMM1,XMM0 ; v_p[j]*u_p[i]
		ADD EDI,EBP; i*k = i*k+j
		MOVAPS XMM2,[EAX+EDI*dim_sub] ;ds[i*k+j]
		SUBPS XMM2,XMM1 ; ds[i*k+j] -= v_p[j]*u_p[i]
		MOVAPS [EAX+EDI*dim_sub],XMM2 ; oldds = newds
		SUB EDI,EBP; i*k+j = i*k
		ADD EBP,pack_sub
		CMP EBP,ECX; j<k?
		JL .for_j
		ADD EDI,ECX; (i*k) += k
		ADD EDX,dim_sub ; &u_p[i] = &u_p[i+1]
		CMP EDI,EBX
		JL .for_i

		POP EBP
		; ------------------------------------------------------------
		; USCITA
		; ------------------------------------------------------------
		POP EDI
		POP ESI
		POP EBX
		MOV ESP, EBP
		POP	EBP
		RET
;------------------------------------------------------------
; Funzione euclidean_distance32
; ------------------------------------------------------------
global euclidean_distance32

p_eu	equ	8
q_eu equ 12
k_eu	equ 16
res_eu equ 20

pack_eu equ 4
dim_eu equ 4

euclidean_distance32:
		;; ------------------------------------------------------------
		; INGRESSO
		; ------------------------------------------------------------
		PUSH EBP
		MOV EBP, ESP
		PUSH EBX
		PUSH ESI
		PUSH EDI
		; ------------------------------------------------------------
		; ELABORAZIONE
		; ------------------------------------------------------------

		MOV EAX,[EBP+p_eu] ; p
		MOV EBX,[EBP+q_eu] ; q
		MOV ECX,[EBP+k_eu] ; k
		MOV EDX,[EBP+res_eu] ; &res
		PUSH EBP ; variabili
		XOR EBP,EBP;MOV EBP,0;
		XORPS XMM2,XMM2; azzero inizialmente il registro dove accumulo la somma

.for_i:
		MOVAPS XMM0,[EAX+EBP*dim_eu] ;p[i]
		MOVAPS XMM1,[EBX+EBP*dim_eu] ;q[i]
		SUBPS XMM0,XMM1; p[i]-q[i]
		MULPS XMM0,XMM0 ; (p[i]-q[i])*(p[i]-q[i])
		ADDPS XMM2,XMM0 ;
		ADD EBP,pack_eu
		CMP EBP,ECX; i<k?
		JL .for_i
		HADDPS XMM2,XMM2 ;sum
		HADDPS XMM2,XMM2 ;
		SQRTSS XMM2,XMM2
		MOVSS [EDX],XMM2 ;
		POP EBP
		; ------------------------------------------------------------
		; USCITA
		; ------------------------------------------------------------
		POP EDI
		POP ESI
		POP EBX
		MOV ESP, EBP
		POP	EBP
		RET
; ----------------------------------------------------------------
; DIVIDI MAT 32
; ----------------------------------------------------------------
global dividiMatAssembly32

vector_div equ 8
nv_div equ 12
const_div equ 16
res_div equ 20
pack_div equ 4
dim_div equ 4

dividiMatAssembly32:
	; ------------------------------------------------------------
	; INGRESSO
	; ------------------------------------------------------------
	PUSH EBP
	MOV EBP, ESP
	PUSH EBX
	PUSH ESI
	PUSH EDI
	; ------------------------------------------------------------
	; ELABORAZIONE
	; ------------------------------------------------------------
	MOV EAX,[EBP+vector_div] ; vector
	MOV EBX,[EBP+nv_div] ; nv
	MOVSS XMM0,[EBP+const_div] ; const
	SHUFPS XMM0,XMM0,00_00_00_00b ; copio il valore nelle altre caselle
	MOV EDI, [EBP+res_div] ; res

	XOR ESI, ESI ; i=0

.for_i:
	XORPS XMM1, XMM1 ; forse si può evitare ma volevo essere sicuro
	MOVAPS XMM1,[EAX+ESI*dim_div] ; vector[i]
	DIVPS XMM1,XMM0 ; vector[i] / costante
	MOVAPS [EDI+ESI*dim_div],XMM1 ; res[i] = valore uscito dalla divisione
	ADD ESI, pack_div ; i++
	CMP ESI,EBX; i<nv ?
	JL .for_i ; cicla

	; ------------------------------------------------------------
	; USCITA
	; ------------------------------------------------------------
	POP EDI
	POP ESI
	POP EBX
	MOV ESP, EBP
	POP	EBP
	RET

; ----------------------------------------------------------------
; PROD MAT 32
; ----------------------------------------------------------------
global prodMatrixAssembly32
;MATRIX matrix1, MATRIX matrix2, int n1,int k1n2, int k2, MATRIX res
pack_prod equ 4
dim_prod equ 4
m1_prod equ 8
m2_prod equ 12
n1_prod equ 16
k1n2_prod equ 20
k2_prod equ 24
res_prod equ 28

prodMatrixAssembly32:
	; ------------------------------------------------------------
	; INGRESSO
	; ------------------------------------------------------------
	PUSH EBP
	MOV EBP, ESP
	SUB ESP,dim_prod
	PUSH EBX
	PUSH ESI
	PUSH EDI
	; ------------------------------------------------------------
	; ELABORAZIONE
	; ------------------------------------------------------------


	;prodotto di matrice vero e proprio

	MOV ECX,[EBP+m1_prod];matrix1
	MOV EDX,[EBP+m2_prod];matrix2
	XOR EDI,EDI; i=0
	XOR ESI,ESI; j=0
	XOR EBX,EBX; k=0
	;continua...
.for_i:
	XOR ESI,ESI; j=0
.for_j:
	XORPS XMM0, XMM0 ; float sum = 0
	XOR EBX,EBX; k=0
.for_k:
	;dobbiamo calcolarci quell'indice di merda
	MOV EAX, EDI
	IMUL EAX, [EBP+k1n2_prod]
	ADD EAX, EBX ; i*k1n2+k in EAX
	MOVAPS XMM1,[ECX+EAX*dim_prod];matrix1[i*k1n2+k]
	MOV EAX,ESI
	IMUL EAX, [EBP+k1n2_prod]
	ADD EAX, EBX ; j+k1n2+k in EAX
	MOVAPS XMM2, [EDX+EAX*dim_prod];matrix2[j*k1n2+k]
	MULPS XMM1,XMM2; prodotto matrix1,matrix2
	ADDPS XMM0, XMM1 ; sum+=

	;Ciclo
	ADD EBX, pack_prod ; k++
	CMP EBX, [EBP+k1n2_prod]
	JL .for_k
	HADDPS XMM0,XMM0
	HADDPS XMM0,XMM0
	MOV EBX,EDI ;Utilizziamo EBX poichè k ha finito il ciclo
	IMUL EBX,[EBP+k2_prod]
	ADD EBX, ESI
	MOV EAX,[EBP+res_prod]
	MOVSS [EAX+EBX*dim_prod],XMM0
	INC ESI ; j++
	CMP ESI, [EBP+k2_prod]
	JL .for_j
	INC EDI ; i++
	CMP EDI, [EBP+n1_prod]
	JL .for_i
	;restituzione del risultato
	MOV EAX,[EBP+res_prod]

	; ------------------------------------------------------------
	; USCITA
	; ------------------------------------------------------------
	POP EDI
	POP ESI
	POP EBX
	ADD ESP,dim_prod
	MOV ESP, EBP
	POP	EBP
	RET
