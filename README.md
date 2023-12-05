# Objected-Oriented-Programming-cs10DartmouthCollege
Huffman Encoding for File Compression and Decompression

Introduction

This repository contains an implementation of Huffman encoding, a classic algorithm for lossless data compression, in Java. The project showcases the use of various data structures such as trees, maps, and priority queues, alongside file I/O operations, to efficiently compress and decompress files.

Background

Huffman coding is a method of lossless data compression that uses variable-length encoding for characters. Frequently occurring characters are assigned shorter codes, while less common characters receive longer codes. This project not only implements the Huffman encoding algorithm but also provides tools for reading and writing bits and handling file operations in Java.

Features

- Efficient Compression: Implements Huffman's algorithm to compress text files efficiently.
- Dynamic Encoding: Generates variable-length, prefix-free codes based on character frequencies.
- File I/O Operations: Includes utilities for reading and writing characters and bits to files.
- Robust Error Handling: Incorporates error handling for file operations.
- Extensive Documentation: The code is well-commented for ease of understanding.

Implementation Details

- Frequency Table Generation: Counts character occurrences in the input file.
- Priority Queue for Tree Construction: Uses a priority queue to build the Huffman tree.
- Code Generation and Retrieval: Maps characters to their respective Huffman codes.
- Compression and Decompression: Reads and writes bits to handle file compression and decompression.
