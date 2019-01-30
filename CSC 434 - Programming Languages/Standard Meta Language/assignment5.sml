(* Author: Philip Smith *)

(* Accepts a list and returns a list that contains each item twice. *)
fun dupList x = foldr(fn(a, b) => a::a::b) [] x;

(* Counts the number of elements in a list. *)
fun mylength x = foldr(fn(_, b) => 1 + b) 0 x;

(* Accepts a list of integers and returns their real absolute values. *)
fun il2absrl x = map(fn a => real(abs(a))) x;

(* Accepts a list of characters and returns a string containing those characters in the same order. *)
fun myimplode x = foldr(fn(a, b) => str(a) ^ b) "" x;

(* Returns the maximum value in a list of integers. *)
fun max x = foldr(fn(a,b) => if a > b then a else b) ~1073741824 x;

(* Returns a list of all values less than e that are in list L *)
fun less(e, L) = foldr(fn(a, b) => if a < e then a::b else b) [] L;

(* Works like foldl *)
fun myfoldl a b c = case c of
                        nil => b |
                        first::rest => myfoldl a (a(first, b)) rest;