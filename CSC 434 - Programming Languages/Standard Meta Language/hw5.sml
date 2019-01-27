(* SML comments appear like this *)
(* Philip Smith *)

(* The partitioning function used by quicksort. Returns a list of numbers greater than or equal to
a pivot and a list of numbers less than the pivot.*)
fun partition (pivot, nil) = (nil, nil)
|   partition (pivot, first::rest) =
        let val (x, y) = partition(pivot, rest)
        in if first < pivot then (first::x, y) else (x, first::y) end;

(* #1 - quicksort *)
(* Performs a quicksort on a list of integers. *)
fun quicksort nil = nil
|   quicksort [e] = [e]
|   quicksort x:int list = let val (a, b) = partition(hd x, tl x) in quicksort(a) @ [hd x] @ quicksort(b) end;


(* #2 - member *)
(* Returns true if element e is in a set. Otherwise returns false. *)
fun member(e, []) = false
|     member(e, first::rest) = if first = e then true else member(e, rest);
               
               
(* #3 - returns the union of sets (lists) s1 and s2*)
(* Returns the set union of two lists. *)
fun union(nil, nil) = nil
|   union(nil, s2::rest) = s2::union(nil, rest)
|   union(s1::rest, nil) = s1::union(rest, nil)
|   union(s1::rest, s2) = if not(member(s1, s2)) then s1::union(rest, s2) else union(rest, s2);

(* #4 - returns the intersection of sets (lists) s1 and s2 *)
(* Returns the set intersection of two lists. *)
fun intersection(nil, nil) = nil
|   intersection(nil, s2::rest) = []
|   intersection(s1::rest, nil) = []
|   intersection(s1::rest, s2) = if member(s1, s2) then s1::intersection(rest, s2) else intersection(rest, s2);

(* A function for the range function to call that includes the current state. *)
fun range_counter(start, stop, step, current) = if current >= stop andalso step > 0 then [] else if current <= stop andalso step < 0 then [] else current::range_counter(start, stop, step, current + step);

(* #5 - Return list of integers from start (inclusive) to stop (exclusive) by step *)
fun range(start, stop, step) = range_counter(start, stop, step, start);

(* A function for the slice function to call that includes a counter. *)
fun slice_count(head::rest, start, stop, count) = if count >= start andalso count < stop then head::slice_count(rest, start, stop, count+1) else if count = stop then [] else slice_count(rest, start, stop, count+1)
|   slice_count(nil, start, stop, count) = [];

(* #6 - Return a slice of a list between indices start inclusive, and stop exclusive. Assume first element of list is at index 0*)
fun slice(aList, start, stop) = slice_count(aList, start, stop, 0);