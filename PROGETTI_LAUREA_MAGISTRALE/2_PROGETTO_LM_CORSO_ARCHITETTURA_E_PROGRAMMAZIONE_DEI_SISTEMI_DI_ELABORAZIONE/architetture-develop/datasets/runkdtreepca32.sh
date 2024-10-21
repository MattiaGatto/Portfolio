#!/bin/bash
nasm -f elf32 kdtreepca32.nasm 
gcc -msse -m32 -O0 sseutils.o kdtreepca32.o kdtreepca32c.c -lm -o kdtreepca32c
pars="";
for arg; do pars=$pars" "$arg; done;
./kdtreepca32c $pars
