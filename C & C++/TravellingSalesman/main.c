#include <stdio.h>
#include <windows.h>
#include <math.h>

double getTime();

int main() {

    double coords[28] = {0.134643599, 0.644606626,		//a
                         0.085715042, 0.392759724,		//b
                         0.773477251, 0.581039988,		//c
                         0.686998724, 0.746717332,		//d
                         0.516545774, 0.398441588,		//e
                         0.939667609, 0.546435841,		//f
                         0.197769913, 0.008271149,		//g
                         0.67671762 , 0.374678047,		//h
                         0.099895976, 0.364538864,		//i
                         0.216475744, 0.75104157 ,		//j
                         0.019240325, 0.168853455,		//k
                         0.895383164, 0.512507985,		//l
                         0.225475686, 0.67818289 ,		//m
                         0.841331763, 0.156130221};		//n

    double distances[14][14];

    for (int x=0; x<14; x++) {
        for (int y=0; y<14; y++) {
            distances[x][y] = sqrt((coords[x * 2] - coords[y * 2])*(coords[x * 2] - coords[y * 2]) + (coords[x * 2 + 1] - coords[y * 2 + 1])*(coords[x * 2 + 1] - coords[y * 2 + 1]));
        }
    }

    int countArray[14];
    int cities[14] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
    char city_labels[] = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n'};

    for (int i=0; i<14; i++) {
        countArray[i] = 0;
    }

    int i=0;
    int temp;
    double minTrip = 0;
    double currentTrip = 0;
    int path[14];

    double start = getTime();

    for (int z=0; z<13; z++) {
        minTrip += distances[z][z+1];
    }

    while (i<13) {

        if (countArray[i] < i) {
            if (i%2 == 0) {
                temp = cities[0];
                cities[0] = cities[i];
                cities[i] = temp;
            } else {
                temp = cities[countArray[i]];
                cities[countArray[i]] = cities[i];
                cities[i] = temp;
            }

            for (int z=0; z<13; z++) {
                currentTrip += distances[cities[z]][cities[z+1]];
            }

            currentTrip += distances[cities[13]][cities[0]];

            if (currentTrip < minTrip) {
                minTrip = currentTrip;
                for (int a=0; a<14; a++) {
                    path[a] = cities[a];
                }
            }

            currentTrip = 0;

            countArray[i]++;
            i = 0;
        } else {
            countArray[i] = 0;
            i++;
        }
    }

    double finish = getTime();
    printf("6,227,020,800 paths compared in %f seconds\n", finish-start);
    printf("Minimum Trip Length: %f\nPath: ", minTrip);
    for (int b=0; b<14; b++) {
        printf("%c", city_labels[path[b]]);
    }
}

double getTime() {
    LARGE_INTEGER t, f;
    QueryPerformanceCounter(&t);
    QueryPerformanceFrequency(&f);
    return (double)t.QuadPart/(double)f.QuadPart;
}