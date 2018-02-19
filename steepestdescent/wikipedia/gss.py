#!/usr/bin/env python
import math

invphi = (math.sqrt(5) - 1) / 2 # 1/phi
invphi2 = (3 - math.sqrt(5)) / 2 # 1/phi^2

def gss(f,a,b,tol=1e-5):
    '''
    Golden section search.

    Given a function f with a single local minumum in
    the interval [a,b], gss returns a subset interval
    [c,d] that contains the minimum with d-c <= tol.

    example:
    >>> f = lambda x: (x-2)**2
    >>> a = 1
    >>> b = 5
    >>> tol = 1e-5
    >>> (c,d) = gss(f, a, b, tol)
    >>> print (c,d)
    (1.9999959837979107, 2.0000050911830893)
    '''

    (a,b)=(min(a,b),max(a,b))
    h = b - a
    if h <= tol: return (a,b)

    # required steps to achieve tolerance
    n = int(math.ceil(math.log(tol/h)/math.log(invphi)))

    c = a + invphi2 * h
    d = a + invphi * h
    fc = f(c)
    fd = f(d)

    for k in xrange(n-1):
        if fc < fd:
            b = d
            d = c
            fd = yc
            h = invphi*h
            c = a + invphi2 * h
            fc = f(c)
        else:
            a = c
            c = d
            fc = yd
            h = invphi*h
            d = a + invphi * h
            fd = f(d)

    if fc < fd:
        return (a,d)
    else:
        return (c,b)

def main():
    f = lambda x: (x-2)**2
    a = 1
    b = 5
    (c,d) = gss(f, a, b)
    print (c,d)

if __name__ == "__main__":
    main()
