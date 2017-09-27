Project 2: Gomuku Model
-

Group Memebers:
-
- Jonathan Metzger
- Kien Nhan
- Joseph Wilder

Instructions to Compile Code:
-
- $ cd /PATH/testing/

Initial
- bom.jar
- bomb.jar
- referee.py

TERMINAL ONE:
- $ java -jar bomb.jar

TERMINAL TWO:
- $ java -jar bom.jar

TERMINAL THREE:
- $ python referee.py

Creates:
- end_game
- move_file


Utility Function:
-
- In our project, the utility function is actually incorporated into the 
evaluation function. If the agent ever encounters a terminal board-- that is,
a board where the game is over-- then that board receives an evaluation of 
positive or negative infinity, depending on which player has won. This would
effectively overpower other streaks on the board, and the evaluation function
would effectively act as the utility function in this scenario.

Evaluation Function:
-
- The evaluation function is used to score how "good" or "bad" a board is, 
relatively speaking. This is necessary in a game such as Gomoku, where it is
not computationally feasible to search the entire state space for each move. 
Instead, there needs to be a way to judge boards that are incomplete (where 
neither player has won). This is the job of the evaluation function. 

The basic idea behind our evaluation function is to consider all "streaks" on
the board and assign a score based off how promising that streak is. Here, 
streaks are continuous paths of same-color pieces on the board, and can be 
horizontal, vertical, or diagonal. Each time our evaluation algorithm 
encounters a piece on the board, it looks around for streaks originating at 
that piece. Once a streak has been found, the spaces on either end of the 
streak are considered in order to assign a score to the path. 

For example, a streak of 5 consecutive pieces wins the game, so such a streak
would have a score of positive infinity. A streak of 4 that is unblocked on
both sides is not a win yet, but is technically unbeatable. This case would 
also receive a score of positive infinity. However, a streak of 4 that is 
blocked on one side is worth much less, because it could potentially be blocked
on the next turn. Similarly, a streak of 3 blocked on one side is worth less 
than the same situation with 4 pieces. Streaks of 1 and 2 pieces are assigned
very small utility values, and streaks that are blocked on both sides are 
assigned a score of zero, since they could never lead to a win. 

Once all paths have been scored, the total score for the board can be 
determined by summing all the path scores on the board, where paths for the
maximizing player have positive cardinality and paths for the minimizing player
are negative. This score can then be used by the MiniMax algorithm to determine 
the optimal next move without having to traverse the entire state space. 


Additional Heuristics and Strategies:
-
- We also incorporated the use of an additional strategy to guide decision
making. Specifically, our agent always attempts to counter an open-ended
streak of three pieces as soon as it appears on the board. The reasoning 
behind this strategy is discussed below. 

Results:
-
- Tests Ran:
	- x
- Program play against human?:
 	- x
- Program play against self?:
 	- x
- Program play against other programs?:
	- x
- Program Performance:
	- x
- Strengths:
	- x
- Weaknesses:
	- x
	
Discussion on why evaluation function and heuristics chosen are good choices:

- While not perfect, our evaluation function is able to judge the relative
utility of possible boards and make an informed choice from this information.
Our particular method mimics the way a human player might evaluate the board,
albeit in a more simplistic way. For instance, a human player would recognize
that a streak of 4-in-a-row with no pieces next to either end is unblockable 
and therefore a guaranteed win. Our evaluation function encompasses that 
principle by assigning the same score to actual wins and this guaranteed win
scenario. 

Our evaluation function also judges the relative worth of streaks in the same 
manner. A human player knows that-- all else being equal-- a streak of four 
is better than a streak of three, which is better than a streak of two, and 
so on.  Our algorithm relates this fact by assigning lower scores to smaller 
streaks. An interesting case arises when deciding between a streak of 3 blocked
on both sides, or a streak of 4 blocked on one side. According to our
algorithm, the blocked streak of 4 receives a higher score. The logic here is 
that there are still more pieces in a row, and the unblocked streak of 3 could
easily be blocked on the opponents next turn, which leaves you with a blocked
streak of 4 in the best case. However, a human player would recognize that a
streak blocked on both ends can never lead to a win, so such a streak always
receives a score of zero. 

We also use an additional strategy to emulate human decision making. Whenever
our agent detects an unblocked streak of three, it will immediately attempt to
counter it. This simulates the impulse to counter that a human player might 
feel in such a situation. Anecdotally, we observed that our AI performed better
with this strategy implemented. 
