## SQL JOINs

- work and solutions

  - 1. Provide a table for all the for all **web_events** associated with account **name** of `Walmart`. There should be three columns. Be sure to include the `primary_poc`, time of the event, and the `channel` for each event. Additionally, you might choose to add a fourth column to assure only `Walmart` events were chosen.

       ```
       SELECT a.primary_poc, w.occurred_at, w.channel, a.name
       FROM web_events w
       JOIN accounts a
       ON w.account_id = a.id
       WHERE a.name = 'Walmart';
       ```

    2. Provide a table that provides the **region** for each **sales_rep** along with their associated **accounts**. Your final table should include three columns: the region **name**, the sales rep **name**, and the account **name**. Sort the accounts alphabetically (A-Z) according to account name.
    
       ```
       SELECT r.name region, s.name rep, a.name account
       FROM sales_reps s
       JOIN region r
       ON s.region_id = r.id
       JOIN accounts a
       ON a.sales_rep_id = s.id
       ORDERD BY a.name
       ```
  
- LEFT and RIGHT JOIN

  - INNER JOIN:Notice **every** JOIN we have done up to this point has been an **INNER JOIN**. That is, we have always pulled rows only if they exist as a match across two tables.
  - LEFT JOIN 
  - If there is not matching information in the **JOIN**ed table, then you will have columns with empty cells.pulls all the data that exists in both tables, as well as all of the rows from the table in the **FROM** even if they do not exist in the **JOIN** statement.
  - RIGHT JOIN
  - pulls all the data that exists in both tables, as well as all of the rows from the table in the **JOIN** even if they do not exist in the **FROM** statement.
  - 

