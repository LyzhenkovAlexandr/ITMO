package queue;

/*
    Model: a[1]..a[n]
    Invariant: n >= 0 && for i = 1..n: a[i] != null

    Let immutable(n): for i = 1..n: a'[i] = a[i]
 */

public interface Queue {

//    Pred: element != null
//    Post: n' = n + 1 && a[n'] = element && immutable(n)
    /*public*/ void enqueue(final Object element);

//    Pred: true
//    Post: R == a[1] && immutable(n) && n' == n
    /*public*/ Object element();

//    Pred: n >= 1
//    Post: n' = n - 1 && R == a[1] && immutable(n')
    /*public*/ Object dequeue();

//    Pred: true
//    Post: R == n && n' == n && immutable(n)
    /*public*/ int size();

//    Pred: true
//    Post: R == (n == 0) && n' == n && immutable(n)
    /*public*/ boolean isEmpty();

//    Pred: true
//    Post: n' == 0 && n' == n && immutable(n')
    /*public*/ void clear();

//    Pred: true
//    Post: R == a[1]..a[n] && n' == n && immutable(n)
    /*public*/ Object[] toArray();

//    Pred: element != null
//    Post: R == min(i): for all i in [1..n): a[i] == element && n' == n && immutable(n)
    /*public*/ int indexOf(final Object element);

//    Pred: element != null
//    Post: R == max(i): for all i in [1..n): a[i] == element && n' == n && immutable(n)
    /*public*/ int lastIndexOf(final Object element);
}