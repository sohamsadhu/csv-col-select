// The program will be able to read a CSV file and then output only the
// columns that are not mentioned in the command line alongwith the input file.

import java.io.FileNotFoundException ;
import java.io.IOException ;
import java.io.BufferedReader ;
import java.io.FileReader ;
import java.util.Arrays ;
import java.util.List ;

public class RemoveCSVColsProg
{
  public boolean isCSVFileCheck(String ip_file)
  {
    int dotpos = ip_file.lastIndexOf(".") ;
    String ext = ip_file.substring(dotpos + 1, ip_file.length()) ;
    if(ext.equals("csv")) {
      return true ;
    } else {
      System.out.println("The input file should be a comma separated (CSV) format file.") ;
      System.out.println("Usage: java RemoveCSVColsProg input_file.csv cols_not_required*") ;
      return false ;
    }
  }

  public void fileAccessCheck(String [] file_n_cols)
  {
    StringBuilder contents = new StringBuilder() ;
    try {
      BufferedReader input = new BufferedReader(new FileReader(file_n_cols[0])) ;
      try {
        String line = null ;
        while((line = input.readLine()) != null) {
          contents.append(line) ;
          contents.append(System.getProperty("line.separator")) ;
        }
      } catch(IOException ioex) {
        ioex.printStackTrace() ;
        System.exit(0) ;
      } finally {
        input.close() ;
      }
    } catch(FileNotFoundException fnfe) {
      System.err.println("The file " + file_n_cols[0] + " does not exist") ;
      System.exit(0) ;
    }  catch(Exception e) {
      System.err.println("Error: " + e.getMessage()) ;
      System.exit(0) ;
    }
    wellFormedFile(contents.toString(), file_n_cols) ;
  }

  public void wellFormedFile(String fcontent, String [] file_n_cols)
  {
    String [] lines = fcontent.split(System.getProperty("line.separator")) ;
    int rowlength = lines[0].split(",").length ;
    for(String line : lines) {
      if(rowlength != line.split(",").length) {
        System.out.println("Error: The number of columns in the input CSV file is not consistent.") ;
        System.exit(0) ;
      }
    }
    fileHasFields(fcontent, file_n_cols) ;
  }

  public void fileHasFields(String fcontent, String [] file_n_cols)
  {
    String [] lines = fcontent.split(System.getProperty("line.separator")) ;
    String [] hrow = lines[0].split(",") ;
    for(int i = 0; i < hrow.length; i++) {
      hrow[i] = hrow[i].trim() ;
    }
    if((file_n_cols.length - 1) > hrow.length) {
      System.out.println("The column arguments provided are more than present in input csv file.") ;
      System.exit(0) ;
    }
    for(int i = 1; i < file_n_cols.length; i++) {
      if(!Arrays.asList(hrow).contains(file_n_cols[i])) {
        System.out.println("The columns to be removed do not match that in the input file.") ;
        System.exit(0) ;
      }
    }
    showColumns(fcontent, file_n_cols) ;
  }

  public void showColumns(String fcontent, String [] file_n_cols)
  {
    String [] lines = fcontent.split(System.getProperty("line.separator")) ;
    if((file_n_cols.length - 1) == 0) { // Nothing removed, show entire file.
      for(String line : lines) {
        System.out.println(line) ;
      }
    } else if((file_n_cols.length - 1) == lines[0].split(",").length) {
      // Do nothing. No data needs to be displayed.
    } else {
      String [] col_header = lines[0].split(",") ;
      for(int i = 0; i < col_header.length; i++) {
        col_header[i] = col_header[i].trim() ;
      }
      Integer [] cols_to_remove = new Integer[file_n_cols.length - 1] ;
      List col_list = Arrays.asList(col_header) ;
      for(int i = 1; i < file_n_cols.length; i++) {
        cols_to_remove[i - 1] = col_list.indexOf(file_n_cols[i]) ;
      }
      for(String line : lines) {
        String disp_str = new String("") ;
        String [] show_line = line.split(",") ;
        for(int i = 0; i < show_line.length; i++) {
          if(!Arrays.asList(cols_to_remove).contains(i)) {
            disp_str += "," + show_line[i] ;
          }
        }
        System.out.println(disp_str.substring(1, disp_str.length())) ;
      }
    }
  }

  public static void main(String [] args)
  {
    RemoveCSVColsProg R = new RemoveCSVColsProg() ;
    if(args.length < 1) {
      System.out.println("Usage: java RemoveCSVColsProg input_file.csv cols_not_required*") ;
      System.exit(0) ;
    }
    if(R.isCSVFileCheck(args[0])) {
      R.fileAccessCheck(args) ;
    } else {
      System.exit(0) ;
    }
  }
}
