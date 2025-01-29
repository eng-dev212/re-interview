
INSERT INTO resurs.customer (id, name, social_security_number, credit_score)
VALUES
    (1001, 'John Doe', '19900101-1234', 700),
    (1002, 'Jane Doe', '19900101-5678', 800)
ON CONFLICT DO NOTHING;


INSERT INTO resurs.transaction (customer_id, amount, transaction_type, transaction_date)
VALUES
    (1001, 100.50, 'CREDIT', '2021-01-01 12:00:00'),
    (1001, 50.75, 'DEBIT', '2021-01-02 12:00:00'),
    (1001, 100.50, 'CREDIT', '2021-01-01 12:00:00'),
    (1001, 50.75, 'DEBIT', '2021-01-02 12:00:00'),
    (1002, 200.00, 'CREDIT', '2021-01-01 12:00:00'),
    (1002, 100.00, 'DEBIT', '2021-01-02 12:00:00'),
    (1002, 700.00, 'CREDIT', '2021-01-01 12:00:00'),
    (1002, 125.00, 'DEBIT', '2021-01-02 12:00:00')
ON CONFLICT DO NOTHING;