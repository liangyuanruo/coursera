%Working solution for Week 13 quiz

%Initial configuration of Sigmoid Belief Net
% Two weights (w1 & w2) from two hidden nodes (h1 & h2) to 
% one visible node (v). There are no biases (or they are zero)

w1 = -6.90675478; % Weight from h1 -> v
w2 = 0.40546511; % Weight from h2 -> v
h1 = 0; % Hidden unit 1
h2 = 1; % Hidden unit 2
v=1; % Visible unit

w = [w1, w2]';
h = [h1, h2]';

% Q2
% What is P(v=1 | h1=0, h2=1)?
q2 = logistic( h' * w );

% Q3
% What is probability of full configuration of C011
% i.e. P(h1=0, h2=1, v=1) ?

% P(h1=0, h2=1, v=1) = P(v=1|h1=0, h2=1)P(h1=0, h2=1)
% Answer: assume P(h1=0, h2=1) = P(h1=0)P(h2=1)
q3 = logistic( h' * w ) * (1 - logistic(0)) * logistic(0) ;

% Q4
% What is dlogP(C011)/dw1 ?
% Formula: j is parent, i is child
% dlogP(C)/dw_ij = s_j * (s_i - p_i)

q4 = h1 * (v - logistic( h' * w ));

% Q5
% What is dlogP(C011)/dw2 ?

q5 = h2 * (v - logistic( h' * w ));
%{
As was explained in the lectures, the log likelihood gradient for a full 
configuration is just one part of the learning. The more difficult part is 
to get a handle on the posterior probability distribution over full 
configurations, given the state of the visible units. Explaining away is 
an important issue there. 

Let's explore it with new weights: for the remainder of this quiz, w1=10, and w2=?4.
%}
% Q6

w1_new = 10; w2_new = -4; w_new = [w1, w2]';

% What is P(h2=1 | v=1, h1=0)?
% Give your answer with at least four digits after the decimal point. 
% Hint: it's a fairly small number (and not a round number like for the earlier questions); 
% try to intuitively understand why it's small. 
% Second hint: you might find Bayes' rule useful, but even with that rule, this still requires some thought.

% Answer derivation 
%   P(h2=1|v=1,h1=0)
% = P(v=1|h1=0,h2=1)P(h2=1) / 
% [ P(v=1|h1=0,h2=1)P(h2=1) + P(v=1|h1=0,h2=0)P(h2=0) ]

q6 = ( logistic(w2_new) * 0.5 )/( 0.5 * 0.5 + 0.5 * logistic(w2_new) );


% Q7
% What is P(h2=1 | v=1, h=1)?

q7 = ( logistic(w1_new+w2_new) * 0.5)/( 0.5 * logistic(w1_new+w2_new) + 0.5 * logistic(w1_new) );





