; ---------------------------------------------------------
; PageRank con istruzioni AVX a 64 bit
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
;     nasm -f elf64 pagerank64.nasm
;

%include "sseutils64.nasm"

section .data			; Sezione contenente dati inizializzati

uno:		dd		1.0
;
;align 32
;vec1:		dd		1.0, 2.0, 3.0, 4.0

section .bss			; Sezione contenente dati non inizializzati

;alignb 32
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
	mov	rdi, %1
	mov	rsi, %2
	call	get_block
%endmacro

%macro	fremem	1
	mov	rdi, %1
	call	free_block
%endmacro

; ------------------------------------------------------------
; Funzione prova
; ------------------------------------------------------------
global prova

msg	db 'n:',0
nl	db 10,0

prova:
		; ------------------------------------------------------------
		; Sequenza di ingresso nella funzione
		; ------------------------------------------------------------
		push		rbp				; salva il Base Pointer
		mov		rbp, rsp			; il Base Pointer punta al Record di Attivazione corrente
		pushaq						; salva i registri generali

		; ------------------------------------------------------------
		; I parametri sono passati nei registri
		; ------------------------------------------------------------
		; rdi = indirizzo della struct input

		; esempio: stampa input->n e di input->k
		; rdi contiente l'indirizzo della struttura contenente i parametri
		; [rdi] contiene l'indirizzo della stringa con il nome del file
		; [rdi+8] contiene l'indirizzo di partenza del data set
		; [rdi+16] contiene l'indirizzo di partenza del query set
		movsx rax, dword[rdi+24]		; [rdi+16] contiene n
		prints msg
		printi rax
		prints nl
		;movsx rax, dword[rdi+28]		; a 4 byte da n si trova k
		;printi rax
		; ------------------------------------------------------------
		; Sequenza di uscita dalla funzione
		; ------------------------------------------------------------

		popaq						; ripristina i registri generali
		mov		rsp, rbp			; ripristina lo Stack Pointer
		pop		rbp					; ripristina il Base Pointer
		ret							; torna alla funzione C chiamante

; ------------------------------------------------------------
; Funzione subMat
; ------------------------------------------------------------
global subMatAssembly64

pack_sub equ 8
dim_sub equ 4

subMatAssembly64:
		; ------------------------------------------------------------
		; INGRESSO
		; ------------------------------------------------------------

		PUSH RBP
		MOV RBP,RSP
		pushaq

		; ------------------------------------------------------------
		; ELABORAZIONE
		; ------------------------------------------------------------

		;RDI=dataset,RSI=n,RDX=k,RCX=u_p,R8=v_p

		XOR R10,R10;MOV R10,0; i
		XOR R11,R11;MOV R11,0; j
		XOR R9,R9;MOV R9,0; i*k
		XOR R12,R12;MOV R12,0; i*k+j

.for_i:
		VBROADCASTSS YMM0,dword[RCX+R10*dim_sub] ;u_p[i]
		XOR R11,R11;MOV R11,0; j = 0
		MOV R12,R9; (i-1)*k+(k+resto) = i*k+0
.for_j:
		VMOVUPS YMM1,[R8+R11*dim_sub] ;v_p[j]
		VMULPS YMM1,YMM0 ; v_p[j]*u_p[i]
		VMOVUPS YMM2,[RDI+R12*dim_sub] ;ds[i*k+j]
		VSUBPS YMM2,YMM1 ; ds[i*k+j] -= v_p[j]*u_p[i]
		VMOVUPS [RDI+R12*dim_sub],YMM2 ; oldds = newds
		ADD R12,pack_sub ; j+=8
		ADD R11,pack_sub ; i*k+(j-1)=i*k+j
		CMP R11,RDX
		JL .for_j
		INC R10; i++
		ADD R9,RDX; (i-1)*k=i*k
		CMP R10,RSI
		JL .for_i

		; ------------------------------------------------------------
		; USCITA
		; ------------------------------------------------------------

		popaq
		MOV RSP, RBP
		POP RBP
		RET
; ------------------------------------------------------------
; Funzione euclidean_distance64
; ------------------------------------------------------------
global euclidean_distance64

pack_eu equ 8
dim_eu equ 4

euclidean_distance64:
		; ------------------------------------------------------------
		; INGRESSO
		; ------------------------------------------------------------

		PUSH RBP
		MOV RBP,RSP
		pushaq

		; ------------------------------------------------------------
		; ELABORAZIONE
		; ------------------------------------------------------------

		;RDI=P,RSI=Q,RDX=k,RCX=RES

		XOR R10,R10;MOV R10,0; i
		VXORPS YMM2,YMM2;
		VMOVUPS [RDI+RDX*dim_eu],YMM2
