# CMAKE generated file: DO NOT EDIT!
# Generated by "MinGW Makefiles" Generator, CMake Version 3.9

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

SHELL = cmd.exe

# The CMake executable.
CMAKE_COMMAND = "C:\Program Files\JetBrains\CLion 2017.3\bin\cmake\bin\cmake.exe"

# The command to remove a file.
RM = "C:\Program Files\JetBrains\CLion 2017.3\bin\cmake\bin\cmake.exe" -E remove -f

# Escaping for special characters.
EQUALS = =

# The top-level source directory on which CMake was run.
CMAKE_SOURCE_DIR = "D:\Devel\school_stuff\C & C++\play"

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = "D:\Devel\school_stuff\C & C++\play\cmake-build-debug"

# Include any dependencies generated for this target.
include CMakeFiles/play.dir/depend.make

# Include the progress variables for this target.
include CMakeFiles/play.dir/progress.make

# Include the compile flags for this target's objects.
include CMakeFiles/play.dir/flags.make

CMakeFiles/play.dir/main.c.obj: CMakeFiles/play.dir/flags.make
CMakeFiles/play.dir/main.c.obj: CMakeFiles/play.dir/includes_C.rsp
CMakeFiles/play.dir/main.c.obj: ../main.c
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir="D:\Devel\school_stuff\C & C++\play\cmake-build-debug\CMakeFiles" --progress-num=$(CMAKE_PROGRESS_1) "Building C object CMakeFiles/play.dir/main.c.obj"
	C:\MinGW\bin\gcc.exe $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -o CMakeFiles\play.dir\main.c.obj   -c "D:\Devel\school_stuff\C & C++\play\main.c"

CMakeFiles/play.dir/main.c.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing C source to CMakeFiles/play.dir/main.c.i"
	C:\MinGW\bin\gcc.exe $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -E "D:\Devel\school_stuff\C & C++\play\main.c" > CMakeFiles\play.dir\main.c.i

CMakeFiles/play.dir/main.c.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling C source to assembly CMakeFiles/play.dir/main.c.s"
	C:\MinGW\bin\gcc.exe $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -S "D:\Devel\school_stuff\C & C++\play\main.c" -o CMakeFiles\play.dir\main.c.s

CMakeFiles/play.dir/main.c.obj.requires:

.PHONY : CMakeFiles/play.dir/main.c.obj.requires

CMakeFiles/play.dir/main.c.obj.provides: CMakeFiles/play.dir/main.c.obj.requires
	$(MAKE) -f CMakeFiles\play.dir\build.make CMakeFiles/play.dir/main.c.obj.provides.build
.PHONY : CMakeFiles/play.dir/main.c.obj.provides

CMakeFiles/play.dir/main.c.obj.provides.build: CMakeFiles/play.dir/main.c.obj


# Object files for target play
play_OBJECTS = \
"CMakeFiles/play.dir/main.c.obj"

# External object files for target play
play_EXTERNAL_OBJECTS =

play.exe: CMakeFiles/play.dir/main.c.obj
play.exe: CMakeFiles/play.dir/build.make
play.exe: D:/Downloads/libsodium-1.0.16-msvc/Win32/Release/v141/dynamic/libsodium.lib
play.exe: CMakeFiles/play.dir/linklibs.rsp
play.exe: CMakeFiles/play.dir/objects1.rsp
play.exe: CMakeFiles/play.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir="D:\Devel\school_stuff\C & C++\play\cmake-build-debug\CMakeFiles" --progress-num=$(CMAKE_PROGRESS_2) "Linking C executable play.exe"
	$(CMAKE_COMMAND) -E cmake_link_script CMakeFiles\play.dir\link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
CMakeFiles/play.dir/build: play.exe

.PHONY : CMakeFiles/play.dir/build

CMakeFiles/play.dir/requires: CMakeFiles/play.dir/main.c.obj.requires

.PHONY : CMakeFiles/play.dir/requires

CMakeFiles/play.dir/clean:
	$(CMAKE_COMMAND) -P CMakeFiles\play.dir\cmake_clean.cmake
.PHONY : CMakeFiles/play.dir/clean

CMakeFiles/play.dir/depend:
	$(CMAKE_COMMAND) -E cmake_depends "MinGW Makefiles" "D:\Devel\school_stuff\C & C++\play" "D:\Devel\school_stuff\C & C++\play" "D:\Devel\school_stuff\C & C++\play\cmake-build-debug" "D:\Devel\school_stuff\C & C++\play\cmake-build-debug" "D:\Devel\school_stuff\C & C++\play\cmake-build-debug\CMakeFiles\play.dir\DependInfo.cmake" --color=$(COLOR)
.PHONY : CMakeFiles/play.dir/depend

