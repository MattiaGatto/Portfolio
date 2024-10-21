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

dataset	equ	8
n equ 12
k	equ 16
u_p equ 20
v_p equ 24
pack equ 4
dim equ 4

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

		MOV EAX,[EBP+dataset] ; ds
		MOV EBX,[EBP+n] ; n
		MOV ECX,[EBP+k] ; k
		MOV EDX,[EBP+u_p] ; &u_p[i]
		MOV ESI,[EBP+v_p] ; v_p
		MOV EDI,0; i*k
		PUSH EBP ; mi servono variabili
		MOV EBP,0; j
		IMUL EBX,ECX; n*k

.for_i:
		MOVSS XMM0,[EDX] ;u_p[i]
		SHUFPS XMM0,XMM0,00_00_00_00b ; riempio 4 spazi con lo stesso float
		MOV EBP,0; j = 0
.for_j:
		MOVAPS XMM1,[ESI+EBP*dim] ;v_p[j]
		MULPS XMM1,XMM0 ; v_p[j]*u_p[i]
		ADD EDI,EBP; i*k = i*k+j
		MOVAPS XMM2,[EAX+EDI*dim] ;ds[i*k+j]
		SUBPS XMM2,XMM1 ; ds[i*k+j] -= v_p[j]*u_p[i]
		MOVAPS [EAX+EDI*dim],XMM2 ; oldds = newds
		SUB EDI,EBP; i*k+j = i*k
		ADD EBP,pack
		CMP EBP,ECX; j<k?
		JL .for_j
		ADD EDI,ECX; (i*k) += k
		ADD EDX,dim ; &u_p[i] = &u_p[i+1]
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

p	equ	8
q equ 12
k	equ 16
res equ 20

pack equ 4
dim equ 4

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

		MOV EAX,[EBP+p] ; p
		MOV EBX,[EBP+q] ; q
		MOV ECX,[EBP+k] ; k
		MOV EDX,[EBP+res] ; &res
		PUSH EBP ; variabili
		MOV EBP,0;
		XORPS XMM2,XMM2; azzero inizialmente il registro dove accumulo la somma

.for_i:
		MOVAPS XMM0,[EAX+EBP*dim] ;p[i]
		MOVAPS XMM1,[EBX+EBP*dim] ;q[i]
		SUBPS XMM0,XMM1; p[i]-q[i]
		MULPS XMM0,XMM0 ; (p[i]-q[i])*(p[i]-q[i])
		ADDPS XMM2,XMM0 ;
		ADD EBP,pack
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

vector equ 8
nv equ 12
const equ 16
res equ 20
pack equ 4
dim equ 4

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

	MOV EAX,[EBP+vector] ; vector
	MOV EBX,[EBP+nv] ; nv
	MOVSS XMM0,[EBP+const] ; const
	SHUFPS XMM0,XMM0,00_00_00_00b ; copio il valore nelle altre caselle
	MOV ECX, [EBP+res] ; res

	XOR EDX, EDX ; i=0

.for_i:
<<<<<<< HEAD
	;XORPS XMM1, XMM1 ; forse si può evitare ma volevo essere sicuro
	MOVAPS XMM1,[EAX+EDX*dim] ; vector[i]
	DIVPS XMM1,XMM0 ; vector[i] / costante
	MOVAPS [ECX+EDX*dim],XMM1 ; res[i] = valore uscito dalla divisione
	ADD EDX, pack ; i++
	CMP EDX,EBX; i<nv ?
=======
	XORPS XMM1, XMM1 ; forse si può evitare ma volevo essere sicuro
	MOVAPS XMM1,[EAX+ESI*dim] ; vector[i]
	DIVPS XMM1,XMM0 ; vector[i] / costante
	MOVAPS [EDI+ESI*dim],XMM1 ; res[i] = valore uscito dalla divisione
	ADD ESI, pack ; i++
	CMP ESI,EBX; i<nv ?
>>>>>>> ffd519d438fbe2f177ef96c83337caf8fc98789d
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
