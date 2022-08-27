package com.techelevator.tenmo.dao;


import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDTO;
import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import org.intellij.lang.annotations.Language;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao{

    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }

    @Override
    public Transfer create(Transfer transfer) {
        @Language("SQL")
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

    @Override
    public List<TransferDTO> getTransfersByUser(User user) {
        List<TransferDTO> transferDTOs=new ArrayList<>();
        @Language("SQL")
        String sql="SELECT transfer.transfer_id,tenmo_user.username,transfer.amount " +
                "FROM transfer " +
                "JOIN account ON transfer.account_to = account.account_id " +
                "JOIN tenmo_user ON account.user_id = tenmo_user.user_id " +
                "WHERE transfer.account_from=(SELECT account_id FROM account JOIN tenmo_user ON account.user_id=tenmo_user.user_id WHERE tenmo_user.user_id=?)";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, user.getUserId());
        while(results.next()){
            transferDTOs.add(mapRowToTransferDTOSent(results,user));
        }
        sql="SELECT transfer.transfer_id,tenmo_user.username,transfer.amount " +
                "FROM transfer " +
                "JOIN account ON transfer.account_from = account.account_id " +
                "JOIN tenmo_user ON account.user_id = tenmo_user.user_id " +
                "WHERE transfer.account_to=(SELECT account_id FROM account JOIN tenmo_user ON account.user_id=tenmo_user.user_id WHERE tenmo_user.user_id=?)";
        results = jdbcTemplate.queryForRowSet(sql, user.getUserId());
        while(results.next()){
            transferDTOs.add(mapRowToTransferDTOReceived(results,user));
        }
        Collections.sort(transferDTOs);
        return transferDTOs;
    }

    @Override
    public TransferDTO getTransferById(Long id){
        TransferDTO transferDTO;
        @Language("SQL")
        String sql="SELECT t.transfer_id, tt.transfer_type_desc, ts.transfer_status_desc, t.amount, t.account_from, t.account_to " +
                "FROM transfer t " +
                "JOIN transfer_status ts ON ts.transfer_status_id = t.transfer_status_id " +
                "JOIN transfer_type tt ON tt.transfer_type_id = t.transfer_type_id " +
                "WHERE t.transfer_id = ?;";
        transferDTO=jdbcTemplate.queryForObject(sql,TransferDTO.class,id);
        return transferDTO;
    }

    private TransferDTO mapRowToTransferDTOSent(SqlRowSet rs,User user) {
        TransferDTO transferDTO=new TransferDTO();
        transferDTO.setTransferId(rs.getLong("transfer_id"));
        transferDTO.setUsernameFrom(user.getUsername());
        transferDTO.setUsernameTo(rs.getString("username"));
        transferDTO.setAmount(rs.getBigDecimal("amount"));
        transferDTO.setFromPrincipal(true);
        return transferDTO;
    }

    private TransferDTO mapRowToTransferDTOReceived(SqlRowSet rs, User user) {
        TransferDTO transferDTO=new TransferDTO();
        transferDTO.setTransferId(rs.getLong("transfer_id"));
        transferDTO.setUsernameFrom(rs.getString("username"));
        transferDTO.setUsernameTo(user.getUsername());
        transferDTO.setAmount(rs.getBigDecimal("amount"));
        return transferDTO;
    }
}
