### Viagogo Programming Test

## Instructions

To compile the program, run:
`javac VaigogoTest.java`

To run the program, run:
`java ViagogoTest`

## Assumptions
- Event with 0 tickets will be filtered out since there will be no lowest ticket price
- Ticket always has positive price
- member function getCheapestTicketPrice for Event will return 999999.0 when there is no tickets,
this value will not be used though.

## Q & A

Q: How might you change your program if you needed to support multiple events at the same location?

A: It will work with multiple events at the same location, but ranking could be a problem. It depends on what criteria we choose for ranking. One possible criterion could be ticket price, or we could simply rank based on Lexicographical order, after the result array has been sorted with distance to user.

Q: How would you change your program if you were working with a much larger world size:

A: For now, I simply used a sort method to get nearest event, with a customized comparator that calculates manhattan distance between two points. Sorting will take O(nlogn) time complexity which is acceptable. Say if the world size is a bit bigger, I can reduce some use of space. For now I am making a copy of events array list to extract closest events. If it is ok to modify original events array list, I can simply just sort that and print out the closest five. This will reduce space complexity by half. 

However if the world size is very large, say that it can be larger than the memory of a machine, we have to use database system to store these data. Querying nearest event with user location will be a request processed by the database system. There are DBs optimized for geo-location storage, and for searching nearest points (for instance, spatial index in mysql). I can take advantage of these if I want want to perform query in a much larger world.
