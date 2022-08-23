package com.techelevator.tenmo.dao;


import com.techelevator.tenmo.model.Transfer;
import com.techelevator.util.BasicLogger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JdbcTransferDao implements TransferDao{

    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }
    @Override
    public Transfer create(Transfer transfer) {
        String sql =
                "BEGIN TRANSACTION;" +
                "UPDATE account SET balance = balance - ? WHERE account_id=?;" +
                "UPDATE account SET balance = balance + ? WHERE account_id=?;" +
                "INSERT INTO transfer(transfer_type_id, transfer_status_id, account_from, account_to, amount) VALUES (?,?,?,?,?) RETURNING transfer_id;" +
                "COMMIT;";
        Transfer newTransfer=null;
        try{
            newTransfer=jdbcTemplate.queryForObject(sql,
                                                    Transfer.class,
                                                    transfer.getAmount(),
                                                    transfer.getSenderAccount().getAccountId(),
                                                    transfer.getAmount(),
                                                    transfer.getRecipientAccount().getAccountId(),
                                                    transfer.getTransferTypeId(),
                                                    transfer.getTransferStatusId(),
                                                    transfer.getSenderAccount().getAccountId(),
                                                    transfer.getRecipientAccount().getAccountId(),
                                                    transfer.getAmount().doubleValue());
        }catch(DataAccessException e){
            BasicLogger.log(e.getMessage());
        }
        return newTransfer;
    }
}
