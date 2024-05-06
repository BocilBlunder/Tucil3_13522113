<h1 align="center">Tugas Kecil 3 IF2211 Strategi Algoritma</h1>
<h2 align="center">Semester II Tahun 2023/2024</h2>
<h3 align="center">Penyelesaian Permainan Word Ladder Menggunakan </br> Algoritma UCS, Greedy Best First Search, dan A*</h3>

## Creator
| NIM      | Nama                   | Kelas |
| -------- | ---------------------- | ----- |
| 13522113 | Wiliam Glory Henderson | K-01  |

## Table of Contents
* [Overview](#Overview)
* [Technologies Used](#technologies-used)
* [Features](#features)
* [Setup](#setup)
* [How to Use the Program](#how-to-use-program)

## Overview
This Word Ladder Solver project is designed to transform a starting word into a target word by changing one letter at a time, with each intermediate step forming a valid word. The project implements three algorithms to solve the Word Ladder problem: Uniform Cost Search (UCS), Greedy Best First Search (GBFS), and A* (A-Star). The aim is to assess the effectiveness of these algorithms in finding the shortest transformation path between two words.

## Technologies Used
- **Java**: All logic, GUI, and algorithms are implemented using Java.
- **Prerequisites**: 
  - **Windows**: Java 19 or newer must be installed on your machine to compile and run the code.
  - **Linux**: The latest version of Java must be installed on your machine to compile and run the code.

## Features
- **Algorithms**: Solves the Word Ladder game using three different algorithms:
  - **Uniform Cost Search (UCS)**:
    - Based on costs from start node until end node (cost for every changed words is same).
    - Guarantees to find the optimal solution but not efficient.
    
  - **Greedy Best First Search (GBFS)**:
    - Based on heuristic estimates from current node to the goal.
    - Efficient in memory usage and run time but may not always find the optimal solution.
    
  - **A\* (A Star)**:
    - Combines UCS and GBFS by considering both actual cost and heuristic estimates.
    - Efficient and guarantees to find the optimal solution.


## Setup
To get started with the Word Ladder solver, you can clone the repository and run the application.

1. Clone the repository
```bash
https://github.com/BocilBlunder/Tucil3_13522113.git
``` 
2. To run the program (GUI)
- For Windows :
```
./run1
```
- For Linux (Don't forget to change End of Line Sequence file run1.sh to LF) :
```
chmod +x run1.sh
./run1.sh
```
3. To run the program (CLI)
- For Windows :
```
./run2
```
- For Linux (Don't forget to change End of Line Sequence file run2.sh to LF) :
```
chmod +x run2.sh
./run2.sh
```

## How to Use the Program
1. **Start and End Word**: Input the start word and the end word.
2. **Choose the Algorithm**: Select one of the three algorithms (UCS, GBFS, or A*) for solving the Word Ladder.
3. **Execution**: The program will display the path from the start word to the end word, along with the execution time, the memory that been used, and the number of nodes visited during the search.

## Limitations
- **Language Constraint**: This program currently supports only English valid words (from dictionary).
- **Word Length Requirement**: Both the start and end words must have the same length.
- **Word Format Requirement**: Both word input will be automatically change into lower case.
