package io.swagger.repository;

import io.swagger.model.dto.IbanTransactionRequestDTO;
import io.swagger.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
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

        Root<Transaction> root = criteriaQuery.from(Transaction.class);

             Predicate FromPredicate = criteriaBuilder
                    .like(root.get("accountFrom"),"%" +Iban + "%");

        Predicate ToPredicate = criteriaBuilder
                .like(root.get("accountTo"),"%" +Iban + "%");

            Predicate IbanToPredicate = criteriaBuilder
                    .like(root.get("accountTo"),"%" + request.getIbanTo()+ "%" );

        Predicate IbanFromPredicate = criteriaBuilder
                .like(root.get("accountFrom"),"%" +request.getIbanFrom() + "%");

        //Get all date and time from certain date
            Predicate dateFromPredicate = criteriaBuilder
                    .between(root.get("executionDate"),request.getDateFrom(), LocalDateTime.now());

        //Get all date and time until certain date
            Predicate dateToPredicate = criteriaBuilder
                    .between(root.get("executionDate"), LocalDateTime.now(),request.getDateTo());

            Predicate amountEqualsPredicate = criteriaBuilder
                    .equal(root.get("amount"),request.getAmountEquals());


            Predicate amountBiggerPredicate = criteriaBuilder
                    .greaterThan(root.get("amount"),request.getAmountBigger());


            Predicate amountSmallerPredicate = criteriaBuilder
                    .lessThan(root.get("amount"),request.getAmountSmaller());

            Predicate orPredicate = criteriaBuilder
                    .or(IbanToPredicate,IbanFromPredicate, dateFromPredicate,dateToPredicate,amountEqualsPredicate,amountBiggerPredicate,amountSmallerPredicate);

            Predicate IbanPredicate = criteriaBuilder
                    .or(FromPredicate,ToPredicate);
            criteriaQuery.where(
                    criteriaBuilder.and(orPredicate,IbanPredicate)
            );

        TypedQuery<Transaction> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }
}
