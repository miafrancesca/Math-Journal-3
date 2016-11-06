# Math-Journal-3
Chemical Equations

EDIT 11/5: Fixed small bugs that prevented execution for edge cases

Reflections:

This program is still a work in progress. When I began, I didn't realize how many additional considerations and obstacles would pop up. The goal of this program is to balance chemical equations. It relates to math because it involves systems of equations to equate the coefficients of the molecules. Program is still a work in progress, but contains algorithms to load Equation objects from user input, can decipher if the sides of the equation are balanced, and loads the linear system of equations, for any size of equation. 

I had a lot of trouble starting this program: figuring out what structure I wanted to use and overcoming programming obstacles, particularly with loading Molecule objects into a map in the Equation objects. The math involved in the program is very simple, for an unbalanced equation, for example x1C6H12O6 + x2O2 = x3H20 + x4CO2, where x1 through x4 are the coefficients. For example, one equation (considering the element carbon) is 6x1 = x4, or 6x1 + 0x2 + 0x3 -x4 = 0, or [6,0,0,-1,0] as an array]. Then, becuase there are 3 elements, the 2D array for the system of equations has 3 rows, and 5 columns (number of molecules (4) + a constant (0)). The coefficients are found by solving the system, for the smallest possible combination of integer coefficients.

Another mathematical feature is the greatest common factor method, which finds simplifies the coefficients of the equation, if there is a GCF > 1. For instance, 6H2O = 12H + 3O2 becomes 2H2O = 4H + O2, by finding the greatest common factor of the first two numbers in the array [6, 12, 3] and then comparing it to the greatest common factor of the old gcf with the next number. 

Another consideration was making the coefficients integers, instead of doubles (decimals). The program is written with all integers, for simplicity. While obviously the coefficients for atoms in a molecule should be integers (the 2 in H2O needs to be an integer, not a decimal!), it is feasible to use doubles for the coefficients of whole molecules, as functionality for figuring out how many moles of a product would result from (for instance) .6 moles of a reactant. More consideration would be needed to reconfigure the greatest common factor methods, becuase 2.6 and 3.9 should ideally have 1.3 as a greatest common factor, and perhaps simplify to 2 and 3, but .5 and 1, could be left as is, or be multiplied by their gcf of .5, to 1 and 2. 

Given free time, I will continue working on this, specifically working on solving systems of equations in Java, when the size of the system and equations themselves are unknown.

Lastly, apologies for uncommented, and at times redundant code. I will reupload at a later date, as I'll continue working on this project, which I've had a great deal of fun creating.
