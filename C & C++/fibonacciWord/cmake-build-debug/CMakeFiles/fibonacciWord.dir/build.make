# CMAKE generated file: DO NOT EDIT!
# Generated by "Unix Makefiles" Generator, CMake Version 3.9

# Delete rule output on recipe failure.
.DELETE_ON_ERROR:


#=============================================================================
# Special targets provided by cmake.

# Disable implicit rules so canonical targets will work.
.SUFFIXES:


# Remove some rules from gmake that .SUFFIXES does not remove.
SUFFIXES =

.SUFFIXES: .hpux_make_needs_suffix_list


# Suppress display of executed commands.
$(VERBOSE).SILENT:


# A target that is always out of date.
cmake_force:

.PHONY : cmake_force

#=============================================================================
# Set environment variables for the build.

# The shell in which to execute make rules.
SHELL = /bin/sh

# The CMake executable.
CMAKE_COMMAND = /home/phil/programs/clion-2017.3.1/bin/cmake/bin/cmake

# The command to remove a file.
RM = /home/phil/programs/clion-2017.3.1/bin/cmake/bin/cmake -E remove -f

# Escaping for special characters.
EQUALS = =

# The top-level source directory on which CMake was run.
CMAKE_SOURCE_DIR = "/home/phil/devel/school_stuff/C & C++/fibonacciWord"

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = "/home/phil/devel/school_stuff/C & C++/fibonacciWord/cmake-build-debug"

# Include any dependencies generated for this target.
include CMakeFiles/fibonacciWord.dir/depend.make

# Include the progress variables for this target.
include CMakeFiles/fibonacciWord.dir/progress.make

# Include the compile flags for this target's objects.
include CMakeFiles/fibonacciWord.dir/flags.make

CMakeFiles/fibonacciWord.dir/main.c.o: CMakeFiles/fibonacciWord.dir/flags.make
CMakeFiles/fibonacciWord.dir/main.c.o: ../main.c
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir="/home/phil/devel/school_stuff/C & C++/fibonacciWord/cmake-build-debug/CMakeFiles" --progress-num=$(CMAKE_PROGRESS_1) "Building C object CMakeFiles/fibonacciWord.dir/main.c.o"
	/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -o CMakeFiles/fibonacciWord.dir/main.c.o   -c "/home/phil/devel/school_stuff/C & C++/fibonacciWord/main.c"

CMakeFiles/fibonacciWord.dir/main.c.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing C source to CMakeFiles/fibonacciWord.dir/main.c.i"
	/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -E "/home/phil/devel/school_stuff/C & C++/fibonacciWord/main.c" > CMakeFiles/fibonacciWord.dir/main.c.i

CMakeFiles/fibonacciWord.dir/main.c.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling C source to assembly CMakeFiles/fibonacciWord.dir/main.c.s"
	/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -S "/home/phil/devel/school_stuff/C & C++/fibonacciWord/main.c" -o CMakeFiles/fibonacciWord.dir/main.c.s

CMakeFiles/fibonacciWord.dir/main.c.o.requires:

.PHONY : CMakeFiles/fibonacciWord.dir/main.c.o.requires

CMakeFiles/fibonacciWord.dir/main.c.o.provides: CMakeFiles/fibonacciWord.dir/main.c.o.requires
	$(MAKE) -f CMakeFiles/fibonacciWord.dir/build.make CMakeFiles/fibonacciWord.dir/main.c.o.provides.build
.PHONY : CMakeFiles/fibonacciWord.dir/main.c.o.provides

CMakeFiles/fibonacciWord.dir/main.c.o.provides.build: CMakeFiles/fibonacciWord.dir/main.c.o


# Object files for target fibonacciWord
fibonacciWord_OBJECTS = \
"CMakeFiles/fibonacciWord.dir/main.c.o"

# External object files for target fibonacciWord
fibonacciWord_EXTERNAL_OBJECTS =

fibonacciWord: CMakeFiles/fibonacciWord.dir/main.c.o
fibonacciWord: CMakeFiles/fibonacciWord.dir/build.make
fibonacciWord: CMakeFiles/fibonacciWord.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir="/home/phil/devel/school_stuff/C & C++/fibonacciWord/cmake-build-debug/CMakeFiles" --progress-num=$(CMAKE_PROGRESS_2) "Linking C executable fibonacciWord"
	$(CMAKE_COMMAND) -E cmake_link_script CMakeFiles/fibonacciWord.dir/link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
CMakeFiles/fibonacciWord.dir/build: fibonacciWord

.PHONY : CMakeFiles/fibonacciWord.dir/build

CMakeFiles/fibonacciWord.dir/requires: CMakeFiles/fibonacciWord.dir/main.c.o.requires

.PHONY : CMakeFiles/fibonacciWord.dir/requires

CMakeFiles/fibonacciWord.dir/clean:
	$(CMAKE_COMMAND) -P CMakeFiles/fibonacciWord.dir/cmake_clean.cmake
.PHONY : CMakeFiles/fibonacciWord.dir/clean

CMakeFiles/fibonacciWord.dir/depend:
	cd "/home/phil/devel/school_stuff/C & C++/fibonacciWord/cmake-build-debug" && $(CMAKE_COMMAND) -E cmake_depends "Unix Makefiles" "/home/phil/devel/school_stuff/C & C++/fibonacciWord" "/home/phil/devel/school_stuff/C & C++/fibonacciWord" "/home/phil/devel/school_stuff/C & C++/fibonacciWord/cmake-build-debug" "/home/phil/devel/school_stuff/C & C++/fibonacciWord/cmake-build-debug" "/home/phil/devel/school_stuff/C & C++/fibonacciWord/cmake-build-debug/CMakeFiles/fibonacciWord.dir/DependInfo.cmake" --color=$(COLOR)
.PHONY : CMakeFiles/fibonacciWord.dir/depend

