package io.swagger.repository;

import io.swagger.model.dto.TransactionRequestDTO;
import io.swagger.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TransactionFilterRepository {

    @Autowired
    EntityManager entityManager;

    public List<Transaction> getTransactionByRequest(
            TransactionRequestDTO request

    ){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Transaction> criteriaQuery = criteriaBuilder.createQuery(Transaction.class);
        List<Predicate> predicates = new ArrayList<>();

        //select from Transaction
        Root<Transaction> root = criteriaQuery.from(Transaction.class);
        if(request.getAmount() != null){
            Predicate amountPredicate = criteriaBuilder
                    //amount fromm the Transaction class what is to be shown in ui
                    .equal(root.get("amount"),request.getAmount());
                               //request methods from the request dtos from what will be requested
            predicates.add(amountPredicate);
        }

        if(request.getIbanFrom() != null && request.getIbanTo() != "NL01INHO0000000001"){
            Predicate ibanFromPredicate = criteriaBuilder
                    .like(root.get("accountFrom"),"%"+ request.getIbanFrom()+ "%");
            predicates.add(ibanFromPredicate);
        }

        if(request.getIbanTo() != null && request.getIbanTo() != "NL01INHO0000000001"){
            Predicate ibanToPredicate = criteriaBuilder
                    .like(root.get("accountTo"),"%"+ request.getIbanTo()+ "%");
            predicates.add(ibanToPredicate);
        }

        //Get all date and time from certain date
        if(request.getDateFrom() != null){
            Predicate dateFromPredicate = criteriaBuilder
                    .greaterThanOrEqualTo(root.get("executionDate"),request.getDateFrom());
            predicates.add(dateFromPredicate);

        }

        //Get all date and time until certain date
        if(request.getDateTo() != null ){
            Predicate dateToPredicate = criteriaBuilder
                    .lessThanOrEqualTo(root.get("executionDate"), request.getDateTo());
            predicates.add(dateToPredicate);
        }

        // Get date and time between two dates
        if (request.getDateFrom() != null && request.getDateTo() != null && request.getDateFrom().isBefore(request.getDateTo()))
        {
            Predicate datePredicate = criteriaBuilder
                    .between(root.get("executionDate"),request.getDateFrom(),request.getDateTo());
            predicates.add(datePredicate);
        }

        if(request.getOperator() != null){
            Predicate operatorPredicate = criteriaBuilder
                    .equal(root.get("userPerforming"), request.getOperator() );
            predicates.add(operatorPredicate);
        }

        criteriaQuery.where(
                criteriaBuilder.and(predicates.toArray(new Predicate[0]))
        );
        TypedQuery<Transaction> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }
}
