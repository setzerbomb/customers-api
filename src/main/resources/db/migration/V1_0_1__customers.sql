CREATE VIEW report_customers_by_uf AS
SELECT a.state, count(c.id) as quantity
FROM customers AS c
INNER JOIN addresses as a
    ON c.id = a.customer
GROUP BY a.state