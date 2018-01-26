cmake_minimum_required(VERSION 3.9)
project(perfectHash C)

set(CMAKE_C_STANDARD 99)

add_executable(perfectHash main.c user.c user.h)