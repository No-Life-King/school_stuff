#include "queueLibrary.h"
#include "devices.h"
#include <stdlib.h>


/****************************************************************************/
/*									    */
/*									    */
/*				Module DEVICES				    */
/*                             Internal Routines                            */
/*									    */
/*									    */
/****************************************************************************/


Queue device_iorbs[MAX_DEV];
QueueIterator current_iorbs[MAX_DEV];

void devices_init()
{
	for (int x=0; x<MAX_DEV; x++) {
		Queue *iorbs = malloc(sizeof(Queue *));
		initQueue(iorbs);
		device_iorbs[x] = *iorbs;

		QueueIterator *current = malloc(sizeof(QueueIterator *));
		current_iorbs[x] = *current;

		Dev_Tbl[x].busy = false;
		Dev_Tbl[x].iorb = NULL;
	}
}



void enq_io(iorb)
IORB *iorb;
{
}



void deq_io(iorb)
IORB *iorb;
{
}



void purge_iorbs(pcb)
PCB *pcb;
{
}


/* end of module */
