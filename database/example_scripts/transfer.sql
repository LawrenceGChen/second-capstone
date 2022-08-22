BEGIN TRANSACTION;
UPDATE account SET balance = balance - 20 WHERE account_id=2001;
UPDATE account SET balance = balance + 20 WHERE account_id=2002;
INSERT INTO transfer
(transfer_type_id, transfer_status_id, account_from, account_to, amount) 
VALUES 
(2,2,2001,2002,20) 
RETURNING transfer_id;
COMMIT;