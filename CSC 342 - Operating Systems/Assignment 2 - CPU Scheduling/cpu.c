/****************************************************************************/
/*                                                                          */
/*				Module CPU				                                    */
/*			     Internal Routines				                            */
/*                                                                          */
/****************************************************************************/


#include "cpu.h"
#include <stdlib.h>
#include <math.h>
#include "queue.h"

int compareTo(PCB *pcb1, PCB *pcb2);
char *toString(PCB *pcb);

Queue ready_queue;

void cpu_init()
{
    initQueue(&ready_queue);
}



void dispatch()
{
    if (PTBR != NULL && PTBR->pcb->status == running) {
        if (Int_Vector.cause == timeint) {
            PTBR->pcb->status = ready;
            enQueue(&ready_queue, PTBR->pcb);
        } else {
            PTBR->pcb->last_dispatch = get_clock();
            return;
        }
    }

    PCB *next_job = deQueue(&ready_queue);
    if (next_job == NULL) {
        PTBR = NULL;
        return;
    }

    next_job->status = running;
    PTBR = next_job->page_tbl;
    prepage(next_job);
    next_job->last_dispatch = get_clock();
    set_timer(Quantum);

}



void insert_ready(PCB *pcb)
{
    pcb->priority = 0;
    pcb->status = ready;
    enQueue(&ready_queue, pcb);
}


char *toString(PCB *pcb)
{
}


int compareTo(PCB *pcb1, PCB *pcb2)
{
}


/* end of module */

