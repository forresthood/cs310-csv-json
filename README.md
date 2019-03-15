# cs310-csv-json
This is a simple program written in java to convert json objects to csv and vice versa.
The main class reads the files from where they're stored and passes them to the functions, then prints the results to the screen once finished.
The converter class has two methods csvtoJson and jsontoCsv. csvtoJson reads all of the contents from the csv file into a buffer, parses the buffer, converts the data to the json format, and returns it as a json string. jsontoCsv takes a jsonstring as its arguement and converts it into a json object. It then parses the json object into different arrays for column, row, and data, uses CSVWriter to convert it to the csv data format, then returns it as a string.
