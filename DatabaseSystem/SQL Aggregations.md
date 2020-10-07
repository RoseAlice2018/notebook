## SQL Aggregations

- SUM

  - Unlike **COUNT**, you can only use **SUM** on numeric columns. However, **SUM** will ignore **NULL** values, as do the other aggregation functions you will see in the upcoming parts.

  - Quiz:SUM

    - ```
      1.Find the total amount of poster_qty paper ordered in the orders table
      
      SELECT SUM(poster_qty) AS total_standard_sales
      FROM  orders
      ```

    - ```
      2.Find the total amount of standard_qty paper ordered in the orders table.
      SELECT SUM(standard_qty) AS total_standard_sales
      FROM orders;
      ```

- MIN&MAX

  - Notice that **MIN** and **MAX** are aggregators that again ignore **NULL** values. 
  - Tip:Functionally, **MIN** and **MAX** are similar to **COUNT** in that they can be used on non-numerical columns. Depending on the column type, **MIN** will return the lowest number, earliest date, or non-numerical value as early in the alphabet as possible. As you might suspect, **MAX** does the opposite—it returns the highest number, the latest date, or the non-numerical value closest alphabetically to “Z.”

- AVG(average)

  - Similar to other software **AVG** returns the mean of the data - that is the sum of all of the values in the column divided by the number of values in a column. This aggregate function again ignores the **NULL** values in both the numerator and the denominator.

    If you want to count **NULL**s as zero, you will need to use **SUM** and **COUNT**. However, this is probably not a good idea if the **NULL** values truly just represent unknown values for a cell.

  - Tip:One quick note that a median might be a more appropriate measure of center for this data, but finding the median happens to be a pretty difficult thing to get using SQL alone — so difficult that finding a median is occasionally asked as an interview question.

  - Quiz:MIN,Max,AVG

    - ```
      1.When was the earliest order ever placed? You only need to return the date.
      
      SELECT MIN(occurred_at) AS earlistDate
      FROM orders;
      
      ```

    - ```
      2.Try performing the same query as in question 1 without using an aggregation function.
      
      SELECT occurred_at
      FROM orders
      ORDER BY occurred_at
      LIMIT 1;
      ```

    - ```
      3.When did the most recent (latest) web_event occur?
      
      SELECT MAX(occurred_at) AS latestDate
      FROM web_events;
      ```

    - ```
      4.Try to perform the result of the previous query without using an aggregation function.
      
      SELECT occurred_at
      FROM web_events
      ORDER BY occurred_at DSEC
      LIMIT 1;
      ```

    - ```
      5.Find the mean (AVERAGE) amount spent per order on each paper type, as well as the mean amount of each paper type purchased per order. Your final answer should have 6 values - one for each paper type for the average number of sales, as well as the average amount.
      
      SELECT AVG(standard_qty) mean_standard, AVG(gloss_qty) mean_gloss, 
                 AVG(poster_qty) mean_poster, AVG(standard_amt_usd) mean_standard_usd, 
                 AVG(gloss_amt_usd) mean_gloss_usd, AVG(poster_amt_usd) mean_poster_usd
      FROM orders;
      
      ```

    - ```
      6.You might be interested in how to calculate the MEDIAN. Though this is more advanced than what we have covered so far try finding - what is the MEDIAN total_usd spent on all orders?
      
      
      SELECT *
      FROM (SELECT total_amt_usd
            FROM orders
            ORDER BY total_amt_usd
            LIMIT 3457) AS Table1
      ORDER BY total_amt_usd DESC
      LIMIT 2;
      
      Since there are 6912 orders - we want the average of the 3457 and 3456 order amounts when ordered. This is the average of 2483.16 and 2482.55. This gives the median of 2482.855. This obviously isn't an ideal way to compute. If we obtain new orders, we would have to change the limit. SQL didn't even calculate the median for us. The above used a SUBQUERY, but you could use any method to find the two necessary values, and then you just need the average of them.
      ```

- GROUP BY

  - **GROUP BY** can be used to aggregate data within subsets of the data. For example, grouping for different accounts, different regions, or different sales representatives.
  - Any column in the **SELECT** statement that is not within an aggregator must be in the **GROUP BY** clause.
  - The **GROUP BY** always goes between **WHERE** and **ORDER BY**.
  - **ORDER BY** works like **SORT** in spreadsheet software.
  - Tip: