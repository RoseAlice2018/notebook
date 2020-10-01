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

    2. 