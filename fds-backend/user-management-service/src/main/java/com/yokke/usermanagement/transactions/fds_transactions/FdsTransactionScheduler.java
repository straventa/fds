package com.yokke.usermanagement.transactions.fds_transactions;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

@Service
@Slf4j
@RequiredArgsConstructor
public class FdsTransactionScheduler {
    private static final int BATCH_SIZE = 1000;
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;
    //
    //    private final FdsTransactionsRepository fdsTransactionsRepository;
//    private final UserAccountRepository userAccountRepository;
//
//    public FdsTransactionScheduler(FdsTransactionsRepository fdsTransactionsRepository, UserAccountRepository userAccountRepository) {
//        this.fdsTransactionsRepository = fdsTransactionsRepository;
//        this.userAccountRepository = userAccountRepository;
//    }
//
//    @Transactional
//    public void assignAlert() {
//        boolean isAlert = true;
//        while (isAlert) {
//            Optional<FdsTransactions> fdsTransactionsOpt = fdsTransactionsRepository.findByAssignedUserAccountNull();
//            if (fdsTransactionsOpt.isEmpty()) {
//                isAlert = false;
//                break;
//            }
//            FdsTransactions fdsTransactions = fdsTransactionsOpt.get();
//            List<UserAccount> userAccounts = userAccountRepository.findByUserAccountRule_Rule_RuleIdIn(
//                    fdsTransactions.getRule().stream().map(Rule::getRuleId).collect(Collectors.toList())
//            );
//            //Select random user account
//            UserAccount userAccount = userAccounts.get((int) (Math.random() * userAccounts.size()));
//            fdsTransactions.setAssignedUserAccount(userAccount);
//            fdsTransactions.setAssignedDateTime(LocalDateTime.now());
//            fdsTransactionsRepository.save(fdsTransactions);
//            log.info("Assigned user account {} to FDS transaction {}", userAccount.getUsername(), fdsTransactions.getAuthSeqNo());
//        }
//    }
//
//    //Scheduler every 1 minutes
//    @Scheduled(fixedRate = 60000)
//    public void assignAlertScheduler() {
//        assignAlert();
//    }
//    private final FdsTransactionsRepository fdsTransactionsRepository;
//    private final UserAccountRepository userAccountRepository;
//    private final FdsTransactionsMapper fdsTransactionsMapper;
//    @Value("${app.base-url}")
//    private String baseUrl;
//    @Value("${app.scheduler}")
//    private Boolean scheduler;
//    private int currentUserIndex = 0;

//    public void assignAlert() {
//        List<UserAccount> allUserAccounts = userAccountRepository.findAll();
//        if (allUserAccounts.isEmpty()) {
//            log.warn("No user accounts found for assignment");
//            return;
//        }
//
//        int pageNumber = 0;
//        Page<FdsTransactions> page;
//        page = fdsTransactionsRepository.findFdsTransactionsByAssignedUserAccountNull(
//                PageRequest.of(pageNumber, BATCH_SIZE)
//        );
//        log.info("Fetched {} transactions", page.getNumberOfElements());
//        do {
//            log.info("Starting page fetch, pageNumber: {}", pageNumber);
//            page = fdsTransactionsRepository.findFdsTransactionsByAssignedUserAccountNull(
//                    PageRequest.of(pageNumber, BATCH_SIZE)
//            );
//            log.info("Fetched {} transactions", page.getNumberOfElements());
//            processBatch(page.getContent(), allUserAccounts);
//            pageNumber++;
//        } while (page.hasNext());
//    }

//    @Transactional  // Ensure that this method runs within an open session
//    protected void processBatch(List<FdsTransactions> unassignedTransactions, List<UserAccount> allUserAccounts) {
//        if (unassignedTransactions.isEmpty()) {
//            return;
//        }
//
//        Map<List<String>, List<FdsTransactions>> groupedTransactions = unassignedTransactions.stream()
//                .collect(Collectors.groupingBy(
//                        fdsTransaction -> fdsTransaction.getRule().stream()
//                                .map(Rule::getRuleId)
//                                .sorted()
//                                .collect(Collectors.toList())
//                ));
//
//        List<FdsTransactions> updatedTransactions = new ArrayList<>();
//
//        for (Map.Entry<List<String>, List<FdsTransactions>> entry : groupedTransactions.entrySet()) {
//            List<String> ruleIds = entry.getKey();
//            List<FdsTransactions> transactions = entry.getValue();
//
//            List<UserAccount> eligibleUserAccounts = allUserAccounts.stream()
//                    .filter(userAccount -> userAccount.getUserAccountRule().stream()
//                            .map(userAccountRule -> userAccountRule.getRule().getRuleId())
//                            .collect(Collectors.toSet())
//                            .containsAll(ruleIds))
//                    .collect(Collectors.toList());
//
//            if (eligibleUserAccounts.isEmpty()) {
//                log.warn("No eligible user accounts found for rule IDs: {}", ruleIds);
//                continue;
//            }
//
//            for (FdsTransactions transaction : transactions) {
//                UserAccount assignedUser = getNextUserAccount(eligibleUserAccounts);
//                transaction.setAssignedUserAccount(assignedUser);
//                transaction.setAssignedDateTime(LocalDateTime.now());
//                updatedTransactions.add(transaction);
//                log.info("Assigned user account {} to FDS transaction {}", assignedUser.getUsername(), transaction.getAuthSeqNo());
//            }
//        }
//
//        fdsTransactionsRepository.saveAll(updatedTransactions);
//    }

//    private UserAccount getNextUserAccount(List<UserAccount> userAccounts) {
//        if (currentUserIndex >= userAccounts.size()) {
//            currentUserIndex = 0;
//        }
//        return userAccounts.get(currentUserIndex++);
//    }
//
//    @Scheduled(fixedRate = 60000)
//    public void assignAlertScheduler() {
//        log.info("Assigning alerts...");
//        if (scheduler) {
//            assignAlert();
//        }
//    }

//    }

}
