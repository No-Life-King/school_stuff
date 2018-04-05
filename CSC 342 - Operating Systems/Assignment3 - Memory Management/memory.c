#include <stdlib.h>
#include <stdio.h>
#include "memory.h"
#include "queue.h"


/****************************************************************************/
/*                                                                          */
/*                                                                          */
/*                              Module MEMORY                               */
/*                            Internal Routines                             */
/*                                                                          */
/*                                                                          */
/****************************************************************************/

Queue frame_queue;

char *toString(FRAME *f)
{
    static char result[BUFSIZ];

    // For each from that is printed, print the frame number, pcb id of the
    // process that owns it, the page number of the page it contains,
    // D or C if it is dirty or clean, and the lock count.

    if (f == NULL) sprintf (result, "(null) ");
    else sprintf (result, "Frame %d(pcb-%d,page-%d,%c,lock-%d) ",
               f->frame_id,
               f->pcb->pcb_id,
               f->page_id,
               (f->dirty ? 'D' : 'C'),
               f->lock_count);

    return result;
}


void memory_init()
{
	initQueue(&frame_queue);
}



void reference(int logic_addr, REFER_ACTION action) {

	if (PTBR != NULL) {
		PCB *pcb = PTBR->pcb;

		int page_num = logic_addr / PAGE_SIZE;
		int offset = logic_addr % PAGE_SIZE;

		if (page_num >= 0 && page_num < MAX_PAGE && offset >= 0 && offset < PAGE_SIZE) {
			PAGE_ENTRY page = pcb->page_tbl->page_entry[page_num];

			if (page.valid != true) {
				Int_Vector.cause = pagefault;
				Int_Vector.pcb = pcb;
				Int_Vector.page_id = page_num;
				gen_int_handler();
			}

			if (action == store) {
				Frame_Tbl[page.frame_id].dirty = true;
			}

			QueueNode *node = findNode(&frame_queue, page.frame_id);

			if (node) {
				removeNode(&frame_queue, node);
				enQueue(&frame_queue, page.frame_id);
			} else {
				enQueue(&frame_queue, page.frame_id);
			}

			int physical_addr = page.frame_id * PAGE_SIZE + offset;
			memoryAccess(action, page.frame_id, offset);

		} else {
			fprintf(stderr, "Somehow magically an invalid page number or offset has been generated.");
		}
	}

}



void prepage(PCB *pcb)
{

}


int start_cost(PCB *pcb)
{
	return 0;
}



void deallocate(PCB *pcb) {

	for (int x=0; x<MAX_PAGE; x++) {
		if (pcb->page_tbl->page_entry[x].valid) {
			int frame = pcb->page_tbl->page_entry[x].frame_id;
			Frame_Tbl[frame].free = true;
			Frame_Tbl[frame].pcb = NULL;
			Frame_Tbl[frame].dirty = false;

			removeNode(&frame_queue, findNode(&frame_queue, frame));
		}
	}
}



void get_page(PCB *pcb, int page_id) {
	int x = 0;
	FRAME *frame;

	for (x; x<MAX_FRAME; x++) {
		if (Frame_Tbl[x].free && !Frame_Tbl[x].lock_count) {
			frame = &Frame_Tbl[x];
			break;
		}
	}

	if (frame == NULL) {
		QueueNode *current = frame_queue.head;
		int victim = -1;

		while (current != NULL) {
			int frame_id = current->frame_id;

			if (!Frame_Tbl[frame_id].lock_count) {
				victim = frame_id;
			}

			current = current->next;
		}

		if (victim != -1) {
			frame = &Frame_Tbl[victim];
			if (frame->dirty) {
				frame->lock_count = 1;
				siodrum(write, frame->pcb, frame->page_id, victim);
				frame->dirty = false;
			}

			frame->pcb->page_tbl->page_entry[frame->page_id].valid = false;
		}
	}


	frame->free = false;
	frame->dirty = false;
	frame->pcb = pcb;
	frame->page_id = page_id;
	frame->lock_count = 1;

	siodrum(read, pcb, page_id, frame->frame_id);
	pcb->page_tbl->page_entry[page_id].frame_id = frame->frame_id;
	pcb->page_tbl->page_entry[page_id].valid = true;

	QueueNode *node = findNode(&frame_queue, frame->frame_id);
	if (node) {
		removeNode(&frame_queue, node);
		enQueue(&frame_queue, frame->frame_id);
	} else {
		enQueue(&frame_queue, frame->frame_id);
	}

}



void lock_page(IORB *iorb) {
	int page = iorb->page_id;

	if (iorb->pcb->page_tbl->page_entry[page].valid) {
		Int_Vector.cause = pagefault;
		Int_Vector.pcb = iorb->pcb;
		Int_Vector.page_id = page;
		gen_int_handler();
	}

	int frame = iorb->pcb->page_tbl->page_entry[page].frame_id;
	if (iorb->action == read) {
		Frame_Tbl[frame].dirty = true;
	}

	Frame_Tbl[frame].lock_count++;
}



void unlock_page(IORB  *iorb)
{
	int frame = iorb->pcb->page_tbl->page_entry[iorb->page_id].frame_id;
	Frame_Tbl[frame].lock_count--;
}



/* end of module */
