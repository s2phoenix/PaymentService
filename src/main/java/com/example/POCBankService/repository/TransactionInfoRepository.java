package com.example.POCBankService.repository;

import com.example.POCBankService.entity.TransactionInfo;
import com.example.POCBankService.entity.UserInfo;
import com.example.POCBankService.model.AccountBalance;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TransactionInfoRepository extends JpaRepository<TransactionInfo, Long> {
    List<TransactionInfo> findByUserAccount(UserInfo user);
    Optional<TransactionInfo> findTopByUserAccountOrderByTransactionDateDescTransactionTimeDesc(UserInfo user);
    List<TransactionInfo> findByUserAccount_AccountOrderByTransactionDateDescTransactionTimeDesc(String account);

    @Query(value = """
    SELECT 
        u.account AS accountId,
        u.eng_name AS accountName,
        COALESCE((
            SELECT t.balance
            FROM reborn.transaction_info t
            WHERE t.user_account = u.account
            ORDER BY t.transaction_date DESC, t.transaction_time DESC
            LIMIT 1
        ), 0) AS balance
    FROM reborn.user_info u
    WHERE u.user_id = :userId
""", nativeQuery = true)
    List<AccountBalance> findAllAccountsWithLatestBalance(@Param("userId") String userId);
}
