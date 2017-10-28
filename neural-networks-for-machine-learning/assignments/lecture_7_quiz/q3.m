Wxh = 0.5; Whh = -1.0; Why = -0.7;
hbias = -1.0; ybias = 0.0;
x0 = 9; x1 = 4; x2 = -2;

%First time step
z0 = Wxh * x0 + hbias;
h0 = logistic(z0);
y0 = Why * h0 + ybias;

%Second time step
z1 = Wxh * x1 + Whh * h0 + hbias;
h1 = logistic(z1);
y1 = Why * h1 + ybias;

y1