.for_i:
		VMOVAPS YMM0,[RDI+R10*dim_eu] ;u_p[i]
		VMOVAPS YMM1,[RSI+R10*dim_eu] ;u_p[i]
		VSUBPS YMM0,YMM1 ; p[i]-q[i]
		VMULPS YMM0,YMM0 ; (p[i]-q[i])*(p[i]-q[i])
		VADDPS YMM2,YMM0 ;
		ADD R10,pack_eu ; i*k+(j-1)=i*k+j
		CMP R10,RDX
		JL .for_i
		VPERM2F128 YMM3,YMM2,YMM2,0_0_00_0_0_0_01b
		VADDPS YMM2,YMM3
		VHADDPS YMM2,YMM2 ;sum
		VHADDPS YMM2,YMM2 ;
		VSQRTPS YMM2,YMM2
		VMOVSS [RCX],XMM2 ;

		; ------------------------------------------------------------
		; USCITA
		; ------------------------------------------------------------

		popaq
		MOV RSP, RBP
		POP RBP
		RET

; ------------------------------------------------------------
; Funzione dividiMatAssembly64
; ------------------------------------------------------------
global dividiMatAssembly64

	pack_div equ 8
	dim_div equ 4

	dividiMatAssembly64:
; ------------------------------------------------------------
; INGRESSO
; ------------------------------------------------------------

	PUSH RBP
	MOV RBP,RSP
	pushaq

; ------------------------------------------------------------
; ELABORAZIONE
; ------------------------------------------------------------

;RDI=vector,RSI=nv,RDX=const,RCX=res,R8=v_p

	VSHUFPS YMM0,YMM0,0
	VPERM2F128 YMM0,YMM0,YMM0,0
	XOR R10,R10; i
.for_i:
	VMOVAPS YMM1,[RDI+R10*dim_div] ;vector[i]
	VDIVPS YMM1,YMM0 ; vector[i]/const
	VMOVAPS [RDX+R10*dim_div],YMM1 ; res[i] = valore divisore
	ADD R10,pack_div ; i*k+(j-1)=i*k+j
	CMP R10,RSI
	JL .for_i

; ------------------------------------------------------------
; USCITA
; ------------------------------------------------------------

	popaq
	MOV RSP, RBP
	POP RBP
	RET

; ------------------------------------------------------------
; Funzione prodMatrixAssembly64
; ------------------------------------------------------------
global prodMatrixAssembly64

pack_prod equ 8
dim_prod equ 4

prodMatrixAssembly64:
		; ------------------------------------------------------------
		; INGRESSO
		; ------------------------------------------------------------

		PUSH RBP
		MOV RBP,RSP

		pushaq

		; ------------------------------------------------------------
		; ELABORAZIONE
		; ------------------------------------------------------------

		;RDI=matrix1,RSI=matrix2,RDX=n,RCX=K1n2,R8=k2,R9=res


		;prodotto di matrice vero e proprio
		XOR R10,R10; i=0
		XOR R11,R11; j=0
		XOR R12,R12; k=0

.for_i:
	XOR R11,R11; j=0
.for_j:
	VXORPS YMM0,YMM0
	XOR R12,R12; k=0
.for_k:
		MOV R13,R10 ;metto i
		IMUL R13,RCX ;i*K1n2
		ADD R13,R12 ;i*k1n2+k
		VMOVUPS YMM1,[RDI+R13*dim_prod] ;matrix1[i*k1n2+k]
		MOV R13,R11 ; metto j
		IMUL R13,RCX ; j*K1n2
		ADD R13,R12 ;j*k1n2+k
		VMOVUPS YMM2,[RSI+R13*dim_prod] ;matrix2[j*k1n2+k]
		VMULPS YMM1,YMM2; prodotto matrix1,matrix2
		VADDPS YMM0,YMM1; sum+=

		;Ciclo
		ADD R12,pack_prod ;k+4
		CMP R12,RCX ;K cmp K1n2
		JL .for_k
		VPERM2F128 YMM3,YMM0,YMM0,0_0_00_0_0_0_01b
		VADDPS YMM0,YMM3
		VHADDPS YMM0,YMM0
		VHADDPS YMM0,YMM0
		MOV RAX,R10 ; metto i
		IMUL RAX,R8 ; i*k2
		ADD RAX,R11 ; i*k2+j
		;MOV RAX,RBP
		VMOVSS [R9+RAX*dim_prod],XMM0
		INC R11 ;j++
		CMP R11,R8 ;j CMP k2
		JL .for_j
		INC R10 ;i++
		CMP R10,RDX ; i CMP n
		JL .for_i
		;restituzione del risultato
		;VMOVSS RAX,

		; ------------------------------------------------------------
		; USCITA
		; ------------------------------------------------------------

		popaq
		MOV RSP, RBP
		POP RBP
		RET
