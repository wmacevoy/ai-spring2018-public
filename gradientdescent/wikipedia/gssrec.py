#!/usr/bin/env python
import math

invphi = (math.sqrt(5) - 1) / 2 # 1/phi
invphi2 = (3 - math.sqrt(5)) / 2 # 1/phi^2

def gss(f,a,b,tol=1e-5):
    return gssrec(f,a=min(a,b),b=max(a,b),
                  c=None,fc=None,d=None,fd=None,h=abs(b-a),tol=tol)

def gssrec(f,a,b,c,fc,d,fd,h,tol):
    if h <= tol: return (a,b)
    if c == None:
        c = a + invphi2*h
        fc = f(c)
    if d == None:
        d = a + invphi*h
        fd = f(d)        
    if fc < fd:
        return gssrec(f,a,d,c=None,fc=None,d=c,fd=fc,h=h*invphi,tol=tol)
    else:
        return gssrec(f,c,b,c=d,fc=fd,d=None,fd=None,h=h*invphi,tol=tol)

def main():
    f = lambda x: (x-2)**2
    a = 1
    b = 5
    (c,d) = gss(f, a, b)
    print (c,d)

if __name__ == "__main__":
    main()
