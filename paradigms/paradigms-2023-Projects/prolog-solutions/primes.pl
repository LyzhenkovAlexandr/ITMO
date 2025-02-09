check_prime(2, 2):- !.
check_prime(3, 2):- !.
check_prime(N, 2):- cache(N), !.
check_prime(N, D):-
	D * D > N,
	!.

check_prime(N, D):-
	MOD is mod(N, D),
	MOD > 0,
	D1 is D + 1,
	check_prime(N, D1),
	!.

prime(N) :-
    number(N),
	N >= 2,
	check_prime(N, 2),
	assert(cache(N)).

composite(N) :-
	\+ prime(N).


find_prime(N, NEXT):-
    prime(N),
    NEXT is N,
    !.

find_prime(N, NEXT):-
    N1 is N + 1,
    find_prime(N1, NEXT),
    !.

check_divs(1, 2, [], 1):- !.
check_divs(N, D, [N], M) :-
    N >= 2,
    M < D * D,
    !.
check_divs(N, D, [], M) :-
    M < D * D,
    !.
check_divs(N, H, [H | T], M) :-
    MOD is mod(N, H),
    MOD = 0,
    N1 is div(N, H),
    check_divs(N1, H, T, M), !.

check_divs(N, D, DIVS, M) :-
    D1 is D + 1,
    find_prime(D1, D2),
    check_divs(N, D2, DIVS, M).

prime_divisors(1, []) :- !.
prime_divisors(N, [N]) :- prime(N), !.
prime_divisors(N, DIVS) :-
    N > 0,
    check_divs(N, 2, DIVS, N), !.

square_divisors(N, DIVS):-
	SQUARE is N * N,
	prime_divisors(SQUARE, DIVS).

cube_divisors(N, DIVS):-
	CUBE is N * N * N,
	prime_divisors(CUBE, DIVS).