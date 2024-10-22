# Progetto #2: LinkedList

## Corso di Programmazione Orientata agli Oggetti

Questo progetto consiste nella realizzazione di un'interfaccia `List<T>` e della sua implementazione tramite una lista concatenata a doppio puntatore, `LinkedList<T>`. Il progetto si ispira al framework di collezioni di Java e utilizza caratteristiche avanzate di Java 8, come i metodi default e statici nelle interfacce.

---

## Indice

- [Interfaccia `List<T>`](#interfaccia-listt)
- [Parte 1: Implementazione](#parte-1-implementazione)
- [Iteratori](#iteratori)
- [Ordinamento](#ordinamento)
- [Testing](#testing)
- [Parte Facoltativa](#parte-facoltativa)
- [Conclusioni](#conclusioni)

---

## Interfaccia `List<T>`

L'interfaccia `List<T>` è definita nel package `poo.util` e include i seguenti metodi:

```java
package poo.util;

public interface List<T> extends Iterable<T> {
    int size();
    void clear();
    boolean contains(T e);
    boolean isEmpty();
    void add(T e);
    void remove(T e);
    ListIterator<T> listIterator();
    ListIterator<T> listIterator(int pos);
    void addFirst(T e);
    void addLast(T e);
    T removeFirst();
    T removeLast();
    T getFirst();
    T getLast();
    void sort(Comparator<T> c);
}
```

---

## Parte 1: Implementazione

- **Metodi Default e Statici**: Sono stati concretizzati il maggior numero possibile di metodi direttamente nell'interfaccia `List<T>`.
- **Classe Astratta `AbstractList<T>`**: Questa classe implementa l'interfaccia `List<T>` e fornisce implementazioni dei metodi `toString()`, `equals()`, e `hashCode()`.
- **Classe Concreta `LinkedList<T>`**: Estende `AbstractList<T>` e gestisce gli elementi utilizzando una lista concatenata a doppio puntatore.

---

## Iteratori

Il progetto include la realizzazione di strutture di iterazione, tra cui:

- **Iteratore Semplice**: Implementato in una classe interna che gestisce il puntamento agli elementi della lista.
- **ListIterator**: Derivato dall'iteratore semplice, implementato in una sola inner class. Supporta sia metodi di iteratore standard (`hasNext()`, `next()`, `remove()`) che metodi specifici di `ListIterator`.

---

## Ordinamento

Il metodo `sort()` utilizza l'algoritmo bubble sort e accetta un oggetto `Comparator<T>` per ordinare gli elementi della lista.

---

## Testing

È stata predisposta una classe `Main` con un metodo `main()` per testare le varie funzionalità del progetto, confrontando i risultati con l'implementazione di `java.util.LinkedList<T>`.

---

## Parte Facoltativa

Si propone di sviluppare una GUI che esponga una struttura a menu per eseguire tutte le operazioni disponibili su una lista. Le funzionalità della GUI includono:

- Scelta del tipo di elementi (Integer o String).
- Visualizzazione dello stato corrente della lista.
- Indirizzamento dell'iteratore e visualizzazione della posizione corrente.
- Gestione dinamica dei menu in base alle operazioni ammissibili nello stato attuale della lista.

---

## Conclusioni

Questo progetto offre un'opportunità per approfondire la comprensione delle strutture dati e della programmazione orientata agli oggetti in Java, nonché per implementare interfacce e classi in modo strutturato e modulare.

---