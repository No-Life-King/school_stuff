import datetime


def get_stats():
    """
    Reads data from a record file and returns the dates of the record hi and low
    as well as the number of highs and lows recorded between certain intervals.
    """
    
    # creates a date object set to Jan. 1st of a leap year
    date = datetime.date(2016, 1, 1)
    
    # initializes the variables that need to be returned
    hottest_day_ever = date
    coldest_day_ever = date
    hottest_temp = -9000
    coldest_temp = 9000
    highs_1874_1915 = 0
    highs_1916_1958 = 0
    highs_1959_2008 = 0
    lows_1874_1915 = 0
    lows_1916_1958 = 0
    lows_1959_2008 = 0
    
    # opens the records file in read mode
    records_file = open("records.txt", 'r')
    
    # loops through each record in the file pulling out the relevant data
    # and checking which category the data belong to
    for record in records_file:
        
        #cleans up the line entry and splits it into usable parts
        entry = record.strip().split('\t')
        record_high = int(entry[0])
        record_low = int(entry[2])
        high_year = int(entry[1])
        low_year = int(entry[3])
        
        # checks to see if the high or low are record setting, if they are,
        # it stores a date with the day that the line number refers to and
        # the appropriate year
        if record_high > hottest_temp:
            hottest_temp = record_high
            hottest_day_ever = date.replace(high_year)
        elif record_low < coldest_temp:
            coldest_temp = record_low
            coldest_day_ever = date.replace(low_year)
            
        # checks to see if the high should be added to a category
        if high_year > 1958:
            highs_1959_2008 += 1
        elif high_year > 1915:
            highs_1916_1958 += 1
        elif high_year > 1873:
            highs_1874_1915 += 1
        
        # checks to see if the low should be added to a category
        if low_year > 1958:
            lows_1959_2008 += 1
        elif low_year > 1915:
            lows_1916_1958 += 1
        elif low_year > 1873:
            lows_1874_1915 += 1
            
        # increments the day of the date object by 1 to keep track of the days by line number 
        date += datetime.timedelta(1)
        
    return hottest_day_ever, hottest_temp, coldest_day_ever, coldest_temp, highs_1874_1915, highs_1916_1958, highs_1959_2008, lows_1874_1915, lows_1916_1958, lows_1959_2008


hottest_day_ever, hottest_temp, coldest_day_ever, coldest_temp, highs_1874_1915, highs_1916_1958, highs_1959_2008, lows_1874_1915, lows_1916_1958, lows_1959_2008 = get_stats()

# create an answer file 
answer_file = open("answers.txt", 'w')

# builds a list of text that is formatted properly to be written to a file
lines = ["\t~ Wilmington Weather Stats ~\n"]
lines.append("The hottest day ever in Wilmington was " + hottest_day_ever.strftime("%A, %B %d, %Y") + ". It was " + str(hottest_temp) + " degrees.\n")
lines.append("The coldest day ever in Wilmington was " + coldest_day_ever.strftime("%A, %B %d, %Y") + ". It was " + str(coldest_temp) + " degrees.\n")
lines.append(str(highs_1874_1915) + " highs were recorded from 1874 to 1915.\n")
lines.append(str(lows_1874_1915) + " lows were recorded from 1874 to 1915.\n")
lines.append(str(highs_1916_1958) + " highs were recorded from 1916 to 1958.\n")
lines.append(str(lows_1916_1958) + " lows were recorded from 1916 to 1958.\n")
lines.append(str(highs_1959_2008) + " highs were recorded from 1959 'til now.\n")
lines.append(str(lows_1959_2008) + " lows were recorded from 1959 'til now.\n")

# finally writes all of the lines to the file
answer_file.writelines(lines)
