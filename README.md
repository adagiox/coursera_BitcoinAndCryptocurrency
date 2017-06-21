# coursera_BitcoinAndCryptocurrency
Coursera course which teaches the fundamentals of cryptocurrencies.
https://www.coursera.org/learn/cryptocurrency/

There are three assignments in Java which implement a transaction handler class, a distributed consensus algorithm, and a node in the network. Each assignemnt contains some starter code.

Assignment 1:
The goal of Assignment 1 is to implement a transaction handler class which will take an array of incoming transactions and return an array of valid transactions of maximal size. To validate incoming transactions in our bitcoin-like model, we must check that for each input there exists a UTXO (unspent transaction output) in the current UTXO pool, validate the signatures on each input of the transaction, make sure each UTXO is only claimed once, and validate the input and output values (bitcoins) of each of the transactions inputs and outputs. 

Assignment 2:
The goal of Assignment 2 is to implement a CompliantNode class for a distributed consensus simulation in a bitcoin-like directed random graph environment. The simulation constructs a network consisting of either malicious nodes or compliant nodes based on a specified probability. The compliant nodes should (attempt to) come into consensus regardless of the network topology, number of malicious nodes, and distribution of transactions across the initial state of the network before propagation. 
