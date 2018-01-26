cmake_minimum_required(VERSION 3.9)
project(linkedList C)

set(CMAKE_C_STANDARD 99)

add_executable(linkedList main.c linkedList.c linkedList.h)