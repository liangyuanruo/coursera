Wxh = -0.1; Whh = 0.5; Why = 0.25;
hbias = 0.4; ybias = 0.0;
x0 = 18; x1 = 9; x2 = -8;
t0 = 0.1; t1 = -0.1; t2 = -0.2;

%First time step
z0 = Wxh * x0 + hbias;
h0 = logistic(z0);
y0 = Why * h0 + ybias;
E0 = 0.5 * (t0 - y0)^2;

%Second time step
z1 = Wxh * x1 + Whh * h0 + hbias;
h1 = logistic(z1);
y1 = Why * h1 + ybias;
E1 = 0.5 * (t1 - y1)^2;

%Third time step
z2 = Wxh * x2 + Whh * h1 + hbias;
h2 = logistic(z2);
y2 = Why * h2 + ybias;
E2 = 0.5 * (t2 - y2)^2;

%Total error
E = E0 + E1 + E2;

%Derivatives
%{

dE/dz1 = 

dE0/dz1 + dE1/dz1 + dE2/dz1 =

dE0/dz1 = 0

dE1/dz1 = (dE1/dy1 * dy1/dh1 * dh1/dz1)

dE2/dz1 = (dE2/dy2 * dy2/dh2 * dh2/dz2 * dz2/dh1 * dh1/dz1) = 

-1 * Why * z1*(1-z1) +
-1 * Why * z2*(1-z2) * Whh * z1*1-z1)

%}
dEdz1 = ...
-(t1-y1) * Why * z1*(1-z1) + ...
-(t2-y2) * Why * z2*(1-z2) * Whh * z1*(1-z1);

dEdz1