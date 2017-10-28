clear; clc;

%% Question 10

visible_state = [0, 1]';
rbm_w = [1, -1.5];
b = -0.5;

%From wikipedia
%P(h_j=1|v) = logistic( b_j + sum_i(w_ij*v_i) ) 
p = logistic(b + rbm_w * visible_state)

%% Question 11

x = 1;
t = 5;
w1 = 1.0986123;
w2 = 4;

%What is squared error?
%Squared error = 0.5*(y-t)^2
h_in = x * w1;
h_out = logistic(h_in);
y = h_out * w2;
cost = 0.5 * (y-t)^2 % cost = error E

%% Question 12
%What is dEdw1?

%dEdw1 
% = dEdy * dydhout * dhoutdhin * dhindw1
% = (y-t) * w2 * h_in*(1-h_in) * x

dEdw1 = (y-t) * w2 * h_in*(1-h_in) * x

