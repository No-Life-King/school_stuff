cmake_minimum_required(VERSION 3.9)
project(play C)

set(CMAKE_C_STANDARD 99)

add_executable(play main.c)

include_directories(D:/Downloads/libsodium-1.0.16-msvc/include)

target_link_libraries(play D:/Downloads/libsodium-1.0.16-msvc/Win32/Release/v141/dynamic/libsodium.lib)