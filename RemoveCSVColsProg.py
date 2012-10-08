# The program will be able to read a CSV file and then output only
# the columns that are not mentioned in the command line alongwith
# the input file.

import sys
import os.path
import errno

def isCSVFileCheck(csv_ip_file):
    file_extension = os.path.splitext(csv_ip_file)[1]
    if(file_extension != '.csv'):
        print('The input file should be a comma separated (CSV) format file.')
        print('Usage: python3.2 RemoveCSVColsProg.py input_file.csv cols_not_required*')
        return False
    else:
        return True

def fileAccessCheck(ip_file):
    try:
        fileacshandle = open(ip_file,'r')
    except IOError as ioex:
        if ioex.errno == errno.ENOENT :
            print("There exists no file named " + ip_file)
            return False
        elif ioex.errno in (errno.EACCES, errno.EPERM):
            print('You do not have the permissions to read file ' + ip_file)
            return False
        else:
            return False
    fileacshandle.close() # File is accessible, close handle.
    return True

def wellFormedFile(ip_file):
    '''Each line in the file should have same number of comma separated fields.'''
    fhandler = open(ip_file, 'r')
    linelength = len(fhandler.readline().rstrip('\n').rstrip('\r').split(','))
    for line in fhandler:
        if(linelength != len(line.rstrip('\n').rstrip('\r').split(','))):
            print('Error: The number of columns in the input CSV file is not consistent.')
            return False
    return True

def fileHasFields(file_n_fields):
    '''Make sure that file contains the columns that are required to be removed.'''
    if(len(file_n_fields) == 1): # Only input file name, no columns required to be removed.
        return True
    fhandler = open(file_n_fields[0], 'r')
    colnames = fhandler.readline().rstrip('\n').rstrip('\r').split(',') # Get only the first line.
    fhandler.close()
    for i in range(len(colnames)):
        colnames[i] = colnames[i].strip() # Remove the trailing and leading whitespace.
    if((len(file_n_fields) - 1) > len(colnames)):
        print('The column arguments provided are more than present in input csv file.')
        return False
    for col in file_n_fields[1:]:
        if(col not in colnames):
            print('The columns to be removed do not match that in the input file.')
            return False
    return True

def showColumns(ip_file, col_names):
    fhandler = open(ip_file, 'r')
    column_row = fhandler.readline().rstrip('\r').rstrip('\n').split(',')
    for i in range(len(column_row)):
        column_row[i] = column_row[i].strip()
    if(len(col_names) == 0): # No columns are required to be removed.
        display_string = ''
        for cols in column_row:
            display_string += ',' + cols
        print(display_string.lstrip(','))
        for line in fhandler:
            print(line.rstrip('\n').rstrip('\r'))
        fhandler.close()
        return None
    else:
        col_to_remove = []
        for col in col_names:
            col_to_remove.append(column_row.index(col))
        display_string = ''
        for i in range(len(column_row)):
            if(i not in col_to_remove):
                display_string += ',' + column_row[i]
        print(display_string.lstrip(','))
        for line in fhandler:
            display_string = ''
            showline = line.rstrip('\n').rstrip('\r').split(',')
            for i in range(len(showline)):
                showline[i] = showline[i].strip()
            for i in range(len(showline)):
                if(i not in col_to_remove):
                    display_string += ',' + showline[i]
            print(display_string.lstrip(','))
        fhandler.close()
        return None

def main():
    if(len(sys.argv) < 2):
        print('Usage: python3.2 RemoveCSVColsProg.py input_file.csv cols_not_required*')
        return None # Stop execution, exit.
    ip_file = sys.argv[1]
    if(isCSVFileCheck(ip_file) and fileAccessCheck(ip_file)):
        if(wellFormedFile(ip_file) and fileHasFields(sys.argv[1:])):
            showColumns(ip_file, sys.argv[2:])
        else:
            return None # Arguments invalid or csv file out of shape. Halt execution.
    else:
        return None # Wrong input or inaccessible file, halt execution.

main()
