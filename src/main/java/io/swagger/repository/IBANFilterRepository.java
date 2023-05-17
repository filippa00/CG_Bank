package io.swagger.repository;

import io.swagger.model.dto.IbanTransactionRequestDTO;
import io.swagger.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Repository
public class IBANFilterRepository {

    @Autowired
    EntityManager entityManager;

    public List<Transaction> GetIBANTransactionsByRequest(
            IbanTransactionRequestDTO request, String Iban
    ){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Transaction> criteriaQuery = criteriaBuilder.createQuery(Transaction.class);
        List<Predicate> predicates = new ArrayList<>();

        Root<Transaction> root = criteriaQuery.from(Transaction.class);

        Predicate FromPredicate = criteriaBuilder
                .like(root.get("accountFrom"),"%" +Iban + "%");

        Predicate ToPredicate = criteriaBuilder
                .like(root.get("accountTo"),"%" +Iban + "%");

        if (request.getIbanTo() != null){
            Predicate IbanToPredicate = criteriaBuilder
                    .like(root.get("accountTo"),"%" + request.getIbanTo()+ "%" );
            predicates.add(IbanToPredicate);
        }

        if(request.getIbanFrom() != null)
        {
            Predicate IbanFromPredicate = criteriaBuilder
                    .like(root.get("accountFrom"),"%" +request.getIbanFrom() + "%");
            predicates.add(IbanFromPredicate);
        }

        //Get all date and time from certain date
        if (request.getDateFrom() != null){
            Predicate dateFromPredicate = criteriaBuilder
                    .between(root.get("executionDate"), request.getDateFrom(),LocalDateTime.now() );
            predicates.add(dateFromPredicate);
        }

        //Get all date and time until certain date
        if(request.getDateTo() != null)
        {
            Predicate dateToPredicate = criteriaBuilder
                    .between(root.get("executionDate"),request.getDateTo().minusMonths(12) , request.getDateTo());
            predicates.add(dateToPredicate);
        }

        // Get date and time between two dates
        if (request.getDateFrom() != null && request.getDateTo() != null && request.getDateFrom().isBefore(request.getDateTo()))
        {
            Predicate datePredicate = criteriaBuilder
                    .between(root.get("executionDate"),request.getDateFrom(),request.getDateTo());
            predicates.add(datePredicate);
        }
        if (request.getAmountEquals() != null)
        {
            Predicate amountEqualsPredicate = criteriaBuilder
                    .equal(root.get("amount"),request.getAmountEquals());
            predicates.add(amountEqualsPredicate);
        }

        if (request.getAmountBigger() != null)
        {
            Predicate amountBiggerPredicate = criteriaBuilder
                    .greaterThan(root.get("amount"),request.getAmountBigger());
            predicates.add(amountBiggerPredicate);
        }

        if (request.getAmountSmaller() != null)
        {
            Predicate amountSmallerPredicate = criteriaBuilder
                    .lessThan(root.get("amount"),request.getAmountSmaller());
            predicates.add(amountSmallerPredicate);
        }

        Predicate IbanPredicate = criteriaBuilder
                .or(FromPredicate,ToPredicate);
        predicates.add(IbanPredicate);

        criteriaQuery.where(
                criteriaBuilder.and(predicates.toArray(new Predicate[0]))

        );

        TypedQuery<Transaction> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }
}
