#!/bin/bash
nasm -f elf64 kdtreepca64.nasm
gcc -no-pie -msse -O0 sseutils64.o kdtreepca64.o kdtreepca64c.c -lm -o kdtreepca64c
pars="";
for arg; do pars=$pars" "$arg; done;
./kdtreepca64c $pars
