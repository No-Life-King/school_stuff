(* SML comments appear like this *)
(* Philip Smith *)

(* #1 - pow *)
(* The integer power of a raised to the b. *)
fun pow (a, b) = if b=0 then 1 else a*pow(a, b-1);


(* #2 - sumTo *)
(* The sum from n=1 to x of 1/n. Accepts an int and returns a real. *)
fun sumTo x = if x = 0 then 0.0 else 1.0/real(x) + sumTo(x-1);
               
(* #3 - repeat *)
(* Returns a string containing the string s repeated n times. *)
fun repeat (s, n) = if n=0 then "" else s ^ repeat(s, n-1);

(* #4 - binary *)
(* Returns a string containing the binary representation of the integer x. *)
fun binary x = if x=0 then "0" else if x mod 2 = 1 then binary(x div 2) ^ "1" else binary(x div 2) ^ "0";

(* #5 - countNegative *)
(* Counts the number of negative numbers in a list of integers. *)
fun countNegative x = if null x then 0 else if hd x < 0 then 1 + countNegative(tl x) else 0 + countNegative(tl x);

(* A helper function that returns the absolute value of a tuple. *)
fun absTuple(x, y) = (abs x, abs y);

(* #6 - absList *)
(* Returns a list of tuples containing the absolute values of the tuples in list x. *)
fun absList x = if null x then [] else [absTuple(hd x)] @ absList(tl x);

(* #7 - split *)
fun split x =
    if null x then
        []
    else if hd x = 0 then
        [(0, 0)] @ split(tl x)
    else if hd x mod 2 = 0 then
        [(hd x div 2, hd x div 2)] @ split(tl x)
    else [(hd x div 2, hd x div 2 + 1)] @ split(tl x);

(* #8 - isSorted *)
(* Returns true is the list is sorted - otherwise false. *)
fun isSorted x = if length x = 0 orelse length x = 1 then true else if hd x > hd(tl x) then false else isSorted(tl x);

 
(* #9 - collapse *)
(* Sums every two elements in a list returning a new list full of the sums. *)
fun collapse x = if null x then [] else if length x = 1 then [hd x] else [hd x + hd(tl x)] @ collapse(tl(tl x));

        
(* #10 - insert *)
(* Inserts a number into a sorted list in the appropriate position. *)
fun insert (n, x) = if null x then
                        [n]
                    else if length x = 1 then
                        if n < hd x then
                            [n] @ x
                        else x @ [n]
                    else if n > hd x then
                        [hd x] @ insert(n, tl x)
                    else [n] @ x;

