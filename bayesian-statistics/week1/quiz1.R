#### Q1
q1.urns = matrix(c(1/2, 1/6, 1/3, 1/3, 1/2, 1/6, 1/6, 1/3, 1/2), ncol = 3, byrow = 3)
colnames(q1.urns) = c("A","B","C")
rownames(q1.urns) = c("Blue","Green","Red")
q1.urns = as.table(q1.urns)

# P(C|red) = P(red|C) * P(C) / P(red)
q1.p_c_given_red = q1.urns[3,3] * (1/3) / (1/3)

# P(C|blue) = P(blue|C) * P(C) / P(blue)
q1.p_c_given_blue = q1.urns[1,3] * (1/3) / (1/3)

# P(C|red,blue) = 2 * P(C|red) * P(C|blue)
q1.p_c_given_red_blue = 2 * q1.p_c_given_red * q1.p_c_given_blue
q1.p_c_given_red_blue  

#### Q3
q3.p = c(1e-3, 1e-6)
q3.n = 10
q3.k = 0
q3.prior = c(0.5, 0.5)
q3.lik = dbinom(q3.k, size = q3.n, prob = q3.p)
q3.posterior = q3.prior*q3.lik/sum(q3.prior*q3.lik)
q3.posterior[1]

#### Q7

q7.alpha = 0.1
q7.N = 300
q7.x = 5
q7.p = 0.01

q7.ans = binom.test(q7.x, q7.N, q7.p, alternative="greater", conf.level = 0.9) #p-value = 0.1839
q7.ans$p.value

#### Q8
q8.n = 413271201 #Number of trials
q8.k = 3 #Number of successes
q8.p = 1/seq(from=100e6, to=900e6, by=100e6) #Models under consideration

q8.prior = rep(1/9, 9) #Prior of models under consideration
q8.lik = dbinom(q8.k, size = q8.n, prob = q8.p) #Likelihood of models

#Compute posteriors, P(model|data) = P(data|model) * P(model) / P(data)
q8.posterior = q8.prior * q8.lik/sum(q8.prior * q8.lik)
sum(q8.posterior[1:5])

#### Q9
q9.p = c(1/6, 0.175)
q9.k = 999
q9.n = 6000
q9.prior = c(0.8, 0.2)

#Likelihood = probability at least X sixes occurred in N trials with proportion P
q9.lik = 1 - pbinom(q9.k-1, q9.n, q9.p)
q9.posterior = q9.prior * q9.lik / sum(q9.prior * q9.lik)
q9.posterior[1] #P(coin is fair aka p=1/6)

