import math

def gss(f,a,b,tol=1e-4):
    r1 = (math.sqrt(5) - 1) / 2
    r2 = math.pow(r1,2)
    (a,b)=(min(a,b),max(a,b))
    h = b - a
    c = a + r2 * h
    d = a + r1 * h
    yc = f(c)
    yd = f(d)
    xmin = d
    ymin = yd
    while True:
        if yc < yd:
            b = d
            d = c
            yd = yc
            h = b - a
            c = a + r2 * h
            if h < tol: break
            yc = f(c)
            xmin = c
            ymin = yc
        else:
            a = c
            c = d
            yc = yd
            h = b - a
            d = a + r1 * h
            if h < tol: break
            yd = f(d)
            xmin = d
            ymin = yd

    return (xmin,ymin)

def f(x):
    return x*math.exp(x)-x

print gss(f,-10,10,1e-4)
