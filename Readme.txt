There are two attachments provided along with this README file, one is the implementation
program in Python3.2 and another is a input.csv file that can be used for testing.

Usage: python3.2 RemoveCSVColsProg.py input_file.csv cols_not_required*
The program has been tested with Python3.2 on Ubuntu 12.04 LTS, but should run smoothly on Windows.

The name of the columns to be removed are optional and if not provided then entire file
is printed as it is. If all columns are mentioned in command line to be removed then nothing
is printed on the console.

The code is a bit long with 102 lines. However almost 55 lines are only for error checking.
There are four functions to do so.
1. isCSVFileCheck(csv_ip_file) to check the file format has extension csv.
2. fileAccessCheck(ip_file) that file with provided name exists and can be accessed.
3. wellFormedFile(ip_file) that is the number of columns in each of the row of csv is consistent.
4. fileHasFields(file_n_fields) makes sure that column names provided match with those present
in the file. If column names are repeated resulting in more number of columns than present in file,
then message will display mentioning superflous arguments and stop execution. 
However if repeated arguments do not exceed the number of columns present in file, then the program
should function.

Function at line 60 showColumns(ip_file, col_names) does the actual work of displaying the data
associated with file as per the arguments given to remove the columns.
The first case is when no argument is provided and file can be printed on console as it is.

Otherwise as per provided column data to be removed, the data is displayed. The only downside of having
a single otherwise case is if all the columns to be removed are mentioned. And though, nothing needs to
be displayed, yet this branch will all be executed taking precious computing time.
However, I have let this remain so instead of making another special case for so. Reason is, if user
ends up duplicating some columns that make the number of arguments equal to number of columns then too,
only the columns that match with arguments will be removed. This is shown in the last test case below.

Test cases and sample runs:

1. Provide imaginary CSV file.
$ python3.2 RemoveCSVColsProg.py blah.csv
There exists no file named blah.csv

2. Provide a imaginary file.
$ python3.2 RemoveCSVColsProg.py blah.ext
The input file should be a comma separated (CSV) format file.
Usage: python3.2 RemoveCSVColsProg.py input_file.csv cols_not_required*

3. Provide more arguments for columns than that are present.
$ python3.2 RemoveCSVColsProg.py input.csv Id Make Model Year Colour make
The column arguments provided are more than present in input csv file.

4. Provide arguments for column name that are not present.
$ python3.2 RemoveCSVColsProg.py input.csv blah
The columns to be removed do not match that in the input file.

5. Provide no argument for columns. The entire file gets printed.
$ python3.2 RemoveCSVColsProg.py input.csv 
Id,Make,Model,Year,Colour
1, Ford, Fiesta, 2010, Blue
2, Honda, Civic, 1998, Silver
3, Toyota, RAV4, 2006, Black

6. Provide names for all columns. 4 blank lines printed this time. The reasons
for this coding decision has been mentioned above and supported by test run # 10.
$ python3.2 RemoveCSVColsProg.py input.csv Id Make Model Year Colour

7. Remove all columns but 1.
$ python3.2 RemoveCSVColsProg.py input.csv Id Make Model Year
Colour
Blue
Silver
Black

8. Just remove one column.
$ python3.2 RemoveCSVColsProg.py input.csv Id
Make,Model,Year,Colour
Ford,Fiesta,2010,Blue
Honda,Civic,1998,Silver
Toyota,RAV4,2006,Black

9. Test case run as per provided specification document.
$ python3.2 RemoveCSVColsProg.py input.csv Year Make
Id,Model,Colour
1,Fiesta,Blue
2,Civic,Silver
3,RAV4,Black

10. A extra case to support a design decision.
$ python3.2 RemoveCSVColsProg.py input.csv Make Make Make Make Make
Id,Model,Year,Colour
1,Fiesta,2010,Blue
2,Civic,1998,Silver
3,RAV4,2006,Black

11. It can handle column data with spaces
$ python3.2 RemoveCSVColsProg.py names.csv "First Name" "Last Name"
Full Name
Mike Logan
Lennie Briscoe
Abbie Carmichael

12. It can handle empty definitions in data columns
$ python3.2 RemoveCSVColsProg.py test.csv
A,B,C,D,E
,,,,
a, b, c, d, e
, b, c, d, e
, b, ,d,
a, , c, , e

13. It can drop outer columns
$ python3.2 RemoveCSVColsProg.py test.csv A E
B,C,D

b,c,d
b,c,d
b,,d
c,

14. Drop all columns
$ python3.2 RemoveCSVColsProg.py test.csv E C A B D
