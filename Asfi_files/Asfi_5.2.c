#include <stdio.h>
#include <math.h>
#include <unistd.h>

typedef struct { float x, y, z; } Vec3;

void rotateX(Vec3* p, float a) {
    float y = p->y*cos(a) - p->z*sin(a);
    float z = p->y*sin(a) + p->z*cos(a);
    p->y = y; 
    p->z = z;
}

void rotateY(Vec3* p, float a) {
    float x = p->x*cos(a) + p->z*sin(a);
    float z = -p->x*sin(a) + p->z*cos(a);
    p->x = x; 
    p->z = z;
}

// ---- New: Z-axis rotation ----
void rotateZ(Vec3* p, float a) {
    float x = p->x*cos(a) - p->y*sin(a);
    float y = p->x*sin(a) + p->y*cos(a);
    p->x = x;
    p->y = y;
}

int main() {
    Vec3 cube[8] = {
        {-1,-1,-1}, { 1,-1,-1}, { 1, 1,-1}, {-1, 1,-1},
        {-1,-1, 1}, { 1,-1, 1}, { 1, 1, 1}, {-1, 1, 1}
    };

    float angle = 0;
    while (1) {
        printf("\033[2J"); // clear screen
        printf("3D Rotating Cube (Modified Version)\n\n");

        for (int i = 0; i < 8; i++) {
            Vec3 p = cube[i];

            rotateX(&p, angle);
            rotateY(&p, angle);
            rotateZ(&p, angle * 0.3);   // small Z rotation added

            float dist = 4;
            float z = p.z + dist;
            int px = (int)(40 + p.x * 20 / z);
            int py = (int)(12 + p.y * 20 / z);

            printf("Point %d: (%d, %d)\n", i, px, py);
        }

        angle += 0.05;
        usleep(50000);
    }
    return 0;
}
